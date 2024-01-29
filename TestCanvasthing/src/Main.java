import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {
    static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame("Cyber");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new TestPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Component mouseClick = new MyComponent();
        frame.addMouseListener((MouseListener) mouseClick);
    }
}

