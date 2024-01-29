import javax.swing.*;
import java.awt.*;

public class TestPane extends JPanel {

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(50, 50, 20, 20);
        g.setColor(Color.BLACK);
        g.fillOval(100, 100, 30, 30);
        g.setColor(Color.RED);
        g.fill3DRect(40,40,20,20, true);
    }
}
