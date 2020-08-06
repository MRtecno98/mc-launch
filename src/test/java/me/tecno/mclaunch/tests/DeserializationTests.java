package me.tecno.mclaunch.tests;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import me.tecno.mclaunch.versioning.Version;
import me.tecno.mclaunch.versioning.VersionManifest;

public class DeserializationTests {
	@Test
	public void testDeserializationVersionManifest() throws IOException {
		VersionManifest m = VersionManifest.getVersionManifest();
		
		System.out.println("Version Manifest downloaded succesfully");
		System.out.println("Versions available: " + m.getVersions().size());
		System.out.println("Latest Release:");
		System.out.println("\tName: " + m.getLatestRelease().getId());
		System.out.println("\tType: " + m.getLatestRelease().getType());
		System.out.println("\tManifest URL: " + m.getLatestRelease().getManifestURI());
		System.out.println("\tReleased on: " + m.getLatestRelease().getReleaseDate());
		System.out.println("\tLast update: " + m.getLatestRelease().getLastUpdate());
		System.out.println();
	}
	
	@Test
	public void testDeserializationClientManifest() throws IOException {
		Version v = VersionManifest.getVersionManifest().getLatestRelease().getVersion();
		System.out.println("Latest release manifest downloaded succesfully");
		System.out.println("\tName: " + v.getId());
		System.out.println("\tType: " + v.getType());
		System.out.println("\tManifest URL: " + v.getManifestURI());
		System.out.println("\tReleased on: " + v.getReleaseDate());
		System.out.println("\tLast update: " + v.getLastUpdate());
		System.out.println("\tArguments: " + v.getArgumentsIndex());
		System.out.println("\tAssets: " + v.getAssetsIndex());
		System.out.println("\tLibraries count: " + v.getLibrariesIndex().size());
		System.out.println("\tExecutables: " + v.getExcecutablesIndex());
		System.out.println("\tLaunch options: " + v.getLaunchOptions());
		System.out.println("\tLogging options: " + v.getLoggingOptions());
		System.out.println();
	}
	
	
}
