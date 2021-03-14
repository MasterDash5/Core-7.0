package xyz.dashnetwork.core.bungee.command.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.CommandSender;
import xyz.dashnetwork.core.bungee.Core;
import xyz.dashnetwork.core.bungee.command.CoreCommand;
import xyz.dashnetwork.core.bungee.utils.*;
import xyz.dashnetwork.core.utils.MapUtils;
import xyz.dashnetwork.core.utils.PlayerProfile;
import xyz.dashnetwork.core.utils.Username;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommandNamemc extends CoreCommand {

    private SimpleDateFormat formatter = new SimpleDateFormat("LLL d, yyyy");

    public CommandNamemc() {
        super(true, PermissionType.STAFF, "namemc", "lookup");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        String name;
        UUID uuid;

        try {
            uuid = UUID.fromString(args[0]);
        } catch (IllegalArgumentException exception) {
            uuid = UUID.fromString(MapUtils.getKeyFromValue(DataUtils.getNames(), args[0]));

            if (uuid == null) {
                uuid = MojangUtils.getUuidFromName(args[0]).getUuid();

                if (uuid == null) {
                    Messages.noPlayerFound(sender);
                    return;
                }
            }
        }

        Map<String, String> nameMap = new HashMap<>();
        PlayerProfile profile = MojangUtils.getProfileFromUuid(uuid);
        Username[] usernames = MojangUtils.getNameHistoryFromUuid(uuid);

        name = profile.getName();

        for (Username username : usernames) {
            String date;
            long changed = username.getChangedAt();

            if (changed == -1)
                date = "???";
            else
                date = formatter.format(new Date(changed));

            nameMap.put(username.getName(), date);
        }

        JsonObject object = new JsonParser().parse(new String(Base64.getDecoder().decode(profile.getSkin()))).getAsJsonObject();
        JsonObject textures = object.get("textures").getAsJsonObject();

        BufferedImage head;
        boolean fromUuid = true;

        if (textures.has("SKIN")) {
            String url = textures.get("SKIN").getAsJsonObject().get("url").getAsString();

            try {
                head = ImageIO.read(new URL(url)).getSubimage(8, 8, 8, 8);
                fromUuid = false;
            } catch (Exception e) {}
        }

        if (fromUuid) {
            boolean steve = uuid.hashCode() % 2 == 0;
            String png = steve ? "assets/steve.png" : "assets/alex.png";

            try {
                head = ImageIO.read(Core.class.getClassLoader().getResource(png));
            } catch (Exception e) {} // This shouldn't happen
        }

        // TODO: Send a message
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(sender, args[0]);
        return Collections.EMPTY_LIST;
    }

}
