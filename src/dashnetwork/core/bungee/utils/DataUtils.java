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

    private static File folder = new File(Core.getInstance().getDataFolder(), "data");
    private static File ipsFile = new File(folder, "ips.yml");
    private static File namesFile = new File(folder, "names.yml");
    private static File lastplayedFile = new File(folder, "lastplayed.yml");
    private static File nicknamesFile = new File(folder, "nicknames.yml");
    private static File staffchatFile = new File(folder, "staffchat.yml");
    private static File adminchatFile = new File(folder, "adminchat.yml");
    private static File ownerchatFile = new File(folder, "ownerchat.yml");
    private static File commandspyFile = new File(folder, "commandspy.yml");
    private static File altspyFile = new File(folder, "altspy.yml");
    private static File pingspyFile = new File(folder, "pingspy.yml");
    private static File mutesFile = new File(folder, "mutes.yml");
    private static File bansFile = new File(folder, "bans.yml");
    private static File ipbansFile = new File(folder, "ipbans.yml");

    private static Map<String, List<String>> ips;
    private static Map<String, String> names, nicknames;
    private static Map<String, Long> lastplayed;
    private static Map<String, PunishData> mutes, bans, ipbans;
    private static List<String> staffchat, adminchat, ownerchat, commandspy, altspy, pingspy;

    public static void startup() {
        folder.mkdirs();

        try {
            ips = readFile(ipsFile);
            names = readFile(namesFile);
            lastplayed = readFile(lastplayedFile);
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

        if (ips == null)
            ips = new HashMap<>();

        if (names == null)
            names = new HashMap<>();

        if (lastplayed == null)
            lastplayed = new HashMap<>();

        if (nicknames == null)
            nicknames = new HashMap<>();

        if (staffchat == null)
            staffchat = new ArrayList<>();

        if (adminchat == null)
            adminchat = new ArrayList<>();

        if (ownerchat == null)
            ownerchat = new ArrayList<>();

        if (commandspy == null)
            commandspy = new ArrayList<>();

        if (altspy == null)
            altspy = new ArrayList<>();

        if (pingspy == null)
            pingspy = new ArrayList<>();

        if (mutes == null)
            mutes = new HashMap<>();

        if (bans == null)
            bans = new HashMap<>();

        if (ipbans == null)
            ipbans = new HashMap<>();
    }

    public static void save() {
        try {
            writeFile(ipsFile, ips);
            writeFile(namesFile, names);
            writeFile(lastplayedFile, lastplayed);
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

    public static Map<String, Long> getLastplayed() {
        return lastplayed;
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
