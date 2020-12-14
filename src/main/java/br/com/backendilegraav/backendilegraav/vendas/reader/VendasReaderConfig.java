package br.com.backendilegraav.backendilegraav.vendas.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class VendasReaderConfig {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@StepScope
	@Bean
	public FlatFileItemReader vendasItemReader(
			@Value("file:data/inProcess/vendas.txt") Resource arquivoClientes,
			LineMapper lineMapper) {
		return new FlatFileItemReaderBuilder()
				.name("vendasItemReader")
				.resource(arquivoClientes)
				.lineMapper(lineMapper)
				.build();
	}

}
