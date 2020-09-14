package dashnetwork.core.utils;

import java.util.Collection;

public class LazyUtils {

    public static <T>boolean anyEquals(T object, T... equals) {
        for (T equalsObject : equals)
            if (object.equals(equalsObject))
                return true;
        return false;
    }

    public static boolean anyEqualsIgnoreCase(String string, String... equals) {
        for (String equalsObject : equals)
            if (string.equalsIgnoreCase(equalsObject))
                return true;
        return false;
    }

    public static boolean anyStartsWith(String string, String... starts) {
        for (String startsObject : starts)
            if (string.startsWith(startsObject))
                return true;
        return false;
    }

    public static boolean anyEndsWith(String string, String... ends) {
        for (String endsObject : ends)
            if (string.endsWith(endsObject))
                return true;
        return false;
    }

    public static boolean anyContains(String string, String... contains) {
        for (String containsObject : contains)
            if (string.contains(containsObject))
                return true;
        return false;
    }

    public static <T>boolean anyContains(Collection<T> collection, T... contains) {
        for (T object : contains)
            if (collection.contains(object))
                return true;
        return false;
    }

}
