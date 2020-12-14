package br.com.backendilegraav.backendilegraav.vendas.reader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.backendilegraav.backendilegraav.vendas.dominio.Cliente;
import br.com.backendilegraav.backendilegraav.vendas.dominio.Transacao;
import br.com.backendilegraav.backendilegraav.vendas.dominio.Vendedor;

@Configuration
public class VendasTransacaoLineMapperConfig {

	private static final String DELIMITADOR = "ç";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public PatternMatchingCompositeLineMapper lineMapper() {
		PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper<>();
		lineMapper.setTokenizers(tokenizers());
		lineMapper.setFieldSetMappers(fieldSetMappers());
		return lineMapper;

	}

	@SuppressWarnings("rawtypes")
	private Map<String, FieldSetMapper> fieldSetMappers() {
		Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
		fieldSetMappers.put("001*", fieldSetMappers(Vendedor.class));
		fieldSetMappers.put("002*", fieldSetMappers(Cliente.class));
		fieldSetMappers.put("003*", fieldSetMappers(Transacao.class));
		return fieldSetMappers;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private FieldSetMapper fieldSetMappers(Class classe) {
		BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(classe);
		return fieldSetMapper;
	}

	private Map<String, LineTokenizer> tokenizers() {
		Map<String, LineTokenizer> tokenizers = new HashMap<>();
		tokenizers.put("001*", vendedorLineTokenizer());
		tokenizers.put("002*", clienteLineTokenizer());
		tokenizers.put("003*", transacaoLineTokenizer());
		return tokenizers;
	}

	private LineTokenizer vendedorLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("cpf", "nome", "salario");
		lineTokenizer.setIncludedFields(1, 2, 3);
		lineTokenizer.setDelimiter(DELIMITADOR);
		return lineTokenizer;
		// 001çCPFçNameçSalary
	}

	private LineTokenizer clienteLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("cnpj", "nome", "unidade");
		lineTokenizer.setIncludedFields(1, 2, 3);
		lineTokenizer.setDelimiter(DELIMITADOR);
		return lineTokenizer;
		// 002çCNPJçNameçBusiness Area
	}

	private LineTokenizer transacaoLineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("matricula", "produto", "nomeVendedor");
		lineTokenizer.setIncludedFields(1, 2, 3);
		lineTokenizer.setDelimiter(DELIMITADOR);
		return lineTokenizer;
	}
}
