package me.tecno.mclaunch.global;

public class SystemNameTransform implements PropertyTransform {
	@Override
	public String transform(String key, String value) {
		if(!key.equals("os.name")) return null;
		
		String osname = value.toLowerCase();
	    if (osname.startsWith("windows"))
	    	return "windows";
	    else if (osname.startsWith("linux"))
	    	return "linux";
	    else if (osname.startsWith("sunos"))
	    	return "solaris";
	    else if (osname.startsWith("mac") 
	    		|| osname.startsWith("darwin"))
	    	return "osx";
	    else return "generic";
	}
}
