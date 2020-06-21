package de.dampfross.ui.toolbar;

import de.dampfross.editor.EditState;
import de.dampfross.icons.CursorIcon;
import de.dampfross.icons.EditStateIcon;
import de.dampfross.hex.map.HexMapController;

import javax.swing.*;
import java.util.HashMap;

public class EditToolBar extends JToolBar {
    private final HexMapController hexMapController;

    private final ButtonGroup buttonGroup = new ButtonGroup();

    private final HashMap<EditState, JToggleButton> editStateJToggleButtonMap = new HashMap<>();
    private final HashMap<ButtonModel, EditState> buttonModelEditStateMap = new HashMap<>();

    public EditToolBar(String name, HexMapController hexMapController) {
        super(name);

        this.hexMapController = hexMapController;

        for (EditState editState : EditState.values()) {
            JToggleButton button;
            if (editState == EditState.NONE) {
                CursorIcon icon = new CursorIcon();
                icon.setSize(22, 19);
                button = new JToggleButton(icon);
            } else {
                button = new JToggleButton(new EditStateIcon(editState));
            }

            editStateJToggleButtonMap.put(editState, button);
            buttonModelEditStateMap.put(button.getModel(), editState);

            buttonGroup.add(button);
            button.setFocusPainted(false);
            button.addActionListener(e -> hexMapController.setEditState(getEditState()));
        }

        // Order matters
        add(editStateJToggleButtonMap.get(EditState.NONE));
        add(editStateJToggleButtonMap.get(EditState.EMPTY));
        add(editStateJToggleButtonMap.get(EditState.CITY));
        add(editStateJToggleButtonMap.get(EditState.MOUNTAIN));
        add(editStateJToggleButtonMap.get(EditState.LAKE));
        add(editStateJToggleButtonMap.get(EditState.RIVER));
        add(editStateJToggleButtonMap.get(EditState.BORDER));

        editStateJToggleButtonMap.get(EditState.NONE).setSelected(true);

        hexMapController.setEditState(getEditState());
    }

    public EditState getEditState() {
        return buttonModelEditStateMap.get(buttonGroup.getSelection());
    }
}
