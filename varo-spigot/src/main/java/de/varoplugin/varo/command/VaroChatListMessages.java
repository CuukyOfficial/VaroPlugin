package de.varoplugin.varo.command;

import de.varoplugin.cfw.chat.DefaultChatMessageSupplier;
import de.varoplugin.cfw.chat.PageableChat;
import de.varoplugin.varo.Main;

import java.util.function.Function;

public class VaroChatListMessages<T> extends DefaultChatMessageSupplier<T> {

    private final String commandName, title;

    public VaroChatListMessages(Function<T, String> entryFunction, String commandName, String title) {
        super(entryFunction);

        this.commandName = commandName;
        this.title = title;
    }

    @Override
    public String getTitle(PageableChat<T> chat) {
        return Main.getPrefix() + "§8--- " + Main.getColorCode() + title + " §7("
                + Main.getColorCode() + chat.getPage() + "§7/" + Main.getColorCode() + chat.getMaxPage() + "§7) §8---";
    }

    @Override
    public String getFooter(PageableChat<T> chat) {
        return Main.getPrefix() + (chat.getPage() == chat.getMaxPage()
                ? "§8------" : commandName + " " + (chat.getPage() + 1));
    }

    @Override
    public String getInvalidPage(int wrongPage) {
        return Main.getPrefix() + "Seitenzahl falsch!";
    }

    @Override
    public String getNoEntriesFound() {
        return Main.getPrefix() + "Keine Einträge gefunden!";
    }
}