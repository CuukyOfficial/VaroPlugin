package de.varoplugin.varo;

import javax.swing.*;

/**
 * For letting the user know if launched as
 * application that this is not an executable program.
 */
public class RunnableLauncher {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "You may not execute this plugin as runnable!\n\nNeed help? https://discord.varoplugin.de/\n\nVaro Plugin by Cuuky");
    }
}