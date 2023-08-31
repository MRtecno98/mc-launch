package me.tecno.mclaunch.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.tecno.mclaunch.indexing.rules.ElementRule;
import me.tecno.mclaunch.indexing.rules.ElementRuleDeserializer;
import me.tecno.mclaunch.indexing.rules.SystemCondition;
import me.tecno.mclaunch.libraries.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryTests {
	@Test
	public void testLibraryDeserialization() {
		String json = "{\"downloads\": {\"artifact\": {\"path\": " +
				"\"org/lwjgl/lwjgl-glfw/3.3.2/lwjgl-glfw-3.3.2-natives-macos.jar\"," +
				"\"sha1\": \"8223ba1b757c43624f03b09ab9d9228c7f6db001\",\"size\": 140627,\"url\": " +
				"\"https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.2/lwjgl-glfw-3.3.2-natives-macos.jar\"}}," +
				"\"name\": \"org.lwjgl:lwjgl-glfw:3.3.2:natives-macos\",\"rules\": " +
				"[{\"action\": \"allow\",\"os\": {\"name\": \"osx\"}}]}";

		Library library = new GsonBuilder()
				.registerTypeAdapter(ElementRule.class, new ElementRuleDeserializer<>())
				.create()
				.fromJson(json, Library.class);

		System.out.println("Deserialized library: ");
		System.out.println(library);
		System.out.println();

		assertEquals("org.lwjgl:lwjgl-glfw:3.3.2:natives-macos", library.getName());
		assertEquals("https://libraries.minecraft.net/org/lwjgl/lwjgl-glfw/3.3.2/lwjgl-glfw-3.3.2-natives-macos.jar",
				library.getDownloads().artifact().getUrl().toString());
		assertEquals("8223ba1b757c43624f03b09ab9d9228c7f6db001", library.getDownloads().artifact().getSha1());
		assertEquals(140627, library.getDownloads().artifact().getSize());
		assertEquals("org/lwjgl/lwjgl-glfw/3.3.2/lwjgl-glfw-3.3.2-natives-macos.jar",
				library.getDownloads().artifact().getPath());
		assertEquals(1, library.getRules().size());
		assertEquals("allow", library.getRules().iterator().next().getAction().name().toLowerCase());
		assertEquals("osx", ((SystemCondition<Library>) library.getRules().iterator().next()
				.getConditions().iterator().next()).get("name"));
	}
}
