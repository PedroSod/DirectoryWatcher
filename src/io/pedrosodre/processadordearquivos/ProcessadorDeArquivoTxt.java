package io.pedrosodre.processadordearquivos;


import io.pedrosodre.config.Propriedades;
import io.pedrosodre.relatorio.RelatorioDeVendas;
import io.pedrosodre.modelo.Vendedor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class ProcessadorDeArquivoTxt implements ProcessadorDeArquivo {

    private Path filePath;
    private ProcessadorDeDados processadorDeDados = new ProcessadorDeDados();
    private static final String SALESPERSON = "001";
    private static final String CLIENT = "002";
    private static final String SALE = "003";
    private static final String DONE_FORMAT = ".done";
    private static final String OUTPUT_FORMAT = "Clientes: %s\nVendedores: %s\nMelhor venda: %s\nPior Vendedor: %s";

    public ProcessadorDeArquivoTxt(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (Stream<String> stream = Files.lines(filePath, Charset.defaultCharset())) {
            final RelatorioDeVendas report = new RelatorioDeVendas();
            report.setDados("");
            RelatorioDeVendas resultado = stream.reduce(report, (relatorio, linha) -> {
                processarDados(relatorio, linha);
                relatorio.setDados(relatorio.getDados() + linha);
                return relatorio;
            }, (report1, report2) -> {
                return report1;
            });
            criaRelatorio(resultado);
        } catch (IOException e) {
            System.err.println("Erro Desconhecido");
            e.printStackTrace();
        }
    }

    private StringBuilder criaNomeDeRelatorio() {
        StringBuilder outputNameFile = new StringBuilder();
        outputNameFile.append(System.getenv("HOMEPATH"));
        outputNameFile.append(Propriedades.get("data.diretorio.saida"));
        outputNameFile.append(filePath.getFileName().toString().substring(0, filePath.getFileName().toString().lastIndexOf('.')));
        outputNameFile.append(DONE_FORMAT);
        outputNameFile.append(filePath.getFileName().toString().substring(filePath.getFileName().toString().lastIndexOf('.')));
        return outputNameFile;
    }

    private void criaRelatorio(RelatorioDeVendas resultado) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(criaNomeDeRelatorio().toString()), Charset.defaultCharset())) {
            String quantidadeClientes = getQuantidadeDeclientes(resultado);
            String quantidadeDeVendedores = getQuantidadeDeVendedores(resultado);
            String melhorVenda = getMelhorVenda(resultado);
            Vendedor piorVendedor = getPiorVendedor(resultado);
            String worstSalesPersonText = piorVendedor != null ? piorVendedor.getName() : "Nenhum";
            writer.write(String.format(OUTPUT_FORMAT, quantidadeClientes, quantidadeDeVendedores, melhorVenda, worstSalesPersonText));
        } catch (IOException e) {
            System.err.println(String.format("Erro na leitura do arquivo de dados %s", filePath.toString()));
        }
    }

    private String getQuantidadeDeclientes(RelatorioDeVendas resultado) {
        return resultado.getClientes() != null ? Integer.toString(resultado.getClientes().size()) : "0";
    }

    private String getQuantidadeDeVendedores(RelatorioDeVendas resultado) {
        return resultado.getVendedores() != null ? Integer.toString(resultado.getVendedores().size()) : "0";
    }

    private String getMelhorVenda(RelatorioDeVendas resultado) {
        return resultado.getMelhorVenda() != null ? resultado.getMelhorVenda().getIdVenda() : "Não houve melhor venda";
    }

    private Vendedor getPiorVendedor(RelatorioDeVendas resultado) {
        return resultado.getVendedores().values().stream().min(Comparator.comparing(Vendedor::getTotalSales)).orElse(null);
    }

    private void processarDados(RelatorioDeVendas resultado, String dataLine) {
        String[] data = dataLine.split(Propriedades.get("data.separador.entrada"));
        if (data.length > 0) {
            switch (data[0]) {
                case SALESPERSON:
                    processadorDeDados.processarVendedor(resultado, data);
                    break;
                case CLIENT:
                    processadorDeDados.processarCliente(resultado, data);
                    break;
                case SALE:
                    processadorDeDados.processarVenda(resultado, data);
                    break;
                default:
                    break;
            }
        }
    }
}
