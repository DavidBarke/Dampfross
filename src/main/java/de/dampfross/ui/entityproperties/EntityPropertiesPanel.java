package de.dampfross.ui.entityproperties;

import de.dampfross.hex.entity.HexEntity;
import de.dampfross.hex.map.HexMapClickedEntityListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EntityPropertiesPanel extends JPanel implements HexMapClickedEntityListener {
    HexEntity entity;
    LocationPanel locationPanel;
    EdgesPanel edgesPanel;

    public EntityPropertiesPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 0));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Feldeigenschaften");
        setBorder(titledBorder);

        locationPanel = new LocationPanel();
        edgesPanel = new EdgesPanel();
        add(locationPanel);
        add(edgesPanel);
    }

    public void setActiveEntity(HexEntity entity) {
        this.entity = entity;
        locationPanel.setActiveEntity(entity);
        edgesPanel.setActiveEntity(entity);
    }
}
