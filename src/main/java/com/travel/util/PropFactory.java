package com.travel.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropFactory {
	public static Properties prop = null;

	public static Properties getProp() {
		prop = new Properties();
		InputStream in = ClassLoader
				.getSystemResourceAsStream("param.properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
