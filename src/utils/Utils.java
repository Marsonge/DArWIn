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
}
