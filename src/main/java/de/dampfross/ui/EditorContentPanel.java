package de.dampfross.ui;

import de.dampfross.map.HexMapController;
import de.dampfross.ui.EntityProperties.EntityPropertiesPanel;

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
