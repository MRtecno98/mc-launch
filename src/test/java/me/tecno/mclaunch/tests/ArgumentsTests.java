package me.tecno.mclaunch.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import me.tecno.mclaunch.launch.LaunchEnvironment;
import me.tecno.mclaunch.versioning.Version;
import me.tecno.mclaunch.versioning.VersionDataset;
import me.tecno.mclaunch.versioning.VersionManifest;

public class ArgumentsTests {
	private static final String DEMO_CRES_C = " --username ${auth_player_name} --version ${version_name} "
			+ "--gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} "
			+ "--uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType "
			+ "${version_type} --demo --width ${resolution_width} --height ${resolution_height}";
	
	private static final String DEMO_NOCRES_C = " --username ${auth_player_name} --version ${version_name} "
			+ "--gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} "
			+ "--uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} --versionType "
			+ "${version_type} --demo";
	
	private static final String NODEMO_NOCRES_C = " --username ${auth_player_name} --version ${version_name} "
			+ "--gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} "
			+ "--uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} "
			+ "--versionType ${version_type}";
	
	private final @Getter(lazy=true) Version version = computeVersion();
	
	private Version computeVersion() {
		try {
			for(VersionDataset dt : VersionManifest.getVersionManifest().getVersions())
				if(dt.getId().equals("1.16.3")) return dt.getVersion();
			throw new RuntimeException("Test version not found");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testArgumentsEvaluation() throws IOException {
		LaunchEnvironment env = new LaunchEnvironment();
		String nodemo_nocres = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		
		assertEquals(NODEMO_NOCRES_C, nodemo_nocres);
		
		env.getProperties().put("is_demo_user", "true");
		String demo_nocres = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		
		assertEquals(DEMO_NOCRES_C, demo_nocres);
		
		env.getProperties().put("has_custom_resolution", "true");
		String demo_cres = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		
		assertEquals(DEMO_CRES_C, demo_cres);
		
		System.out.println("Game arguments evaluation tested succesfully");
	}
}
