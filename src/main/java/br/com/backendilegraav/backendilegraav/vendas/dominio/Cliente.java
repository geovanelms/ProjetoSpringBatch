package br.com.backendilegraav.backendilegraav.vendas.dominio;

public class Cliente {
	private String nome;
	private String cnpj;
	private String unidade;

	public Cliente() {
	}

	public Cliente(String nome, String cnpj, String unidade) {
		this.nome = nome;
		this.cnpj = cnpj;
		this.unidade = unidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@Override
	public String toString() {
		return "Cliente{" + "nome='" + nome + "'" + ", cnpj ='" + cnpj + "'" + ", unidade='" + unidade + "'" + '}';
	}
}
