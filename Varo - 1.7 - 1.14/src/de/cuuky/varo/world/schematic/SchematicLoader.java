package de.cuuky.varo.world.schematic;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

import org.bukkit.Location;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.EditSessionFactory;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.PasteBuilder;
import com.sk89q.worldedit.world.World;

public class SchematicLoader {

	private static Class<?> blockVectorClass;

	// NEW
	private static Class<?> clipboardFormatsClass;
	private static Class<?> cuboidClipClass;

	private static Class<?> localWorldClass;
	private static boolean old;
	// OLD
	private static Class<?> vectorClass;

	static {
		try {
			clipboardFormatsClass = Class.forName("com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats");
			blockVectorClass = Class.forName("com.sk89q.worldedit.math.BlockVector3");
			old = false;
		} catch(Exception | Error e) {}

		try {
			vectorClass = Class.forName("com.sk89q.worldedit.Vector");
			cuboidClipClass = Class.forName("com.sk89q.worldedit.CuboidClipboard");
			localWorldClass = Class.forName("com.sk89q.worldedit.LocalWorld");
			old = true;
		} catch(Exception | Error e) {}
	}

	private File file;

	public SchematicLoader(File file) {
		this.file = file;
	}

	public void paste(Location location) {
		if(!old) {
			try {
				ClipboardFormat format = (ClipboardFormat) clipboardFormatsClass.getDeclaredMethod("findByFile", File.class).invoke(null, file);
				Clipboard clipboard = null;
				ClipboardReader reader = format.getReader(new FileInputStream(file));
				clipboard = (Clipboard) reader.getClass().getDeclaredMethod("read").invoke(reader, null);

				EditSessionFactory factory = WorldEdit.getInstance().getEditSessionFactory();
				Method m = factory.getClass().getDeclaredMethod("getEditSession", World.class, int.class);
				m.setAccessible(true);
				EditSession editSession = (EditSession) m.invoke(factory, new BukkitWorld(location.getWorld()), -1);

				ClipboardHolder cholder = ClipboardHolder.class.getConstructor(Clipboard.class).newInstance(clipboard);

				Object vector3 = blockVectorClass.getDeclaredMethod("at", int.class, int.class, int.class).invoke(null, location.getBlockX(), location.getBlockY(), location.getBlockZ());

				Object paste = cholder.getClass().getDeclaredMethod("createPaste", Extent.class).invoke(cholder, editSession);
				Object to = paste.getClass().getDeclaredMethod("to", vector3.getClass()).invoke(paste, vector3);

				Operation operation = ((PasteBuilder) to).ignoreAirBlocks(false).build();

				Operations.complete(operation);
				editSession.getClass().getDeclaredMethod("flushSession").invoke(editSession, null);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Object origin = vectorClass.getDeclaredMethod("toBlockPoint", double.class, double.class, double.class).invoke(null, location.getX(), location.getY(), location.getZ());

				EditSession es = EditSession.class.getConstructor(localWorldClass, int.class).newInstance(new BukkitWorld(location.getWorld()), 999999999);

				Object clipboard = cuboidClipClass.getDeclaredMethod("loadSchematic", File.class).invoke(null, file);
				clipboard.getClass().getMethod("paste", es.getClass(), vectorClass, boolean.class).invoke(clipboard, es, origin, false);
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}