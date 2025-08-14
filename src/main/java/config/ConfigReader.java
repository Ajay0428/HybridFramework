package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigReader {

	private final Properties properties = new Properties();

	public String getProperty(String key) throws IOException {

		if (key == null || key.trim().isEmpty()) {
			System.err.println("ERROR: Property key cannot be null or empty.");
			return null;
		}

		List<String> propertyFiles = Arrays.asList("config.properties");

		boolean fileLoaded = false;

		for (String file : propertyFiles) {

			String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
					+ File.separator + "resources" + File.separator + file;

			File propFile = new File(filePath);

			if (!propFile.exists()) {
				System.err.println("WARNING: Property file not found: " + filePath);
				continue;
			}

			try (FileInputStream fileInputStream = new FileInputStream(propFile)) {
				properties.load(fileInputStream);
				fileLoaded = true;

			} catch (IOException e) {
				System.err.println("ERROR: Unable to load property file: " + filePath);
				e.printStackTrace();
			}
		}

		if (!fileLoaded) {
			System.err.println("ERROR: No valid property file was loaded.");
			return null;
		}

		String value = properties.getProperty(key);

		if (value == null) {
			System.err.println("WARNING: Property key '" + key + "' not found in loaded files.");
		}

		return value;
	}

	public static void main(String[] args) throws IOException {
		
		ConfigReader configReader = new ConfigReader();

		String browser = configReader.getProperty("browser");
		if (browser != null) {
			System.out.println("Browser: " + browser);
		}
	}

}
