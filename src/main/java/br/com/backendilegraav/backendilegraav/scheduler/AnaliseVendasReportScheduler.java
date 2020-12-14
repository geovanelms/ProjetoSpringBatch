package br.com.backendilegraav.backendilegraav.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.backendilegraav.backendilegraav.vendas.processor.VendasItemProcessor;
import br.com.backendilegraav.backendilegraav.vendas.step.BuscarArquivoStep;
import br.com.backendilegraav.backendilegraav.vendas.step.MoverArquivoProcessadoStep;
import br.com.backendilegraav.backendilegraav.vendas.writer.ResumoVendasRodapeWriter;

@Component
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class AnaliseVendasReportScheduler {
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	private JobBuilderFactory jobs;


	public static void main(String[] args) {
		SpringApplication.run(AnaliseVendasReportScheduler.class, args);
	}

	
	@Scheduled(cron = "0/5 * * * * ?")
	@Bean
	public void performReport() {

		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		try {
			System.out.println(" Job started REPORT ...");

			jobLauncher.run(reportVendasJob(), params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Bean
	public Job reportVendasJob() {
		return jobs.get("reportVendasJob")
				.incrementer(new RunIdIncrementer())
				.start(stepBuscarArquivo())
				.next(reportVendasStep(null, null, null))
				.next(stepMoverArquivoProcessado())
				.build();
	}

	@Autowired
	private StepBuilderFactory steps;
	
	@Bean
	public Step stepBuscarArquivo() {
		return steps.get("stepBuscarArquivo")
				.tasklet(new BuscarArquivoStep())
				.build();
	}
	
	@Bean
	public Step stepMoverArquivoProcessado() {
		return steps.get("stepMoverArquivoProcessado")
				.tasklet(new MoverArquivoProcessadoStep())
				.build();
	}

	

	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	/**
	 * 
	 * @param vendasReader
	 * @return Retorna Relatorio preenchido
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public Step reportVendasStep(
			FlatFileItemReader vendasReader,
			ItemWriter vendasWriter,
			ResumoVendasRodapeWriter rodaPeCallback) {
		return stepBuilderFactory
				.get("reportVendasStep")
				.chunk(1)
				.reader(vendasReader)
				.processor(processor())
				.writer(vendasWriter)
				.listener(rodaPeCallback)
				.faultTolerant()
				.skip(Exception.class)
				.skipLimit(50)
				.build();
	}
	@SuppressWarnings("rawtypes")
	private VendasItemProcessor processor() {
		return new VendasItemProcessor();
	}
	
}
