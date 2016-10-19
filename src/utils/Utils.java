package utils;

import java.util.Random;

public class Utils {
    public static <T extends Enum<?>> T randomEnum(Class<T> className){
        int x = new Random().nextInt(className.getEnumConstants().length);
        return className.getEnumConstants()[x];
    }
}
