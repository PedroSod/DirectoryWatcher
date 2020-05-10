package io.pedrosodre;

import io.pedrosodre.observadordediretorio.ObservadorDeDiretorioIni;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        System.out.println("Executando...");

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(ObservadorDeDiretorioIni.getDirectoryListener(ObservadorDeDiretorioIni.Listeners.DIRETORIO_DE_ENTRADA));

    }
}
