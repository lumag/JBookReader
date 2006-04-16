package org.jbookreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private final Properties myProperties;
	private final File myConfigFile;

	private boolean myAutoSaved;

	public Configuration(String programName, String configuration) {
		this(programName, configuration, null);
	}

	public Configuration(String programName, String configuration,
			String defaultResource) {

		Properties defaults = new Properties();
		if (defaultResource != null) {
			InputStream defaultsStream = this.getClass().getClassLoader().getResourceAsStream(defaultResource);
			
			if (defaultsStream == null) {
				System.err.println("Can't find default properties resource");
			} else {
				try {
					defaults.load(defaultsStream);
				} catch (IOException e) {
					System.err.println("Can't load default properties");
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					System.err.println("Can't load default properties");
					e.printStackTrace();
				}
			}
		}

		this.myProperties = new Properties(defaults);

		File homeDir = new File(System.getProperty("user.home"));
		File programDir = new File(homeDir, '.' + programName);
		if (!programDir.exists()) {
			programDir.mkdir();
		}

		this.myConfigFile = new File(programDir, configuration + ".properties");
		if (this.myConfigFile.exists()) {
			try {
				InputStream configStream = new FileInputStream(this.myConfigFile);
				this.myProperties.load(configStream);
			} catch (IOException e) {
				System.err.println("Can't load user properties");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.err.println("Can't load user properties");
				e.printStackTrace();
			}
		}

		this.myProperties.list(System.out);
	}

	public void save() throws IOException {
		this.myProperties.store(new FileOutputStream(this.myConfigFile), null);
	}

	public boolean isAutoSaved() {
		return this.myAutoSaved;
	}

	public void setAutoSaved(boolean autoSaved) {
		this.myAutoSaved = autoSaved;
	}

	public String getStringValue(String key) {
		return this.myProperties.getProperty(key, "");
	}

	public void setStringValue(String key, String value) {
		this.myProperties.setProperty(key, value);

		if (this.myAutoSaved) {
			try {
				this.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int getIntValue(String key) {
		String value = getStringValue(key);

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void setIntValue(String key, int value) {
		setStringValue(key, Integer.toString(value));
	}
}
