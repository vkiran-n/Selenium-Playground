/* reads from gmoUIMap2.properties file */
package utilityScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class UIMap {

	public static Properties prop;

	// Path to FIREFOX, EDGE and CHROME drivers
	public static String firefoxPath = null;
	public static String chromePath = null;
	public static String edgePath = null;

	public static void loadUIMapProp() {
		String prop_path = "F:\\Training\\Selenium\\WS_JavaCore\\MavenSeleniumAdv\\src\\test\\resources\\gmoUIMap.properties";
		File file = new File(prop_path);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop = new Properties();
			prop.load(fileInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readProperties() {
		// Path to IE and CHROME drivers
		firefoxPath = prop.getProperty("firefoxPath");
		chromePath = prop.getProperty("chromePath");
		edgePath = prop.getProperty("edgePath");
	}
}
