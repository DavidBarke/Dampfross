package de.dampfross.ui.menubar;

import de.dampfross.hex.map.HexMapController;
import de.dampfross.ui.menubar.menu.FileMenu;
import de.dampfross.ui.menubar.menu.SettingsMenu;

import javax.swing.*;

public class EditorMenuBar extends JMenuBar {
    HexMapController hexMapController;

    public EditorMenuBar(HexMapController hexMapController) {
        this.hexMapController = hexMapController;

        JMenu m1 = new FileMenu("Datei", hexMapController);
        JMenu m2 = new SettingsMenu("Einstellungen");
        add(m1);
        add(m2);
    }
}
