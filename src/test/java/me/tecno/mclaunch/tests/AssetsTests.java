package me.tecno.mclaunch.tests;

import com.google.gson.Gson;
import me.tecno.mclaunch.assets.AssetIndex;
import me.tecno.mclaunch.assets.AssetIndex.AssetRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetsTests {

	@Test
	public void testAssetMetaDeserialization() {
		String json = "{\"id\": \"7\",\"sha1\": \"bebcc57ec5d1cf6d557b9641db3b6de6a0bea3e5\",\"size\": 414035," +
				"\"totalSize\": 619175033,\"url\": " +
				"\"https://piston-meta.mojang.com/v1/packages/bebcc57ec5d1cf6d557b9641db3b6de6a0bea3e5/7.json\"}";

		AssetIndex index = new Gson().fromJson(json, AssetIndex.class);

		System.out.print("Deserialized asset index: ");
		System.out.println(index.toString());

		assertEquals("7", index.getId());
		assertEquals("bebcc57ec5d1cf6d557b9641db3b6de6a0bea3e5", index.getSha1());
		assertEquals(414035, index.getSize());
		assertEquals(619175033, index.getTotalSize());
		assertEquals("https://piston-meta.mojang.com/v1/packages/bebcc57ec5d1cf6d557b9641db3b6de6a0bea3e5/7.json",
				index.getUrl());
	}

	@Test
	public void testAssetMetaDownload() throws IOException {
		AssetIndex index = new AssetIndex("7", "fake-hash",
				"https://piston-meta.mojang.com/v1/packages/bebcc57ec5d1cf6d557b9641db3b6de6a0bea3e5/7.json",
				0, 0);

		Map<String, AssetRecord> objects = index.downloadObjectRegistry();
		System.out.printf("Asset index downloaded successfully, count: %d\n", objects.size());

		objects.entrySet().stream().limit(5)
				.forEach(e -> System.out.printf("%s:\t%s\n", e.getKey(), e.getValue()));
		System.out.println();

		assertEquals(3604, objects.size());
		assertEquals("b62ca8ec10d07e6bf5ac8dae0c8c1d2e6a1e3356", objects.get("icons/icon_128x128.png").hash());
		assertEquals("5ff04807c356f1beed0b86ccf659b44b9983e3fa", objects.get("icons/icon_16x16.png").hash());
		assertEquals("8030dd9dc315c0381d52c4782ea36c6baf6e8135", objects.get("icons/icon_256x256.png").hash());
		assertEquals("af96f55a90eaf11b327f1b5f8834a051027dc506", objects.get("icons/icon_32x32.png").hash());
		assertEquals("b80b6e9ff01c78c624df5429e1d3dcd3d5130834", objects.get("icons/icon_48x48.png").hash());

	}
}
