package UI.ContextMenu;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class OnClickFrameOpener implements ActionListener {
    JFrame frame;

    public OnClickFrameOpener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openFrame();
    }

    private void openFrame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
