package de.dampfross.ui;

import de.dampfross.hex.map.HexMapController;
import de.dampfross.ui.entityproperties.EntityPropertiesPanel;

import javax.swing.*;
import java.awt.*;

public class EditorContentPanel extends JPanel {
    public HexMapController hexMapController;

    public EditorContentPanel() {
        setLayout(new BorderLayout());

        this.hexMapController = new HexMapController();
        add(hexMapController, BorderLayout.CENTER);

        EntityPropertiesPanel entityPropertiesPanel = new EntityPropertiesPanel();
        add(entityPropertiesPanel, BorderLayout.LINE_END);

        hexMapController.addClickedEntityListener(entityPropertiesPanel);
    }
}
