package de.dampfross.ui.contextmenu;

import javax.swing.*;

public class HexMapContextMenu extends JPopupMenu {
    public HexMapContextMenu() {
        JMenuItem entityPropertiesItem = new EntityPropertiesItem("Feldeigenschaften");
        add(entityPropertiesItem);
    }
}
