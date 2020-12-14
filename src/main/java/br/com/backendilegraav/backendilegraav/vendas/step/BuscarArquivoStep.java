package br.com.backendilegraav.backendilegraav.vendas.step;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BuscarArquivoStep implements Tasklet, StepExecutionListener {

	// como opcional, essas informacoes podem ser parametrizadas no arquivo de
	// propriedades
	private static final String PATH_FILE_IN = "C:/WorkspaceJava/backend-ilegra-av/data/in/";
	private static final String PATH_FILE_TO_PROCESS = "C:/WorkspaceJava/backend-ilegra-av/data/inProcess/";

	private String arquivoLido = null;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("### ---> Buscar arquivo ...");
		final File folder = new File("C:/WorkspaceJava/backend-ilegra-av/data/in");
		listaArquivoPasta(folder);

	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("### ---> Mover arquivo para diretorio inProcess...");
		if (arquivoLido != null)
			moverArquivoLido();

		return RepeatStatus.FINISHED;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("### ---> Prepara chamada Relatório...");
		registraOrigem();

		return ExitStatus.COMPLETED;
	}

	private void registraOrigem() {
		try {

			String info1 = "-------------------------------------------";
			Files.write(Paths.get(PATH_FILE_TO_PROCESS + "vendas.txt"), info1.getBytes(), StandardOpenOption.APPEND);

			info1 = "SYSFILE-ORIGEM:" + arquivoLido;
			Files.write(Paths.get(PATH_FILE_TO_PROCESS + "vendas.txt"), info1.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void moverArquivoLido() {
		Path origem = Paths.get(PATH_FILE_IN + arquivoLido);
		Path destino = Paths.get(PATH_FILE_TO_PROCESS + "vendas.txt");
		try {
			Files.move(origem, destino);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

}
