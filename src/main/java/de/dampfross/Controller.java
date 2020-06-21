package de.dampfross;

import de.dampfross.ui.EditorContentPanel;
import de.dampfross.ui.menubar.EditorMenuBar;
import de.dampfross.ui.toolbar.EditToolBar;

import javax.swing.*;
import java.awt.*;

public class Controller {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initGui();
            }
        });
    }

    private static void initGui() {
        // Frame
        JFrame f = new JFrame();
        f.setTitle("Dampfross");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setMinimumSize(new Dimension(400, 300));
        // setUndecorated(true);

        // Content
        EditorContentPanel content = new EditorContentPanel();
        f.add(content);

        // MenuBar
        JMenuBar mb = new EditorMenuBar(content.hexMapController);
        f.getContentPane().add(mb, BorderLayout.PAGE_START);

        // ToolBar
        JToolBar toolBar = new EditToolBar("Typ", content.hexMapController);
        content.add(toolBar, BorderLayout.PAGE_START);

        f.setVisible(true);
    }
}
