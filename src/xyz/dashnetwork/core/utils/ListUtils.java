package xyz.dashnetwork.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUtils {

    public static <T>boolean isEqual(List<T> one, List<T> two) {
        if (one == null && two == null)
            return true;
        else if ((one == null && two != null) || (one != null && two == null) || (one.size() != two.size()))
            return false;

        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        one.sort(null);
        two.sort(null);

        return one.equals(two);
    }

    public static <T>boolean containsOtherThan(Collection<T> collection, T contains) {
        return collection.size() > (collection.contains(contains) ? 1 : 0);
    }

}
