package de.dampfross.ui.menubar.menu;

import de.dampfross.hex.map.HexMapController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileEmptyOpener implements ActionListener {
    HexMapController hexMapController;

    public FileEmptyOpener(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("File empty");
        hexMapController.getHexMapLoader().loadEmpty();
    }
}
