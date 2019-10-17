package de.cuuky.varo.version.commandsender;

public class NewCommandSender {

	// private OfflinePlayer player;
	// private String[] args;
	// private MessageReceivedEvent event;
	//
	// public NewCommandSender(OfflinePlayer player, String[] args,
	// MessageReceivedEvent event) {
	// this.player = player;
	// this.args = args;
	// this.event = event;
	// }
	//
	// @Override
	// public void setOp(boolean arg0) {
	// if(player == null)
	// return;
	//
	// player.setOp(arg0);
	// }
	//
	// @Override
	// public boolean isOp() {
	// return false;
	// }
	//
	// @Override
	// public void removeAttachment(PermissionAttachment arg0) {
	//
	// }
	//
	// @Override
	// public void recalculatePermissions() {
	//
	// }
	//
	// @Override
	// public boolean isPermissionSet(Permission arg0) {
	// return false;
	// }
	//
	// @Override
	// public boolean isPermissionSet(String arg0) {
	// return false;
	// }
	//
	// @Override
	// public boolean hasPermission(Permission arg0) {
	// return player != null ? player.isOp() : false;
	// }
	//
	// @Override
	// public boolean hasPermission(String arg0) {
	// return player != null ? player.isOp() : false;
	// }
	//
	// @Override
	// public Set<PermissionAttachmentInfo> getEffectivePermissions() {
	// return null;
	// }
	//
	// @Override
	// public PermissionAttachment addAttachment(Plugin arg0, String arg1,
	// boolean arg2, int arg3) {
	// return null;
	// }
	//
	// @Override
	// public PermissionAttachment addAttachment(Plugin arg0, String arg1,
	// boolean arg2) {
	// return null;
	// }
	//
	// @Override
	// public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
	// return null;
	// }
	//
	// @Override
	// public PermissionAttachment addAttachment(Plugin arg0) {
	// return null;
	// }
	//
	// @Override
	// public void sendMessage(String[] arg0) {
	// event.getTextChannel().sendMessage(Utils.replaceAllColors(Utils.getArgsToString(arg0,
	// " "))).queue();
	// return;
	// }
	//
	// String out = "";
	// int sched = -1;;
	//
	// @Override
	// public void sendMessage(String arg0) {
	// if(sched != -1) {
	// out = out + "\n" + arg0;
	// return;
	// } else {
	// out = arg0;
	// sched = Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
	// new Runnable() {
	//
	// @Override
	// public void run() {
	// Main.getDiscordBot().sendMessage(Utils.replaceAllColors(out), "Command '"
	// + args[0] + "' returned:", Color.RED, event.getTextChannel());
	// Bukkit.getScheduler().cancelTask(sched);
	// sched = -1;
	// return;
	// }
	// }, 5);
	// return;
	// }
	// }
	//
	// @Override
	// public Server getServer() {
	// return null;
	// }
	//
	// @Override
	// public String getName() {
	// return player != null ? player.getName() : event.getAuthor().getName();
	// }
	//
	// @Override
	// public Object spigot() {
	// return null;
	// }
}
