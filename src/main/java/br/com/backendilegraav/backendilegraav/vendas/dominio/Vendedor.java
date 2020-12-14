package br.com.backendilegraav.backendilegraav.vendas.dominio;

public class Vendedor {

	private String cpf;
	private String nome;
	private String salario;

	public String getCpf() {
		return cpf;
	}

	public void setCpj(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSalario() {
		return salario;
	}

	public void setSalario(String salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return "Vendedor{" + "cpf='" + cpf + "'" + ", nome ='" + nome + "'" + ", salario='" + salario + "'" + '}';
	}

}
