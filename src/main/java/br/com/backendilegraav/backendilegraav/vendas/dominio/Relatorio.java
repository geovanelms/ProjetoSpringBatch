package br.com.backendilegraav.backendilegraav.vendas.dominio;

public class Relatorio {
//
//	Quantidade de clientes no arquivo de entrada
//	● Quantidade de vendedores no arquivo de entrada
//	● ID da venda mais cara
//	● O pior vendedor

	private Integer qdtClientes = 0;
	private Integer qdtVendedores = 0;
	private String vendaMaisCara;
	private String piorVendedor;
	
	public Relatorio() {
	
	}

	public Relatorio(Relatorio relatorio) {
		this.qdtClientes = relatorio.getQdtClientes();
		this.qdtVendedores = relatorio.getQdtVendedores();
		this.vendaMaisCara = relatorio.getVendaMaisCara();
		this.piorVendedor = relatorio.getPiorVendedor();
	}

	public Integer getQdtClientes() {
		return qdtClientes;
	}

	public void setQdtClientes(Integer qdtClientes) {
		this.qdtClientes = qdtClientes;
	}

	public Integer getQdtVendedores() {
		return qdtVendedores;
	}

	public void setQdtVendedores(Integer qdtVendedores) {
		this.qdtVendedores = qdtVendedores;
	}

	public String getVendaMaisCara() {
		return vendaMaisCara;
	}

	public void setVendaMaisCara(String vendaMaisCara) {
		this.vendaMaisCara = vendaMaisCara;
	}

	public String getPiorVendedor() {
		return piorVendedor;
	}

	public void setPiorVendedor(String piorVendedor) {
		this.piorVendedor = piorVendedor;
	}

	public void incrementaQdtClientes() {
		this.qdtClientes = this.qdtClientes + 1;
	}

	public void incrementaQtdVenda() {
		this.qdtVendedores = this.qdtVendedores + 1;
	}

	@Override
	public String toString() {
		return "Relatorio{" + "qdtClientes='" + qdtClientes + "'" + ", qdtVendedores ='" + qdtVendedores
				+ "'" + ", vendaMaisCara='" + vendaMaisCara + "'"
				+ ", piorVendedor='" + piorVendedor + "'" + '}';
	}
}
