package dashnetwork.core.bungee.command.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dashnetwork.core.bungee.command.CoreCommand;
import dashnetwork.core.bungee.utils.*;
import dashnetwork.core.utils.MapUtils;
import dashnetwork.core.bungee.utils.MojangUtils;
import dashnetwork.core.utils.PlayerProfile;
import dashnetwork.core.utils.Username;
import net.md_5.bungee.api.CommandSender;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommandNamemc extends CoreCommand {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("LLL d, yyyy");

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
                    MessageUtils.noPlayerFound(sender);
                    return;
                }
            }

            Map<String, String> nameMap = new HashMap<>();

            Username[] usernames = MojangUtils.getNameHistoryFromUuid(uuid);
            PlayerProfile profile = MojangUtils.getProfileFromUuid(uuid);

            for (Username username : usernames)
                nameMap.put(username.getName(), dateFormat.format(new Date(username.getChangedAt())));

            name = profile.getName();

            JsonObject object = new JsonParser().parse(new String(Base64.getDecoder().decode(profile.getSkin()))).getAsJsonObject();
            JsonObject textures = object.get("textures").getAsJsonObject();

            String skin = "";

            if (textures.has("SKIN"))
                skin = textures.get("SKIN").getAsJsonObject().get("url").getAsString();
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return CompletionUtils.players(args[0]);
        return Collections.EMPTY_LIST;
    }

}
