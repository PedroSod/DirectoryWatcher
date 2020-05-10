package io.pedrosodre.relatorio;



import io.pedrosodre.modelo.Venda;
import io.pedrosodre.modelo.Cliente;
import io.pedrosodre.modelo.Vendedor;

import java.util.HashMap;
import java.util.Map;

public class RelatorioDeVendas {

	private Map<String, Vendedor> vendedores;
	private Map<String, Cliente> clientes;
	private Venda melhorVenda;
	private String dados;

	public Map<String, Vendedor> getVendedores() {
		return vendedores;
	}

	public void setVendedores(Map<String, Vendedor> vendedores) {
		this.vendedores = vendedores;
	}

	public Map<String, Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Map<String, Cliente> clientes) {
		this.clientes = clientes;
	}

	public Venda getMelhorVenda() {
		return melhorVenda;
	}

	public void setMelhorVenda(Venda melhorVenda) {
		this.melhorVenda = melhorVenda;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}
	
	public void addSalesPerson(Vendedor person){
		if(vendedores == null){
			vendedores = new HashMap<String, Vendedor>();
		}
		vendedores.put(person.getName(), person);
	}
	
	public void addClient(Cliente client){
		if(clientes == null){
			clientes = new HashMap<String, Cliente>();
		}
		clientes.put(client.getCnpj(), client);
	}
}
