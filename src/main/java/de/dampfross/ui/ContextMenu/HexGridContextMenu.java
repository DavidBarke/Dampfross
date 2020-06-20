package de.dampfross.ui.ContextMenu;

import javax.swing.*;

public class HexGridContextMenu extends JPopupMenu {
    public HexGridContextMenu() {
        JMenuItem entityPropertiesItem = new EntityPropertiesItem("Feldeigenschaften");
        add(entityPropertiesItem);
    }
}
