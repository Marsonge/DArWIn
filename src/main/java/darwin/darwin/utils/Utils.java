package darwin.darwin.utils;

import java.io.File;
import java.net.URL;
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
    
    public static int wrappingBorderVar(int var, int min, int max, int offset){
    	if(var<min) return max-Math.abs(var)+1;
    	if(var>max) return min+(Math.abs(var)-max)-1;
    	return var;
    }
    
    /**
     * 
     * @param input : The 2D array you want to clone
     * @return A clone of the input
     * 
     * To clone a 2D array, you can't use .clone() : You will only make a new array
     * of references to your subarrays. Not good.
     * Use this instead.
     */
    public static float[][] deepCopyFloatMatrix(float[][] input) {
        if (input == null)
            return null;
        float[][] result = new float[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
    /**
     * Gets a file from the resources folder
     * Care, this is really slow, use it at the beginning of the application
     * @param path the path from inside the resources folder (no src/main/resources/....)
     * @return
     */
    public static URL getResource(String path){
    	ClassLoader classLoader = Utils.class.getClassLoader();
    	URL file = classLoader.getResource(path);
    	return file;
    }
}
