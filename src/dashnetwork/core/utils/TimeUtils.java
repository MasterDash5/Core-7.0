package dashnetwork.core.utils;

public class TimeUtils {
	
	public static final long ONE_TICK = 50L, ONE_SECOND = 1000L, ONE_DAY = 86400000L, ONE_MINUTE = ONE_SECOND * 60L, ONE_HOUR = ONE_MINUTE * 60L, ONE_MONTH = ONE_DAY * 30L, ONE_WEEK = ONE_DAY * 7L, ONE_YEAR = ONE_MONTH * 12;
	
	public static String millisecondsToWords(long duration) {
		StringBuilder builder = new StringBuilder();
		long temp = duration / ONE_YEAR;

        if (temp > 0L) {
            duration -= temp * ONE_YEAR;
            builder.append(temp).append(" year").append(temp > 1L ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
        }

        temp = duration / ONE_MONTH;

        if (temp > 0L) {
            duration -= temp * ONE_MONTH;
            builder.append(temp).append(" month").append(temp > 1L ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
        }

        temp = duration / ONE_DAY;

        if (temp > 0L) {
            duration -= temp * ONE_DAY;
            builder.append(temp).append(" day").append(temp > 1L ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
        }

        temp = duration / ONE_HOUR;

        if (temp > 0L) {
            duration -= temp * ONE_HOUR;
            builder.append(temp).append(" hour").append(temp > 1L ? "s" : "").append(duration >= ONE_MINUTE ? ", " : "");
        }

        temp = duration / ONE_MINUTE;

        if (temp > 0L) {
            duration -= temp * ONE_MINUTE;
            builder.append(temp).append(" minute").append(temp > 1L ? "s" : "");
        }

        if (!builder.toString().equals("") && duration >= ONE_SECOND)
            builder.append(" and ");

        temp = duration / ONE_SECOND;

        if (temp > 0L)
            builder.append(temp).append(" second").append(temp > 1L ? "s" : "");

        return builder.toString();
	}
	
	// Examples: 1w, 2d, 7y, etc
    public static Long fromTimeArgument(String argument) {
        argument = argument.toLowerCase();

        String timeString = "";
        Long unit = 0L;

        if (!argument.endsWith("d") && !argument.endsWith("w") && !argument.endsWith("s") && !argument.endsWith("y") && !argument.endsWith("m") && !argument.endsWith("h"))
            return null;

        if (argument.endsWith("d")) {
            timeString = argument.replace("d", "");

            if (!timeString.equals(timeString.toUpperCase()))
                return null;

            unit = ONE_DAY;
        }

        if (argument.endsWith("w")) {
            timeString = argument.replace("w", "");

            if (!timeString.equals(timeString.toUpperCase()))
                return null;

            unit = ONE_WEEK;
        }

        if (argument.endsWith("s")) {
            timeString = argument.replace("s", "");

            if (!timeString.equals(timeString.toUpperCase()))
                return null;

            unit = ONE_SECOND;
        }

        if (argument.endsWith("y")) {
            timeString = argument.replace("y", "");

            if (!timeString.equals(timeString.toUpperCase()))
                return null;

            unit = ONE_YEAR;
        }

        if (argument.endsWith("m")) {
            timeString = argument.replace("m", "");

            if (!timeString.equals(timeString.toUpperCase()))
                return null;

            unit = ONE_MINUTE;
        }

        if (argument.endsWith("h")) {
            timeString = argument.replace("h", "");

            if (!timeString.equals(timeString.toUpperCase()))
                return null;

            unit = ONE_HOUR;
        }

        return unit * Long.parseLong(timeString);
    }
}
