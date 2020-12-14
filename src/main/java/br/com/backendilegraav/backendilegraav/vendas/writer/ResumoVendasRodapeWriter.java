package br.com.backendilegraav.backendilegraav.vendas.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import br.com.backendilegraav.backendilegraav.vendas.dominio.Cliente;
import br.com.backendilegraav.backendilegraav.vendas.dominio.Transacao;

@Component
public class ResumoVendasRodapeWriter implements FlatFileFooterCallback {

	private Integer totalClientes = 0;
	private Integer totalVendedores = 0;
	private Double totalSubItem = 0.0; //Opcional, somatorio de vendas
	private Double valorVendaMaisCara = 0.0;
	private Double valorVendaMaisBarata = 1000000.0;
	private String idVendaMaisCara ="";
	private String piorVendedor = "";
	private String vendaMaisCara ="";
	private String vendaMaisBarata="";

	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.append(String.format("\n----------------------------------------------------------------------------\n\n"));
		writer.append(String.format("## Quantidade de clientes no arquivo de entrada.........: %d\n",totalClientes));
		writer.append(String.format("## Quantidade de vendedores no arquivo de entrada.......: %d\n",totalVendedores));
		writer.append(String.format("## ID da venda mais cara................................: %s\n",idVendaMaisCara));
		writer.append(String.format("## O pior vendedor......................................: %s\n",piorVendedor));		
		writer.append(String.format("\n----------------------------------------------------------------------------"));
		writer.append(String.format("\n\t\t ## DETALHAMENTO DA ANÁLISE (Opcional) ##\n\n"));
		writer.append(String.format("## Venda mais barata.....:%s\n",vendaMaisBarata));
		writer.append(String.format("## Venda mais cara.......:%s\n",vendaMaisCara));
	}
	
	@BeforeWrite
	public void beforeWrite(List<Object> items) {
		items.forEach(item ->{
			if(item instanceof Cliente) {
				totalClientes+=1;
			}
			if(item instanceof Transacao) {
				totalVendedores+=1;
				Transacao itemTransacao = new Transacao();
				itemTransacao = (Transacao) item;
				analisaVenda(itemTransacao);
				totalSubItem+= itemTransacao.getValor();				
			}
			
			
		});
	}
	
	public void analisaVenda(Transacao itemTransacao) {
		if(itemTransacao.getValor() > valorVendaMaisCara) {
			valorVendaMaisCara = itemTransacao.getValor();
			idVendaMaisCara = itemTransacao.getMatricula();
			vendaMaisCara = "Total Venda =" + itemTransacao.getValor() + "\n \t\t\t\t\t\t\t"  +
			                "Itens da Venda ="   + itemTransacao.getProduto() + " \n \t\t\t\t\t\t\tVendedor =" + itemTransacao.getNomeVendedor();
		}
		if(itemTransacao.getValor() < valorVendaMaisBarata) {
			valorVendaMaisBarata = itemTransacao.getValor();
			piorVendedor = itemTransacao.getNomeVendedor();
			vendaMaisBarata = "Total Venda =" + itemTransacao.getValor() + "\n \t\t\t\t\t\t\t"  +
	                "Itens da Venda ="   + itemTransacao.getProduto() + " \n \t\t\t\t\t\t\tVendedor =" + itemTransacao.getNomeVendedor();
		}
	}

}
