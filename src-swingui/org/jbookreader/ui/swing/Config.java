package org.jbookreader.ui.swing;

import org.jbookreader.Configuration;

public class Config extends Configuration {

	private static String DEFAULT_RESOURCE = "org/jbookreader/ui/swing/DefaultValues.properties";

	private static Configuration ourConfig;
	
	private Config() {
		super("JBookReader", "swingui", DEFAULT_RESOURCE);
		setAutoSaved(true);
	}

	public static Configuration getConfig() {
		if (ourConfig == null) {
			ourConfig = new Config();
		}

		return ourConfig;
	}
}
