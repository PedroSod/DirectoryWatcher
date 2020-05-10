package io.pedrosodre.observadordediretorio;



import io.pedrosodre.processadordearquivos.ProcessadorDeArquivo;
import io.pedrosodre.processadordearquivos.ProcessadorDeArquivoIni;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObservadorDeDiretorioDeEntrada implements ObservadorDeDiretorio {

	private final String filePath;

	ObservadorDeDiretorioDeEntrada(String filePath) {
		this.filePath = filePath;
	}

	public void run() {
		try {

			WatchService inPathWatcher = FileSystems.getDefault().newWatchService();
			Path inPath = Paths.get(System.getenv("HOMEPATH") + filePath);
			processExistingFiles(inPath);
			inPath.register(inPathWatcher, StandardWatchEventKinds.ENTRY_CREATE);
			watching(inPathWatcher,inPath);

		} catch (IOException e) {
			System.out.println(String.format("Erro no monitoramento de entrada dos dados: %s", e.getMessage()));
		} catch (InterruptedException e) {
			System.out.println(String.format("Erro de execução: %s", e.getMessage()));
		}
	}

	public void watching( WatchService inPathWatcher,Path inPath) throws InterruptedException {
		WatchKey inPathWatchkey;
		while ((inPathWatchkey = inPathWatcher.take()) != null) {
			for (WatchEvent<?> event : inPathWatchkey.pollEvents()) {
				if (event.context() instanceof Path) {
					Path fileFullPath = inPath.resolve((Path) event.context());
					processFile(fileFullPath);
				}
			}
			inPathWatchkey.reset();
		}
	}

	private void processFile(Path fileFullPath) {
		ProcessadorDeArquivo processadorDeArquivo = ProcessadorDeArquivoIni.getFileProcessor(fileFullPath);

		if (processadorDeArquivo != null) {
			ExecutorService executor = Executors.newCachedThreadPool();
			executor.submit(processadorDeArquivo);
		}
	}

	private void processExistingFiles(Path inPath) {
		if (inPath.toFile() != null && inPath.toFile().exists()) {
			for (File file : inPath.toFile().listFiles()) {
				processFile(Paths.get(file.getAbsolutePath()));
			}
		}
	}
}