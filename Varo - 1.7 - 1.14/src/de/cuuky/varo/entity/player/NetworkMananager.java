package de.cuuky.varo.entity.player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class NetworkMananager {

	// CHAT TITLE
	private static Class<?> chatMessageTypeClass;
	private static Class<?> enumTitleClass;

	private static Object genericSpeedType;
	private static Class<?> ioBase;

	// IOBASE
	private static Class<?> ioBaseChat;
	private static ArrayList<NetworkMananager> pps;

	private static Class<?> titleClass;
	private static Constructor<?> titleConstructor;

	static {
		pps = new ArrayList<NetworkMananager>();

		try {
			if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
				ioBaseChat = VersionUtils.getChatSerializer();
				ioBase = Class.forName(VersionUtils.getNmsClass() + ".IChatBaseComponent");
				titleClass = Class.forName(VersionUtils.getNmsClass() + ".PacketPlayOutTitle");
				try {
					enumTitleClass = titleClass.getDeclaredClasses()[0];
				} catch(Exception e) {
					enumTitleClass = Class.forName(VersionUtils.getNmsClass() + ".EnumTitleAction");
				}

				try {
					chatMessageTypeClass = Class.forName(VersionUtils.getNmsClass() + ".ChatMessageType");
				} catch(Exception e) {}

				titleConstructor = titleClass.getConstructor(enumTitleClass, ioBase, int.class, int.class, int.class);
			}
		} catch(ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private Object connection;
	private Field footerField;

	private Field headerField;
	private Player player;

	private Object playerHandle;
	private Method sendPacketMethod;
	private Object tablist;

	public NetworkMananager(Player player) {
		this.player = player;

		pps.add(this);
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		try {
			playerHandle = player.getClass().getMethod("getHandle").invoke(player);
			connection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
			sendPacketMethod = connection.getClass().getDeclaredMethod("sendPacket", Class.forName(VersionUtils.getNmsClass() + ".Packet"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Object getConnection() {
		return connection;
	}

	public int getPing() {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return -1;

		try {
			return playerHandle.getClass().getField("ping").getInt(playerHandle);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public Player getPlayer() {
		return player;
	}

	public void respawnPlayer() {
		try {
			Object respawnEnum = Class.forName(VersionUtils.getNmsClass() + ".EnumClientCommand").getEnumConstants()[0];
			Constructor<?>[] constructors = Class.forName(VersionUtils.getNmsClass() + ".PacketPlayInClientCommand").getConstructors();
			for(Constructor<?> constructor : constructors) {
				Class<?>[] args = constructor.getParameterTypes();
				if(args.length == 1 && args[0] == respawnEnum.getClass()) {
					Object packet = Class.forName(VersionUtils.getNmsClass() + ".PacketPlayInClientCommand").getConstructor(args).newInstance(respawnEnum);
					sendPacket(packet);
					break;
				}
			}
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	public void sendActionbar(String message) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		try {
			Object barchat = ioBaseChat.getDeclaredMethod("a", String.class).invoke(ioBaseChat, "{\"text\": \"" + message + "\"}");

			Class<?> packetChatClass = Class.forName(VersionUtils.getNmsClass() + ".PacketPlayOutChat");

			Object packet = null;
			if(chatMessageTypeClass == null)
				packet = packetChatClass.getConstructor(ioBase, byte.class).newInstance(barchat, (byte) 2);
			else
				packet = packetChatClass.getConstructor(ioBase, chatMessageTypeClass).newInstance(barchat, chatMessageTypeClass.getDeclaredField("GAME_INFO").get(null));

			sendPacket(packet);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendLinkedMessage(String message, String link) {
		try {
			Class<?> packet = Class.forName(VersionUtils.getNmsClass() + ".PacketPlayOutChat");
			Constructor<?> constructor = packet.getConstructor(ioBase);
			Object text = ioBaseChat.getMethod("a", String.class).invoke(ioBaseChat, "{text: '" + message + "', color: 'white', clickEvent: {\"action\": \"open_url\" , value: \"" + link + "\"}}");
			Object packetFinal = constructor.newInstance(text);
			Field field = packetFinal.getClass().getDeclaredField("a");
			field.setAccessible(true);
			field.set(packetFinal, text);

			sendPacket(packetFinal);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	public void sendPacket(Object packet) {
		try {
			sendPacketMethod.invoke(connection, packet);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendTablist() {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		try {
			Object tabheader = ioBaseChat.getDeclaredMethod("a", String.class).invoke(ioBaseChat, "{\"text\": \"" + ConfigMessages.TABLIST_HEADER.getValue() + "\"}");
			Object tabfooter = ioBaseChat.getDeclaredMethod("a", String.class).invoke(ioBaseChat, "{\"text\": \"" + ConfigMessages.TABLIST_FOOTER.getValue() + "\"}");

			if(tablist == null) {
				tablist = Class.forName(VersionUtils.getNmsClass() + ".PacketPlayOutPlayerListHeaderFooter").newInstance();

				headerField = getField(tablist.getClass(), "a", "header");
				headerField.setAccessible(true);

				footerField = getField(tablist.getClass(), "b", "footer");
				footerField.setAccessible(true);
			}

			headerField.set(tablist, tabheader);
			footerField.set(tablist, tabfooter);

			sendPacket(tablist);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendTitle(String header, String footer) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		try {
			Object titleHeader = ioBaseChat.getDeclaredMethod("a", String.class).invoke(ioBaseChat, "{\"text\": \"" + header + "\"}");
			Object titleFooter = ioBaseChat.getDeclaredMethod("a", String.class).invoke(ioBaseChat, "{\"text\": \"" + footer + "\"}");

			Object headerPacket = titleConstructor.newInstance(enumTitleClass.getDeclaredField("TITLE").get(null), titleHeader, 0, 2, 0);
			Object footerPacket = titleConstructor.newInstance(enumTitleClass.getDeclaredField("SUBTITLE").get(null), titleFooter, 0, 2, 0);

			sendPacket(headerPacket);
			sendPacket(footerPacket);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setAttributeSpeed(double value) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_8))
			return;

		try {
			if(genericSpeedType == null) {
				Class<?> attribute = Class.forName("org.bukkit.attribute.Attribute");
				genericSpeedType = attribute.getField("GENERIC_ATTACK_SPEED").get(attribute);
			}

			Object attributeInstance = player.getClass().getMethod("getAttribute", genericSpeedType.getClass()).invoke(player, genericSpeedType);

			attributeInstance.getClass().getMethod("setBaseValue", double.class).invoke(attributeInstance, value);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static Field getField(Class<?> clazz, String... strings) {
		for(String s : strings) {
			try {
				return clazz.getDeclaredField(s);
			} catch(NoSuchFieldException e) {
				continue;
			}
		}

		return null;
	}
}
