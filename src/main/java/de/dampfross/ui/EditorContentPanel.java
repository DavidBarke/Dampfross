package de.dampfross.ui;

import de.dampfross.hexgrid.HexGrid;
import de.dampfross.ui.EntityProperties.EntityPropertiesPanel;

import javax.swing.*;
import java.awt.*;

public class EditorContentPanel extends JPanel {
    public HexGrid hexGrid;

    public EditorContentPanel() {
        setLayout(new BorderLayout());

        this.hexGrid = new HexGrid();
        add(hexGrid, BorderLayout.CENTER);

        EntityPropertiesPanel entityPropertiesPanel = new EntityPropertiesPanel();
        add(entityPropertiesPanel, BorderLayout.LINE_END);

        hexGrid.addClickedEntityListener(entityPropertiesPanel);
    }
}
