package de.cuuky.varo.configuration.configurations.language;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.LanguageManager;
import de.cuuky.cfw.configuration.language.broadcast.MessageHolder;
import de.cuuky.cfw.configuration.language.languages.LoadableMessage;
import de.cuuky.cfw.configuration.placeholder.MessagePlaceholderManager;
import de.cuuky.cfw.configuration.placeholder.placeholder.type.MessagePlaceholderType;
import de.cuuky.cfw.player.CustomLanguagePlayer;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.PlaceholderAPIAdapter.VaroPlaceholderExpansion;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.configuration.configurations.language.languages.LanguageEN;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.VaroTeam;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class VaroLanguageManager extends LanguageManager {

    private static final String PATH_DIR = "plugins/Varo/languages", FALLBACK_LANGUAGE = "de_de";

    private PlaceholderAPIAdapter placeholderAPIAdapter;

    public VaroLanguageManager(JavaPlugin instance) {
        super(PATH_DIR, FALLBACK_LANGUAGE, instance);

        if (instance.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            this.placeholderAPIAdapter = new PlaceholderAPIAdapter();
            new VaroPlaceholderExpansion().register();
        }

        loadLanguages();
    }

    @Override
    public void loadLanguages() {
        registerLoadableLanguage("de_de", ConfigMessages.class);
        registerLoadableLanguage("en_us", LanguageEN.class);

        super.loadLanguages();

        Language lang = getLanguages().get(ConfigSetting.MAIN_LANGUAGE.getValueAsString());
        if (lang == null || lang.getClazz() == null)
            throw new NullPointerException("Couldn't find language '" + ConfigSetting.MAIN_LANGUAGE.getValueAsString() + "'");

        setDefaultLanguage(lang);
    }

    @Override
    public String getMessage(String messagePath, String locale) {
        return super.getMessage(messagePath, !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean() ? getDefaultLanguage().getName() : locale);
    }

    private List<Integer> getConvNumbers(String line, String key) {
        ArrayList<Integer> list = new ArrayList<>();

        boolean first = true;
        for (String split0 : line.split(key)) {
            if (first) {
                first = false;
                if (!line.startsWith(key))
                    continue;
            }

            String[] split1 = split0.split("%", 2);

            if (split1.length == 2) {
                try {
                    list.add(Integer.parseInt(split1[0]));
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return list;
    }

    public String replaceMessage(String message, boolean replaceEval) {
        String replaced = message;

        if (Main.getVaroGame() != null) {
	        for (int rank : getConvNumbers(replaced, "%topplayer-")) {
	            VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
                replaced = replaced.replace("%topplayer-" + rank + "%", (player == null ? ConfigMessages.PLACEHOLDER_NO_TOP_PLAYER.getValue() : player.getName()));
	        }

	        for (int rank : getConvNumbers(replaced, "%topplayerkills-")) {
	            VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
	            replaced = replaced.replace("%topplayerkills-" + rank + "%", (player == null ? "0" : String.valueOf(player.getStats().getKills())));
	        }

	        for (int rank : getConvNumbers(replaced, "%topteam-")) {
	            VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
                replaced = replaced.replace("%topteam-" + rank + "%", (team == null ? ConfigMessages.PLACEHOLDER_NO_TOP_TEAM.getValue() : team.getName()));
	        }

	        for (int rank : getConvNumbers(replaced, "%topteamkills-")) {
	            VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
	            replaced = replaced.replace("%topteamkills-" + rank + "%", (team == null ? "0" : String.valueOf(team.getKills())));
	        }
        }

        replaced = Main.getCuukyFrameWork().getPlaceholderManager().replacePlaceholders(replaced, MessagePlaceholderType.GENERAL);
        if (replaceEval) {
            if (this.placeholderAPIAdapter != null)
                replaced = this.placeholderAPIAdapter.setGeneralPlaceholders(replaced);
        }

        return replaced;
    }

    public String replaceMessage(String message) {
        return replaceMessage(message, false);
    }

    public String replaceMessage(String message, CustomPlayer player) {
        return replaceMessage(message, player, true);
    }
    
    public String replaceMessage(String message, CustomPlayer player, boolean replacePapi) {
        message = Main.getCuukyFrameWork().getPlaceholderManager().replacePlaceholders(replaceMessage(message, false), MessagePlaceholderType.OBJECT, player);
        if (replacePapi && this.placeholderAPIAdapter != null && player instanceof VaroPlayer)
            return this.placeholderAPIAdapter.setPlayerPlaceholders(message, (VaroPlayer) player);
        return message;
    }

    // Copied from legacy CFW
    @Override
    public MessageHolder broadcastMessage(LoadableMessage message, CustomPlayer replace, MessagePlaceholderManager placeholderManager, ArrayList<CustomLanguagePlayer> players) {
        MessageHolder holder = new MessageHolder(placeholderManager);
        new BukkitRunnable() {
            @Override
            public void run() {
                String consoleMessage = holder.getReplaced(getMessage(message.getPath(), getDefaultLanguage().getName()), replace);
                if (!consoleMessage.isEmpty())
                    ownerInstance.getServer().getConsoleSender().sendMessage(consoleMessage);
                for (CustomLanguagePlayer player : players)
                    if (player.getPlayer() != null) {
                        String playerMessage = holder.getReplaced(getMessage(message.getPath(), ((CustomPlayer) player).getLocale()), replace != null ? replace : (CustomPlayer) player);
                        if (!playerMessage.isEmpty())
                            player.getPlayer().sendMessage(playerMessage);
                    }
            }
        }.runTaskLater(ownerInstance, 1L);

        return holder;
    }
    
    public MessageHolder broadcastMessage(LoadableMessage message, CustomPlayer replace) {
        ArrayList<CustomLanguagePlayer> players = new ArrayList<>(VaroPlayer.getVaroPlayers());
        
        return broadcastMessage(message, replace, Main.getCuukyFrameWork().getPlaceholderManager(), players);
    }

    public MessageHolder broadcastMessage(LoadableMessage message) {
        return this.broadcastMessage(message, null);
    }
}