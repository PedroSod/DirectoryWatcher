package io.pedrosodre.processadordearquivos;


import java.nio.file.Path;

public class ProcessadorDeArquivoIni {
	
	public static ProcessadorDeArquivo getFileProcessor(Path path){
		if(path.toFile().isFile()){
			String fileExtension = path.toString().substring(path.toString().lastIndexOf('.') + 1).toLowerCase();
			
			switch(fileExtension){
				case "txt": return new ProcessadorDeArquivoTxt(path);
				default: return null;
			}
		}
		return null;
	}

}
