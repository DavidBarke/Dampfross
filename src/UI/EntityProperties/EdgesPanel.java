package UI.EntityProperties;

import HexGrid.HexDirection;
import HexGrid.HexEdge;
import HexGrid.HexEntity;
import HexGrid.HexGridClickedEntityListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

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
