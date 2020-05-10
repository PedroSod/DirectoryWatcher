package io.pedrosodre.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Propriedades {


	private static Properties properties;

	static {
		properties = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("config.properties");
			properties.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String get(String value) {
		return properties.getProperty(value);
	}
}
