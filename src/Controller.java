import Editor.EditState;
import HexGrid.HexGrid;
import Icons.EditStateIcon;
import UI.EditorContentPanel;
import UI.ToolBar.EditToolBar;

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

        // MenuBar
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("Datei");
        JMenu m2 = new JMenu("Einstellungen");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Öffnen");
        JMenuItem m12 = new JMenuItem("Speichern");
        m1.add(m11);
        m1.add(m12);
        JMenuItem m21 = new JMenuItem("Öffnen");
        m2.add(m21);
        f.getContentPane().add(mb, BorderLayout.PAGE_START);

        // Content
        EditorContentPanel content = new EditorContentPanel();
        f.add(content);

        // ToolBar
        JToolBar toolBar = new EditToolBar("Typ", content.hexGrid);
        content.add(toolBar, BorderLayout.PAGE_START);

        f.setVisible(true);
    }
}
