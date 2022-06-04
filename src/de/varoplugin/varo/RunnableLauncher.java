package de.varoplugin.varo;

import javax.swing.*;

/**
 * For letting the user know if launched as
 * application that this is not an executable program.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class RunnableLauncher {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Das Plugin ist kein ausführbares Programm! Bitte ziehe es auf einem Server in den plugins Ordner!\nYou may not execute this plugin, please launch it on a Bukkit server!\n\nNeed help? https://discord.varoplugin.de/\n\nVaro Plugin by Cuuky");
    }
}