package de.cuuky.varo.command.varo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.cfw.configuration.placeholder.MessagePlaceholder;
import de.cuuky.cfw.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.cfw.configuration.placeholder.placeholder.type.MessagePlaceholderType;
import de.cuuky.cfw.configuration.placeholder.placeholder.type.PlaceholderType;
import de.cuuky.cfw.utils.chat.PageableChat;
import de.cuuky.cfw.utils.chat.PageableChatBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroChatListMessages;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;

public class PlaceholderCommand extends VaroCommand {
    private static final String[] subCommands = {"info", "general", "player"};
    private final PageableChatBuilder<MessagePlaceholder> listBuilder;

    public PlaceholderCommand() {
        super("placeholder", "Zeigt alle Platzhalter fuer messages, scoreboard etc.", "varo.placeholder", subCommands, "ph");

        this.listBuilder = new PageableChatBuilder<MessagePlaceholder>()
                .messages(new VaroChatListMessages<>(mp ->
                        Main.getPrefix() + Main.getColorCode() + mp.getIdentifier() + " §8- §7" + mp.getDescription(),
                        "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " placeholder <general/player>", "List der Placeholder"));
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Placeholder Befehle:");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " placeholder §7info <name> §8- §7Zeigt Wert und Info vom gegebenen Placeholder");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " placeholder §7general §8- §7Zeigt alle ueberall anwendbaren Placeholder");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " placeholder §7player §8- §7Zeigt alle im Spielerkontext anwendbaren Placeholder");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + "Player-Beispiele: Killmessage, Scoreboard, Kickmessage, Tab");
            return;
        }

        if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("get")) {
            if (args.length != 2) {
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " placeholder §7get <name> §8- §7Zeigt Wert vom gegebenen Placeholder");
                return;
            }

            MessagePlaceholder mp = null;
            for (MessagePlaceholder mp1 : Main.getCuukyFrameWork().getPlaceholderManager().getAllPlaceholders())
                if (mp1.getIdentifier().replace("%", "").equalsIgnoreCase(args[1].replace("%", "")))
                    mp = mp1;

            if (mp == null) {
                sender.sendMessage(Main.getPrefix() + "Placeholder nicht gefunden!");
                return;
            }

            String value = "/";
            if (!(mp instanceof PlayerMessagePlaceholder) || vp == null)
                value = "(" + vp.getName() + ") " + mp.replacePlaceholder(mp.getIdentifier(), vp);

            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + mp.getIdentifier() + " §7Info§8:");
            sender.sendMessage(Main.getPrefix() + "§7Wert§8: " + Main.getColorCode() + value);
            sender.sendMessage(Main.getPrefix() + "§7Refresh-Delay§8: " + Main.getColorCode() + mp.getDefaultRefresh() + "s");
            return;
        }

        PlaceholderType type;
        if (args[0].equalsIgnoreCase("player"))
            type = MessagePlaceholderType.OBJECT;
        else
            try {
                type = MessagePlaceholderType.valueOf(args[0].toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                sender.sendMessage(Main.getPrefix() + "Falsche Argumente! §c/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " ph");
                return;
            }

        PageableChat<?> chat = this.listBuilder.list(() -> Main.getCuukyFrameWork().getPlaceholderManager().getPlaceholders(type))
                .page(args.length >= 2 ? args[1] : "1").build();
        chat.send(sender);

        if (type == MessagePlaceholderType.GENERAL && chat.getPage() == 1) {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topplayer-<RANK>% §8- §7Ersetzt durch den Spieler, der an RANK auf dem Leaderboard ist");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topplayerkills-<RANK>% §8- §7Ersetzt durch die Kills des Spielers, der an RANK auf dem Leaderboard ist");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topteam-<RANK>% §8- §7Ersetzt durch das Team, das an RANK auf dem Leaderboard ist");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "%topteamkills-<RANK>% §8- §7Ersetzt durch die Kills des Teams, das an RANK auf dem Leaderboard ist");
            sender.sendMessage(Main.getPrefix() + "Zusätzlich werden alle Einstellungen mit %<ConfigEintrag>% ersetzt");
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 2 && subCommands != null) {
            List<String> subCommands = Arrays.asList(this.subCommands);
            list.addAll(subCommands);
        }
        ArrayList<String> completerList = new ArrayList<>();
        String curentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(curentarg)) {
                completerList.add(s);
            }
        }
        return completerList;
    }
}