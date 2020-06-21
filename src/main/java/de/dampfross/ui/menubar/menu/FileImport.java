package de.dampfross.ui.menubar.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dampfross.hex.map.HexMapController;
import de.dampfross.hex.map.HexMapLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileImport implements ActionListener {
    private final HexMapController hexMapController;
    private final JFileChooser fileChooser = new JFileChooser();

    public FileImport(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            HexMapLoader hexMapLoader = hexMapController.getHexMapLoader();
            hexMapLoader.loadFromJSON(file);
        }
    }
}
