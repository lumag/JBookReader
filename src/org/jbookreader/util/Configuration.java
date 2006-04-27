package org.jbookreader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class represents a generic configuration engine.
 * It works by using property files in subdir of user home dir.
 * Also you can supply a resource with default values.
 * 
 * @author Dmitry Baryshkov (dbaryshkov@gmail.com)
 *
 */
public class Configuration {
	private final Properties myProperties;
	private final File myConfigFile;

	private boolean myAutoSaved;

	/**
	 * This creates new configuration engine from given
	 * program and configuration names.
	 * 
	 * @param programName the name of the program (the name of the subdir)
	 * @param configuration the name of configuration file
	 */
	public Configuration(String programName, String configuration) {
		this(programName, configuration, null);
	}

	/**
	 * This creates new configuration engine from given
	 * program and configuration names. Default values are loaded
	 * from given resource name.
	 * 
	 * @param programName the name of the program (the name of the subdir)
	 * @param configuration the name of configuration file
	 * @param defaultResource the name of the resource with default values
	 */
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

//		this.myProperties.list(System.out);
	}

	/**
	 * This saves the configuration file.
	 * @throws IOException in case of I/O error.
	 */
	public void save() throws IOException {
		this.myProperties.store(new FileOutputStream(this.myConfigFile), null);
	}

	/**
	 * Returns whether this configuration is automagically saved on changes.
	 * @return whether this configuration is automagically saved on changes.
	 */
	public boolean isAutoSaved() {
		return this.myAutoSaved;
	}

	/**
	 * Sets the autosave property
	 * @param autoSaved whether to autosave config on changes
	 */
	public void setAutoSaved(boolean autoSaved) {
		this.myAutoSaved = autoSaved;
	}

	/**
	 * Returns the string value, associated with specified key.
	 * 
	 * @param key the key to get value for
	 * @return the string value, associated with specified key.
	 */
	public String getStringValue(String key) {
		return this.myProperties.getProperty(key, "");
	}

	/**
	 * Sets the string value, associated with specified key.
	 * @param key the key to work with
	 * @param value the new value for the key
	 */
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

	/**
	 * Returns the integer value, associated with specified key.
	 * 
	 * @param key the key to get value for
	 * @return the integer value, associated with specified key.
	 */
	public int getIntValue(String key) {
		String value = getStringValue(key);

		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Sets the integer value, associated with specified key.
	 * @param key the key to work with
	 * @param value the new value for the key
	 */
	public void setIntValue(String key, int value) {
		setStringValue(key, Integer.toString(value));
	}

	/**
	 * Returns the boolean value, associated with specified key.
	 * 
	 * @param key the key to get value for
	 * @return the boolean value, associated with specified key.
	 */
	public boolean getBooleanValue(String key) {
		String value = getStringValue(key);

		if (value == null) {
			return false;
		}

		if (value.equals("true") || value.equals("True") || value.equals("on") || value.equals("On") || value.equals("1")) {
			return true;
		}

		return false;
	}

	/**
	 * Sets the boolean value, associated with specified key.
	 * @param key the key to work with
	 * @param value the new value for the key
	 */
	public void setBooleanValue(String key, boolean value) {
		setStringValue(key, Boolean.toString(value));
	}
}
