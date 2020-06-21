package de.dampfross.ui.menubar.menu;

import de.dampfross.hex.map.HexMapController;

import javax.swing.*;

public class FileMenu extends JMenu {
    HexMapController hexMapController;

    public FileMenu(String name, HexMapController hexMapController) {
        super(name);

        this.hexMapController = hexMapController;

        FileEmptyOpener fileEmptyOpener = new FileEmptyOpener(hexMapController);
        FileImport fileImport = new FileImport(hexMapController);
        FileExport fileExport = new FileExport(hexMapController);

        JMenu m11 = new JMenu("Neu");
        JMenuItem m12 = new JMenuItem("Importieren");
        m12.addActionListener(fileImport);
        JMenuItem m13 = new JMenuItem("Exportieren");
        m13.addActionListener(fileExport);

        JMenuItem m111 = new JMenuItem("Leer", UIManager.getIcon("FileView.fileIcon"));
        m111.addActionListener(fileEmptyOpener);
        JMenuItem m112 = new JMenuItem("Aus Vorlage");
        m11.add(m111);
        m11.add(m112);

        add(m11);
        addSeparator();
        add(m12);
        add(m13);
    }
}
