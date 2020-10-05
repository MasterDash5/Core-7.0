package dashnetwork.core.utils;

public class EnumUtils {

    public static String toName(Enum input) {
        String[] split = input.toString().split("_");
        StringBuilder builder = new StringBuilder();

        for (String each : split) {
            if (builder.length() > 0)
                builder.append(" ");
            builder.append(GrammarUtils.capitalization(each));
        }

        return builder.toString();
    }

}
