package de.dampfross.ui.ContextMenu;

import javax.swing.*;

public class HexMapContextMenu extends JPopupMenu {
    public HexMapContextMenu() {
        JMenuItem entityPropertiesItem = new EntityPropertiesItem("Feldeigenschaften");
        add(entityPropertiesItem);
    }
}
