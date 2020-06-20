package UI.EntityProperties;

import Editor.EditState;
import HexGrid.HexEntity;
import HexGrid.HexEntityType;
import Icons.EditStateIcon;

import javax.swing.*;
import java.util.HashMap;

public class LocationEntityPanel extends JPanel {
    private final ButtonGroup buttonGroup = new ButtonGroup();

    private final HashMap<HexEntityType, JToggleButton> hexEntityTypeJToggleButtonHashMap = new HashMap<>();
    private final HashMap<ButtonModel, HexEntityType> buttonModelHexEntityTypeHashMap = new HashMap<>();

    private HexEntity entity;

    public LocationEntityPanel() {
        for (HexEntityType entityType : HexEntityType.values()) {
            JToggleButton button = new JToggleButton(new EditStateIcon(entityType.getEditState()));

            hexEntityTypeJToggleButtonHashMap.put(entityType, button);
            buttonModelHexEntityTypeHashMap.put(button.getModel(), entityType);

            buttonGroup.add(button);
            button.setFocusPainted(false);
            button.addActionListener(e -> entity.setHexEntityType(entityType));
        }

        add(hexEntityTypeJToggleButtonHashMap.get(HexEntityType.EMPTY));
        add(hexEntityTypeJToggleButtonHashMap.get(HexEntityType.CITY));
        add(hexEntityTypeJToggleButtonHashMap.get(HexEntityType.MOUNTAIN));
        add(hexEntityTypeJToggleButtonHashMap.get(HexEntityType.LAKE));
    }

    public void setActiveEntity(HexEntity entity) {
        this.entity = entity;
        System.out.println(entity);
        buttonGroup.setSelected(hexEntityTypeJToggleButtonHashMap.get(entity.getHexEntityType()).getModel(), true);
    }
}
