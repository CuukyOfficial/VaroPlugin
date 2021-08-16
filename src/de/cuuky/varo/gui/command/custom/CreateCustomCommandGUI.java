package de.cuuky.varo.gui.command.custom;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.inventory.ItemClick;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.custom.CustomCommand;
import de.cuuky.varo.gui.VaroConfirmInventory;
import de.cuuky.varo.gui.VaroInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Locale;
import java.util.function.Consumer;

public class CreateCustomCommandGUI extends VaroInventory {

    private interface StringChecker {

        String check(String result);

    }

    private String guiName = "Create Command";
    private boolean unsaved;

    private CustomCommand command;
    private String name = "", output = "", description = "", permission;
    private boolean unused = false;

    public CreateCustomCommandGUI(Player player) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

        this.permission = "null";
    }

    public CreateCustomCommandGUI(Player player, CustomCommand command) {
        super(Main.getCuukyFrameWork().getAdvancedInventoryManager(), player);

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

    private ItemClick getInput(String current, String inputName, Consumer<String> resultReceiver, StringChecker checker) {
        return (event) -> {
            Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(getPlayer(),
                    "§7Gebe den/die " + inputName + " ein:", new ChatHookHandler() {

                @Override
                public boolean onChat(AsyncPlayerChatEvent event) {
                    String result = checker.check(event.getMessage());
                    if (result == null) return false;

                    resultReceiver.accept(result);
                    getPlayer().sendMessage(Main.getPrefix() + ChatColor.GRAY + "Der/Die " + inputName + " wurde auf '" + Main.getColorCode() + result + "§7' gesetzt");
                    unsaved = !current.equals(result);
                    open();
                    return true;
                }
            }));
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
        addItem(9, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Edit Name").lore(new String[]{this.name})
                .itemstack(Materials.SIGN.parseItem()).build(),
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

        addItem(11, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Edit Output").lore(new String[]{this.output})
                .itemstack(Materials.SIGN.parseItem()).build(),
                this.getInput(this.output,"Command Output", (String x) -> this.output = x)
        );

        addItem(13, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Edit Description").lore(new String[]{this.description})
                .itemstack(Materials.SIGN.parseItem()).build(), this.getInput(this.description, "Command Description", (String x) -> this.description = x)
        );

        addItem(15, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Edit Permission").lore(new String[]{this.permission})
                .itemstack(Materials.SIGN.parseItem()).build(), this.getInput(this.permission, "Command Permission", (String x) -> this.permission = x)
        );

        addItem(17, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Set Unused").lore(new String[]{String.valueOf(this.unused)})
                .itemstack(Materials.SIGN.parseItem()).build(),
                this.getInput(String.valueOf(this.unused), "Unused State", (String x) -> this.unused = Boolean.parseBoolean(x), (result) -> {
                    result = result.toLowerCase(Locale.ROOT);
                    if (!result.equals("true") && !result.equals("false")) {
                        getPlayer().sendMessage(Main.getPrefix() + "Ungültige Eingabe! (Die Eingabe muss \"true\" oder \"false\" sein)" + " Bitte erneut eingeben!");
                        return null;
                    }
                    return result;
                })
        );

        addItem(9 * 4 - 1, new ItemBuilder().displayname(ChatColor.DARK_PURPLE + "Save")
                .itemstack(Materials.EMERALD.parseItem()).build(), (event) -> {

            if (!this.isReady()) {
                event.getWhoClicked().sendMessage(Main.getPrefix() + "Bitte ändere den Namen!");
                return;
            }

            this.openNext(new VaroConfirmInventory(this, "§7Save?", (accept) -> {
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
                    this.open();
                    return;
                }

                this.open();
            }));
        });

        addItem(3 * 9, new ItemBuilder().displayname((this.command != null) ? ChatColor.RED + "Delete" : "")
                .itemstack((this.command != null) ? Materials.BUCKET.parseItem() : Materials.GRAY_STAINED_GLASS_PANE.parseItem()).build(), (event) -> {
            if (this.command != null) {
                this.openNext(new VaroConfirmInventory(this, "§7Delete?", (accept) -> {
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
