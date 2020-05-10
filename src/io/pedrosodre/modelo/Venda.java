package io.pedrosodre.modelo;

import java.util.ArrayList;
import java.util.List;

public class Venda {

	private String idVenda;
	private List<ItemVenda> itens;
	private String vendedor;

	public String getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(String idVenda) {
		this.idVenda = idVenda;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	
	public void addItem(ItemVenda item){
		if(itens == null){
			itens = new ArrayList<ItemVenda>();
		}
	}
	
	public double getSaleTotal(){
		double result = 0;
			for(ItemVenda item : itens){
				result += item.getPreco() * item.getQuantidade();
			}
		return result;
		
	}
}
