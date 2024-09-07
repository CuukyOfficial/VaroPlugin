package de.cuuky.varo.gui.admin.customcommands;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.custom.CustomCommand;
import de.cuuky.varo.gui.VaroInventory;
import de.varoplugin.cfw.inventory.ItemClick;
import de.varoplugin.cfw.inventory.inbuilt.ConfirmInventory;
import de.varoplugin.cfw.item.ItemBuilder;
import de.varoplugin.cfw.player.hook.chat.ChatHookTriggerEvent;
import de.varoplugin.cfw.player.hook.chat.PlayerChatHookBuilder;

public class CreateCustomCommandGUI extends VaroInventory {

    private String guiName = "Create Command";
    private boolean unsaved;

    private CustomCommand command;
    private String name = "", output = "", description = "", permission;
    private boolean unused = false;

    public CreateCustomCommandGUI(Player player) {
        super(Main.getInventoryManager(), player);

        this.permission = "null";
    }

    public CreateCustomCommandGUI(Player player, CustomCommand command) {
        super(Main.getInventoryManager(), player);

        this.command = command;
        this.name = command.getName();
        this.output = command.getOutput();
        this.description = command.getDescription();
        this.permission = String.valueOf(command.getPermission());
        this.guiName = "Edit '" + this.name + "':";
    }

    private ItemClick getInput(String current, String inputName, Consumer<String> resultReceiver) {
        return this.getInput(current, inputName, resultReceiver, (result -> result));
    }

    private ItemClick getInput(String current, String inputName, Consumer<String> resultReceiver, Function<String, String> checker) {
        return (event) -> {
            new PlayerChatHookBuilder().message("§7Gib den/die " + inputName + " ein:").subscribe(ChatHookTriggerEvent.class, hookEvent -> {
                String result = checker.apply(hookEvent.getMessage());
                if (result == null)
                    return;

                resultReceiver.accept(result);
                getPlayer().sendMessage(Main.getPrefix() + ChatColor.GRAY + "Der/Die " + inputName + " wurde auf '" + Main.getColorCode() + result + "§7' gesetzt");
                unsaved = !current.equals(result);
                open();
                hookEvent.getHook().unregister();
            }).complete(getPlayer(), Main.getInstance());
            this.close();
        };
    }

    @Override
    public String getTitle() {
        return guiName + ((this.unsaved || this.command == null) ? ChatColor.DARK_GRAY + " (" + ChatColor.DARK_RED + "unsaved!" + ChatColor.DARK_GRAY + ")" : "");
    }

    @Override
    public int getSize() {
        return 9 * 4;
    }

    @Override
    public void refreshContent() {
        addItem(9, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(ChatColor.DARK_PURPLE + "Edit Name").lore(new String[]{this.name}).build(),
                this.getInput(this.name, "Command Name", (String x) -> this.name = x, (result) -> {
                    result = result.toLowerCase(Locale.ROOT);
                    if (Main.getDataManager().getCustomCommandManager().isIllegalCommand(result)) {
                        if (command == null || !command.getName().equalsIgnoreCase(result)) {
                            getPlayer().sendMessage(Main.getPrefix() + "Dieser Name kann nicht benutzt werden." + " Bitte erneut eingeben!");
                            return null;
                        }
                    }
                    return result;
                })
        );

        addItem(11, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(ChatColor.DARK_PURPLE + "Edit Output").lore(this.output).build(),
                this.getInput(this.output,"Command Output", (String x) -> this.output = x)
        );

        addItem(13, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(ChatColor.DARK_PURPLE + "Edit Description").lore(this.description).build(), 
                this.getInput(this.description, "Command Description", (String x) -> this.description = x)
        );

        addItem(15, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(ChatColor.DARK_PURPLE + "Edit Permission").lore(this.permission).build(),
                this.getInput(this.permission, "Command Permission", (String x) -> this.permission = x)
        );

        addItem(17, ItemBuilder.material(XMaterial.OAK_SIGN).displayName(ChatColor.DARK_PURPLE + "Set Unused").lore(String.valueOf(this.unused)).build(),
                this.getInput(String.valueOf(this.unused), "Unused State", (String x) -> this.unused = Boolean.parseBoolean(x), (result) -> {
                    result = result.toLowerCase(Locale.ROOT);
                    if (!result.equals("true") && !result.equals("false")) {
                        getPlayer().sendMessage(Main.getPrefix() + "Ungültige Eingabe! (Die Eingabe muss \"true\" oder \"false\" sein)" + " Bitte erneut eingeben!");
                        return null;
                    }
                    return result;
                })
        );

        addItem(9 * 4 - 1, ItemBuilder.material(XMaterial.EMERALD).displayName(ChatColor.DARK_PURPLE + "Save").build(), (event) -> {

            if (!this.isReady()) {
                event.getWhoClicked().sendMessage(Main.getPrefix() + "Bitte ändere den Namen!");
                return;
            }

            this.openNext(new ConfirmInventory(this, "§7Save?", (accept) -> {
                if (accept) {
                    if (this.command == null) {
                        CustomCommand command = new CustomCommand(this.name, this.output, this.description, this.permission, this.unused);
                        command.setConfigEntry();
                        command.save();
                        this.command = command;
                        this.guiName = "Edit '" + this.name + "':";
                    } else {
                        this.command.renameCommand(this.name);
                        this.command.editOutput(this.output);
                        this.command.editDescription(this.description);
                        this.command.editPermission(this.permission);
                        this.command.setUnused(this.unused);
                        this.command.save();
                    }

                    this.unsaved = false;
                }

                this.open();
            }));
        });

        addItem(3 * 9, ItemBuilder.material(this.command != null ? XMaterial.BUCKET : XMaterial.GRAY_STAINED_GLASS_PANE)
                .displayName((this.command != null) ? ChatColor.RED + "Delete" : "").build(), (event) -> {
            if (this.command != null) {
                this.openNext(new ConfirmInventory(this, "§7Delete?", (accept) -> {
                    if (accept) {
                        this.command.removeCommand();
                        this.back();
                        return;
                    }

                    this.open();
                }));
            }
        });
    }

    private boolean isReady() {
        return !this.name.isEmpty();
    }
}
