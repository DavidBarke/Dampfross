package de.dampfross.ui.entityproperties;

import de.dampfross.hex.entity.HexEntity;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LocationPanel extends JPanel {
    HexEntity entity;
    JLabel q = new JLabel();
    JLabel r = new JLabel();
    LocationEntityPanel locationEntityPanel = new LocationEntityPanel();

    public LocationPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Feld");
        setBorder(titledBorder);

        add(q);
        add(r);
        add(locationEntityPanel);
    }

    public void setActiveEntity(HexEntity entity) {
        this.entity = entity;
        locationEntityPanel.setActiveEntity(entity);
        q.setText("q: " + String.valueOf(entity.location.q));
        r.setText("r: " + String.valueOf(entity.location.r));
    }
}
