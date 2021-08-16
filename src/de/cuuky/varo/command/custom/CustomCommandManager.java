package de.cuuky.varo.command.custom;

import de.cuuky.varo.command.VaroCommand;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomCommandManager {

    private static File file;
    private static YamlConfiguration config;

    public CustomCommandManager() {
        this.reloadConfig();
        this.loadCommands();
    }

    private void removeCustoms() {
        new ArrayList<>(VaroCommand.getVaroCommand()).stream()
                .filter(vc -> vc instanceof CustomCommand).forEach(VaroCommand::remove);
    }

    private void loadCommands() {
        this.removeCustoms();
        if (!file.exists()) {
            save();
        } else {
            for (String key : config.getKeys(false)) {
                String output = config.getString(key + "." + "output");
                String description = config.getString(key + "." + "description");
                String permission = config.getString(key + "." + "permission");
                boolean unused = Boolean.parseBoolean(config.getString(key + "." + "deactivated"));
                new CustomCommand(key, output, description, permission, unused);
            }
        }
    }

    private void reloadConfig() {
        file = new File("plugins/Varo/config", "customcommands.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        this.reloadConfig();
        save();
        this.loadCommands();
    }

    public boolean isIllegalCommand(String command) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("create", "edit", "help", "info", "list", "menu", "remove"));
        return VaroCommand.getCommand(command) != null || command.contains(" ") || list.contains(command);
    }

    public void reloadSave() {
        reloadConfig();
        save();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public List<CustomCommand> getCommands() {
        return VaroCommand.getVaroCommand().stream().filter(vc -> vc instanceof CustomCommand)
                .map(vc -> (CustomCommand) vc).collect(Collectors.toList());
    }

    public CustomCommand getCommand(String command) {
        VaroCommand vc = VaroCommand.getCommand(command);
        return vc instanceof CustomCommand ? (CustomCommand) vc : null;
    }
}