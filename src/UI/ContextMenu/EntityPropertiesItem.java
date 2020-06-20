package UI.ContextMenu;

import javax.swing.*;

public class EntityPropertiesItem extends JMenuItem {
    public EntityPropertiesItem(String name) {
        super(name);
        addActionListener(new OnClickFrameOpener(new EntityPropertiesMenu()));
    }
}
