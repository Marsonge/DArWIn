package utils;

import java.util.Random;

public class Utils {
    public static <T extends Enum<?>> T randomEnum(Class<T> className){ //Gives a random component of an enum.
        int x = new Random().nextInt(className.getEnumConstants().length); //Use : Utils.randomEnum(MyClass.class);
        return className.getEnumConstants()[x];
    }
}
