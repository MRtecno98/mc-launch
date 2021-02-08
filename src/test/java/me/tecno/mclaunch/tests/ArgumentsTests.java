package me.tecno.mclaunch.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import me.tecno.mclaunch.global.SystemInfo;
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
	
	private static final String WINDOWS_C = " --username ${auth_player_name} --version ${version_name} "
			+ "--gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} "
			+ "--uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} "
			+ "--versionType ${version_type}";
	
	private static final String LINUX_C = " --username ${auth_player_name} --version ${version_name} "
			+ "--gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} "
			+ "--uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} "
			+ "--versionType ${version_type}";
	
	private static final String OSX_C = " --username ${auth_player_name} --version ${version_name} "
			+ "--gameDir ${game_directory} --assetsDir ${assets_root} --assetIndex ${assets_index_name} "
			+ "--uuid ${auth_uuid} --accessToken ${auth_access_token} --userType ${user_type} "
			+ "--versionType ${version_type}";
	
	private static final String JVM_WINDOWS_C = " -XX:HeapDumpPath=MojangTricksIntelDriversForPerformance"
			+ "_javaw.exe_minecraft.exe.heapdump -Dos.name=Windows 10 -Dos.version=10.0 -Xss1M "
			+ "-Djava.library.path=${natives_directory} -Dminecraft.launcher.brand=${launcher_name} "
			+ "-Dminecraft.launcher.version=${launcher_version} -cp ${classpath}";
	
	private static final String JVM_LINUX_C = " -Djava.library.path=${natives_directory} "
			+ "-Dminecraft.launcher.brand=${launcher_name} "
			+ "-Dminecraft.launcher.version=${launcher_version} -cp ${classpath}";
	
	private static final String JVM_OSX_C = " -XstartOnFirstThread "
			+ "-Djava.library.path=${natives_directory} -Dminecraft.launcher.brand=${launcher_name} "
			+ "-Dminecraft.launcher.version=${launcher_version} -cp ${classpath}";
	
	public static final String TEST_VERSION = "1.16.4";
	
	private final @Getter(lazy=true) Version version = computeVersion();
	
	private Version computeVersion() {
		try {
			for(VersionDataset dt : VersionManifest.getVersionManifest().getVersions())
				if(dt.getId().equals(TEST_VERSION)) return dt.getVersion();
			throw new RuntimeException("Test version not found");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testArgumentsFeaturesEvaluation() throws IOException {
		LaunchEnvironment env = new LaunchEnvironment();
		String nodemo_nocres = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		
		assertEquals(NODEMO_NOCRES_C, nodemo_nocres);
		
		env.getProperties().put("is_demo_user", "true");
		String demo_nocres = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		
		assertEquals(DEMO_NOCRES_C, demo_nocres);
		
		env.getProperties().put("has_custom_resolution", "true");
		String demo_cres = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		
		assertEquals(DEMO_CRES_C, demo_cres);
		
		System.out.println("Game arguments features evaluation tested succesfully");
	}
	
	@Test
	public void testSystemArgumentsEvaluation() throws IOException {
		getVersion();
		
		Map<String, String> backup = System.getProperties().keySet()
			.stream()
			.map(Object::toString)
			.filter((k) -> k.startsWith("os."))
			.collect(Collectors.toMap(Function.identity(), (k) -> System.getProperties().get(k).toString()));
		
		LaunchEnvironment env = new LaunchEnvironment();
		
		emulateSystem("Windows 10", "10.", "x86");
		String windows = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		String jvmWindows = getVersion().getArgumentsIndex().evaluateJVMArguments(env);
		
		assertEquals(WINDOWS_C, windows);
		assertEquals(JVM_WINDOWS_C, jvmWindows);
		
		emulateSystem("Linux", "5.4.0-1019-gcp", "amd64");
		String linux = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		String jvmLinux = getVersion().getArgumentsIndex().evaluateJVMArguments(env);
		
		assertEquals(LINUX_C, linux);
		assertEquals(JVM_LINUX_C, jvmLinux);
		
		emulateSystem("Mac OSX", "5.4.0-1019-gcp", "amd64");
		String osx = getVersion().getArgumentsIndex().evaluateGameArguments(env);
		String jvmOsx = getVersion().getArgumentsIndex().evaluateJVMArguments(env);
		
		assertEquals(OSX_C, osx);
		assertEquals(JVM_OSX_C, jvmOsx);
		
		backup.keySet().forEach((k) -> System.setProperty(k, backup.get(k)));
		
		System.out.println("Game arguments system properties evaluation tested succesfully");
	}
	
	public void emulateSystem(String name, String version, String arch) {
		System.setProperty("os.name", name);
		System.setProperty("os.version", version);
		System.setProperty("os.arch", arch);
		
		SystemInfo.reloadSystemInfo();
	}
}
