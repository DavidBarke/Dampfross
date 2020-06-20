package de.dampfross.ui.EntityProperties;

import de.dampfross.hexgrid.HexEntity;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class EdgesPanel extends JPanel {
    HexEntity entity;

    public EdgesPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Kanten");
        setBorder(titledBorder);
    }

    public void setActiveEntity(HexEntity entity) {
        this.entity = entity;
    }
}
