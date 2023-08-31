package me.tecno.mclaunch.tests;

import com.google.gson.Gson;
import me.tecno.mclaunch.launch.LoggingOptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggingTests {
	@Test
	public void testLogDeserialization() {
		String json = "{\"argument\": \"-Dlog4j.configurationFile=${path}\"," +
				"\"file\": {\"id\": \"client-1.12.xml\",\"sha1\": \"bd65e7d2e3c237be76cfbef4c2405033d7f91521\"," +
				"\"size\": 888,\"url\": " +
				"\"https://piston-data.mojang.com/v1/objects/bd65e7d2e3c237be76cfbef4c2405033d7f91521/client-1.12.xml\"}," +
				"\"type\": \"log4j2-xml\"}";

		LoggingOptions options = new Gson().fromJson(json, LoggingOptions.class);

		System.out.println("Deserialized logging options: " + options);

		assertEquals("-Dlog4j.configurationFile=${path}", options.argument());
		assertEquals("client-1.12.xml", options.file().getId());
		assertEquals("bd65e7d2e3c237be76cfbef4c2405033d7f91521", options.file().getSha1());
		assertEquals(888, options.file().getSize());
	}
}
