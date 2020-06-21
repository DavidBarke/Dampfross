package de.dampfross.ui.menubar.menu;

import javax.swing.*;

public class SettingsMenu extends JMenu {

    public SettingsMenu(String name) {
        super(name);

        JMenuItem m21 = new JMenuItem("Öffnen");
        add(m21);
    }
}
