
package chess.gui;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Table extends JPanel {
    
    public Table() {
        JFrame frame = new JFrame();
        frame.setTitle("Chess");
        
        //TODO: relative path
        ImageIcon icon = new ImageIcon("C:\\Users\\petrn\\OneDrive - České vysoké učení technické v Praze\\Dokumenty\\NetBeansProjects\\Chess\\src\\Data\\icon.png");
        frame.setIconImage(icon.getImage());
        
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public void paint(Graphics graphics) {
        graphics.fillRect(0, 0, 100, 100);
    }
    
}
