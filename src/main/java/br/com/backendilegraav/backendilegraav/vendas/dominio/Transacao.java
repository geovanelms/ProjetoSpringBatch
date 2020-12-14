package br.com.backendilegraav.backendilegraav.vendas.dominio;

public class Transacao {

//	003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name
//	003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
	private String matricula;
	private String produto;
	private String nomeVendedor;
	private Double valor;

	
	public Transacao() {
		
	}
	public Transacao(String matricula, String produto, String nomeVendedor) {
		this.matricula = matricula;
		this.produto = produto;
		this.nomeVendedor = nomeVendedor;
		this.valor = subTotal(produto);
	}

	private Double subTotal(String listaProdutos) {
		// [1-34-10,2-33-1.50,3-40-0.10]
		String[] items = listaProdutos.replace("[", "").replace("]", "").split(",");
		Double subTotal = 0.0;
		for (String item : items) {
			String valorItem = item.substring(item.lastIndexOf("-")+1 , item.length());
			subTotal += Double.valueOf(valorItem);
		}
		return subTotal;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	@Override
	public String toString() {
		return "Transacao{" + "matricula='" + matricula + "'" + ", produto ='" + produto + "'" + ", nomeVendedor='"
				+ nomeVendedor + "'" + '}';
	}

	public Double getValor() {
		this.valor = subTotal(produto);
		return valor;
	}


}
