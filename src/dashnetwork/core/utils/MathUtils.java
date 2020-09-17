package dashnetwork.core.utils;

public class MathUtils {

    public static double getDistance(double double1, double double2) {
        if (double1 > double2)
            return double1 - double2;

        return double2 - double1;
    }

    public static float getDistance(float float1, float float2) {
        if (float1 > float2)
            return float1 - float2;

        return float2 - float1;
    }

    public static int getDistance(int int1, int int2) {
        if (int1 > int2)
            return int1 - int2;

        return int2 - int1;
    }

    public static long getDistance(long long1, long long2) {
        if (long1 > long2)
            return long1 - long2;

        return long2 - long1;
    }

    public static int getLargestInt(int... numbers) {
        Integer largest = null;

        for (int number : numbers)
            if (largest == null || number > largest)
                largest = number;

        return largest;
    }

}
