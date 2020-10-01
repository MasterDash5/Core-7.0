package dashnetwork.core.bungee.utils;

import dashnetwork.core.bungee.Core;
import dashnetwork.core.utils.PunishData;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataUtils {

    private static Core plugin = Core.getInstance();
    private static boolean windows = System.getProperty("os.name").startsWith("Windows"); // Fuck you Windows
    private static char slash = windows ? '\\' : '/';
    private static File folder = plugin.getDataFolder();
    private static String path = folder.getPath() + slash;
    private static File ipsFile = new File(path + "ips.yml");
    private static File namesFile = new File(path + "names.yml");
    private static File nicknamesFile = new File(path + "nicknames.yml");
    private static File staffchatFile = new File(path + "staffchat.yml");
    private static File adminchatFile = new File(path + "adminchat.yml");
    private static File ownerchatFile = new File(path + "ownerchat.yml");
    private static File commandspyFile = new File(path + "commandspy.yml");
    private static File altspyFile = new File(path + "altspy.yml");
    private static File pingspyFile = new File(path + "pingspy.yml");
    private static File mutesFile = new File(path + "mutes.yml");
    private static File bansFile = new File(path + "bans.yml");
    private static File ipbansFile = new File(path + "ipbans.yml");

    private static Map<String, List<String>> ips;
    private static Map<String, String> names, nicknames;
    private static Map<String, PunishData> mutes, bans, ipbans;
    private static List<String> staffchat, adminchat, ownerchat, commandspy, altspy, pingspy;

    public static void startup() {
        folder.mkdirs();

        try {
            ips = readFile(ipsFile);
            names = readFile(namesFile);
            nicknames = readFile(nicknamesFile);
            staffchat = readFile(staffchatFile);
            adminchat = readFile(adminchatFile);
            ownerchat = readFile(ownerchatFile);
            commandspy = readFile(commandspyFile);
            altspy = readFile(altspyFile);
            pingspy = readFile(pingspyFile);
            mutes = readFile(mutesFile);
            bans = readFile(bansFile);
            ipbans = readFile(ipbansFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void save() {
        try {
            writeFile(ipsFile, ips);
            writeFile(namesFile, names);
            writeFile(nicknamesFile, nicknames);
            writeFile(staffchatFile, staffchat);
            writeFile(adminchatFile, adminchat);
            writeFile(ownerchatFile, ownerchat);
            writeFile(commandspyFile, commandspy);
            writeFile(altspyFile, altspy);
            writeFile(pingspyFile, pingspy);
            writeFile(mutesFile, mutes);
            writeFile(bansFile, bans);
            writeFile(ipbansFile, ipbans);
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

    public static Map<String, String> getNicknames() {
        return nicknames;
    }

    public static Map<String, PunishData> getMutes() {
        return mutes;
    }

    public static Map<String, PunishData> getBans() {
        return bans;
    }

    public static Map<String, PunishData> getIpbans() {
        return ipbans;
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

        Yaml yaml = new Yaml(new CustomClassLoaderConstructor(Core.class.getClassLoader()));
        FileReader reader = new FileReader(file);

        return yaml.load(reader);
    }

}
