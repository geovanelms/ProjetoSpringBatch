package br.com.backendilegraav.backendilegraav.vendas.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import br.com.backendilegraav.backendilegraav.vendas.dominio.Cliente;
import br.com.backendilegraav.backendilegraav.vendas.dominio.Transacao;
import br.com.backendilegraav.backendilegraav.vendas.dominio.Vendedor;


@Component
public class itemWriterResumoConfig {

	@Bean
	public FlatFileItemWriter<Object> vendasWriter(
			@Value("file:data/out/AnaliseDeVendas.txt") Resource arquivoClientesSaida,
			ResumoVendasRodapeWriter rodaPeCallback) {
		return new FlatFileItemWriterBuilder<Object>().name("vendasWriter").resource(arquivoClientesSaida)
				.lineAggregator(lineAggregator()).headerCallback(cabecalhoCallback()).footerCallback(rodaPeCallback)
				.build();
	}

	private FlatFileHeaderCallback cabecalhoCallback() {
		return new FlatFileHeaderCallback() {
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.append(String.format("SISTEMA BACKEND ILEGRA:                 DATA: %s\n",
						new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				writer.append(String.format("MÓDULO: VENDAS                          HORA: %s\n",
						new SimpleDateFormat("HH:mm:ss").format(new Date())));
				writer.append(String.format(" \n CRIADO POR: GEOVANE R LEMOS\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));
				writer.append(String.format("\n\t\t ANÁLISE DE VENDAS\n"));
				writer.append(String.format("\n\t\t Detalhes das Vendas (Opcional)\n"));
			}
		};
	}

	private LineAggregator<Object> lineAggregator() {
		return new LineAggregator<Object>() {
			@Override
			public String aggregate(Object item) {
			//	 Se em algum momento precisar listar os itens
				if (item instanceof Vendedor) {
					return String.format(" Vendedor: %s", item);
				}
				if (item instanceof Cliente) {
					return String.format(" Cliente: %s", item);
				}
				if (item instanceof Transacao) {
					return String.format(" Transacao: %s", item);
				}
				return "";
			}
		};
	}
}
