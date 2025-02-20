package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.languages.LoadableMessage;

public enum LanguageEN implements LoadableMessage {

    PLACEHOLDER_NO_TOP_PLAYER("placeholder.noTopPlayer", "-"),
    PLACEHOLDER_NO_TOP_TEAM("placeholder.noTopTeam", "-"),

    MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Please remove the following mods: %colorcode%%mods%"),
    MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
    MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "%colorcode%%player% &7tried to join while having the following mods installed: %colorcode%%mods%");

    private String path, message;

    private LanguageEN(String path, String message) {
        this.path = path;
        this.message = message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getDefaultMessage() {
        return message;
    }
}
