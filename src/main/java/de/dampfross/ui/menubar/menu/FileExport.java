package de.dampfross.ui.menubar.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dampfross.hex.map.HexMapController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileExport implements ActionListener {
    private final HexMapController hexMapController;
    private final JFileChooser fileChooser = new JFileChooser();

    public FileExport(HexMapController hexMapController) {
        this.hexMapController = hexMapController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fileChooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                File file = new File(fileChooser.getSelectedFile() + ".json");
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, hexMapController.getHexMap());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
