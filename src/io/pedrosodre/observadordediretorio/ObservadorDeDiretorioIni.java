package io.pedrosodre.observadordediretorio;


import io.pedrosodre.config.Propriedades;

public class ObservadorDeDiretorioIni {

	public enum Listeners {
		DIRETORIO_DE_ENTRADA
	}

	public static ObservadorDeDiretorio getDirectoryListener(Listeners value) {
		switch (value) {
		case DIRETORIO_DE_ENTRADA:{
			return new ObservadorDeDiretorioDeEntrada(Propriedades.get("data.diretorio.entrada"));
		}
		default:
			throw new IllegalArgumentException();
		}

	}

}
