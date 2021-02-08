package me.tecno.mclaunch.global;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TransformRegister implements PropertyTransform {
	private List<PropertyTransform> transforms = new ArrayList<>();
	
	static {
		getGlobalRegister().registerTransform(new SystemNameTransform());
	}
	
	public void registerTransform(PropertyTransform transform) {
		getTransforms().add(transform);
	}
	
	@Override
	public String transform(String key, String value) {
		return getTransforms().stream().sequential().reduce(
				value, (v, t) -> t.transform(key, v), 
				(a, b) -> {throw new RuntimeException();});
	}
	
	private static TransformRegister GLOBAL_REGISTER;
	public static TransformRegister getGlobalRegister() {
		if(GLOBAL_REGISTER == null)
			GLOBAL_REGISTER = new TransformRegister();
		return GLOBAL_REGISTER;
	}
}
