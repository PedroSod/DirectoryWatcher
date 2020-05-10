package io.pedrosodre.processadordearquivos;

import io.pedrosodre.modelo.Cliente;
import io.pedrosodre.modelo.ItemVenda;
import io.pedrosodre.modelo.Venda;
import io.pedrosodre.modelo.Vendedor;
import io.pedrosodre.relatorio.RelatorioDeVendas;

public final class ProcessadorDeDados {

    private static final String SEPARADOR_ITEM_VENDA = ",";
    private static final String SEPARADOR_DADOS_ITEM = "-";

    public void processarVendedor(RelatorioDeVendas resultado, String[] data) {
        Vendedor person = new Vendedor();
        person.setCpf(data[1]);
        person.setNome(data[2]);
        person.setSalario(Double.parseDouble(data[3]));
        resultado.addSalesPerson(person);
    }

    public void processarCliente(RelatorioDeVendas resultado, String[] data) {
        Cliente client = new Cliente();
        client.setCnpj(data[1]);
        client.setNome(data[2]);
        client.setAreaDeNegocio(data[3]);
        resultado.addClient(client);
    }

    public void processarVenda(RelatorioDeVendas resultado, String[] data) {
        Venda venda = processaItemDeVenda(data);

        venda.setIdVenda(data[1]);
        venda.setVendedor(data[3]);
        if (resultado.getMelhorVenda() == null || resultado.getMelhorVenda().getSaleTotal() < venda.getSaleTotal()) {
            resultado.setMelhorVenda(venda);
        }
        Vendedor vendedor = resultado.getVendedores().get(venda.getVendedor());
        if (vendedor != null) {
            vendedor.setTotalDeVendas(vendedor.getTotalDeVendas() + venda.getSaleTotal());
        }
    }

    private Venda processaItemDeVenda (String[] data){
        Venda venda = new Venda();
        String[] saleItems = data[2].substring(1, data[2].length() - 1).split(SEPARADOR_ITEM_VENDA);
        for (String saleItemStr : saleItems) {
            String[] itemData = saleItemStr.split(SEPARADOR_DADOS_ITEM);
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setItemId(itemData[0]);
            itemVenda.setQuantidade(Integer.parseInt(itemData[1]));
            itemVenda.setPreco(Double.parseDouble(itemData[2]));
            venda.addItem(itemVenda);
        }
        return venda;
    }
}
