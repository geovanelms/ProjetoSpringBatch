package br.com.backendilegraav.backendilegraav.vendas.step;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class MoverArquivoProcessadoStep implements Tasklet, StepExecutionListener {

	/**
	 * Apos a geracao do relatorio, o arquivo vendas.txt eh movido para a pasta de
	 * arquivos processados
	 */

	private static final String PATH_FILE_IN = "C:/WorkspaceJava/backend-ilegra-av/data/inProcess/";
	private static final String PATH_FILE_TO_PROCESS = "C:/WorkspaceJava/backend-ilegra-av/data/done/";
	private static final String PATH_FILE_REPORT = "C:/WorkspaceJava/backend-ilegra-av/data/out/";

	private String arquivoLido = null;
	private String relatorio = "AnaliseDeVendas.txt";

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("### ---> Buscar arquivo processado ...");
		final File folder = new File(PATH_FILE_IN);
		listaArquivoPasta(folder);
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("### ---> Mover arquivo para diretorio done...");
		if (arquivoLido != null)
			moverArquivoLido();

		return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("### ---> Renomeando o RELATORIO com datahora processamento ...");
		if (arquivoLido != null)
			renomeaRelatorio();

		System.out.println("### ---> Processo concluido!! <---- ###");
		return ExitStatus.COMPLETED;
	}

	private void renomeaRelatorio() {
		String tempoConclusao = new SimpleDateFormat("dd-MMM-yyyy__HH-mm-ss-aa").format(new Date());
		Path origem = Paths.get(PATH_FILE_REPORT + relatorio);
		Path destino = Paths
				.get(PATH_FILE_REPORT + relatorio.replace(".txt", "") + "__processed_at_" + tempoConclusao + ".txt");
		try {
			Files.move(origem, destino);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(" *** Relatorio Atualizado: " + arquivoLido + "__processed_at_" + tempoConclusao + ".txt");

	}

	public void listaArquivoPasta(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listaArquivoPasta(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
				arquivoLido = fileEntry.getName();
			}
		}
	}

	private void moverArquivoLido() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		String tempoConclusao = new SimpleDateFormat("dd-MMM-yyyy__HH-mm-ss-aa").format(new Date());
		Path origem = Paths.get(PATH_FILE_IN + arquivoLido);
		Path destino = Paths.get(
				PATH_FILE_TO_PROCESS + arquivoLido.replace(".txt", "") + "__processed_at_" + tempoConclusao + ".txt");
		try {
			Files.move(origem, destino);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(" *** Arquivo processado: " + arquivoLido + "__processed_at_" + tempoConclusao + ".txt");
	}

}
