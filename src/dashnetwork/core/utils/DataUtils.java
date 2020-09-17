package dashnetwork.core.utils;

import dashnetwork.core.Core;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataUtils {

    private static Core plugin = Core.getInstance();
    private static boolean windows = System.getProperty("os.name").startsWith("Windows"); // Fuck you Windows
    private static char slash = windows ? '\\' : '/';
    private static File folder = plugin.getDataFolder();
    private static String path = folder.getPath() + slash;
    private static File ipsFile = new File(path + "ips.yml");
    private static File namesFile = new File(path + "names.yml");
    private static File staffchatFile = new File(path + "staffchat.yml");
    private static File adminchatFile = new File(path + "adminchat.yml");
    private static File ownerchatFile = new File(path + "ownerchat.yml");
    private static File commandspyFile = new File(path + "commandspy.yml");
    private static File altspyFile = new File(path + "altspy.yml");
    private static File pingspyFile = new File(path + "pingspy.yml");

    private static Map<String, List<String>> ips;
    private static Map<String, String> names;
    private static List<String> staffchat;
    private static List<String> adminchat;
    private static List<String> ownerchat;
    private static List<String> commandspy;
    private static List<String> altspy;
    private static List<String> pingspy;

    public static void startup() {
        try {
            folder.mkdirs();

            ips = readFile(ipsFile);
            names = readFile(namesFile);
            staffchat = readFile(staffchatFile);
            adminchat = readFile(adminchatFile);
            ownerchat = readFile(ownerchatFile);
            commandspy = readFile(commandspyFile);
            altspy = readFile(altspyFile);
            pingspy = readFile(pingspyFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void save() {
        try {
            writeFile(ipsFile, ips);
            writeFile(namesFile, names);
            writeFile(staffchatFile, staffchat);
            writeFile(adminchatFile, adminchat);
            writeFile(ownerchatFile, ownerchat);
            writeFile(commandspyFile, commandspy);
            writeFile(altspyFile, altspy);
            writeFile(pingspyFile, pingspy);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Map<String, List<String>> getIps() {
        return ips;
    }

    public static Map<String, String> getNames() {
        return names;
    }

    public static List<String> getStaffchat() {
        return staffchat;
    }

    public static List<String> getAdminchat() {
        return adminchat;
    }

    public static List<String> getOwnerchat() {
        return ownerchat;
    }

    public static List<String> getCommandspy() {
        return commandspy;
    }

    public static List<String> getAltspy() {
        return altspy;
    }

    public static List<String> getPingspy() {
        return pingspy;
    }

    private static void writeFile(File file, Object data) throws IOException {
        file.createNewFile();

        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(file);

        yaml.dump(data, writer);
    }

    private static <T>T readFile(File file) throws IOException {
        file.createNewFile();

        Yaml yaml = new Yaml();
        FileReader reader = new FileReader(file);

        return yaml.load(reader);
    }

}
