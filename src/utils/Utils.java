package utils;

import java.util.Random;

public class Utils {
	/**
	 *  
	 * @param className
	 * @return a random enum in a list of enum
	 */
    public static <T extends Enum<?>> T randomEnum(Class<T> className){ //Gives a random component of an enum.
        int x = new Random().nextInt(className.getEnumConstants().length); //Use : Utils.randomEnum(MyClass.class);
        return className.getEnumConstants()[x];
    }
    
    public static int borderVar(int var, int min, int max, int offset){
		if(var<min) return min+offset;
		if(var>max) return max-offset;
		return var;
	}
}
