package de.cuuky.varo.command.custom;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CustomCommand extends VaroCommand {
    private boolean unused;
    private String output;

    public CustomCommand(String trigger, String output, String description, String permission, boolean unused) {
        super(trigger, description, permission);

        this.output = output;
        this.unused = unused;
    }

    private void removeConfigEntry() {
        Main.getDataManager().getCustomCommandManager().getConfig().set(this.getName(), null);
        this.save();
    }

    public void setConfigEntry() {
        YamlConfiguration config = Main.getDataManager().getCustomCommandManager().getConfig();
        config.set(this.getName() + "." + "output", this.output);
        config.set(this.getName() + "." + "description", this.getDescription());
        config.set(this.getName() + "." + "permission", String.valueOf(this.getPermission()));
        config.set(this.getName() + "." + "deactivated", this.unused);
    }

    public void removeCommand() {
        this.remove();
        this.removeConfigEntry();
    }

    public void renameCommand(String newName) {
        this.removeConfigEntry();
        this.setName(newName);
        this.setConfigEntry();
    }

    public void editOutput(String newOutput) {
        this.output = newOutput;
        this.setConfigEntry();
    }

    public void editDescription(String newDescription) {
        this.setDescription(newDescription);
        this.setConfigEntry();
    }

    public void setUnused(boolean unused) {
        this.unused = unused;
        this.setConfigEntry();
    }

    public void save() { Main.getDataManager().getCustomCommandManager().save(); }

    public void editPermission(String newPermission) {
        this.setPermission(newPermission);
        this.setConfigEntry();
    }

    public String getOutput() {
        return output;
    }

    public boolean isUnused() { return unused; }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            String message = String.format("%s%s", (ConfigSetting.CUSTOMCOMMAND_USEPREFIX.getValueAsBoolean()) ? Main.getPrefix() : "", this.output);
            message = Main.getLanguageManager().replaceMessage(message, vp);
            vp.sendMessage(message);
        } else sender.sendMessage(Main.getConsolePrefix() + "Du musst ein Spieler sein!");
    }
}