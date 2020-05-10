package io.pedrosodre.modelo;

public class Cliente {
	
	private String cnpj;
	private String nome;
	private String areaDeNegocio;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAreaDeNegocio() {
		return areaDeNegocio;
	}

	public void setAreaDeNegocio(String areaDeNegocio) {
		this.areaDeNegocio = areaDeNegocio;
	}

}
