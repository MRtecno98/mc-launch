package me.tecno.mclaunch.global;

import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class SystemInfo {
	private static Properties prop = new Properties();
	
	static {
		reloadSystemInfo();
	}
	
	public static Properties getProperties() {
		return prop;
	}
	
	public static void reloadSystemInfo() {
		prop.clear();
		System.getProperties().forEach((key, value) -> {
			String newValue = TransformRegister.getGlobalRegister()
					.transform((String) key, (String) value);
			
			prop.setProperty((String) key, newValue != null ? newValue : (String) value);
		});
	}
}
