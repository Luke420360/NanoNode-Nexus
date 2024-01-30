import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Grid extends JFrame {

    private final int gridSize = 10;  // Anzahl der Zellen in einer Zeile/Spalte
    private final int cellSize = 50;  // Größe jeder Zelle in Pixel
    private final Color gridColor = Color.BLACK;
    private JButton fullscreenButton;
    private boolean isFullscreen = false;
    private boolean[][] grid;  // Matrix, um den Zustand jeder Zelle zu speichern

    public Grid() {
        setTitle("Grafisches Grid");
        setSize(gridSize * cellSize, gridSize * cellSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid = new boolean[gridSize][gridSize];  // Initialisiere die Zellenmatrix

        // Füge ein benutzerdefiniertes JPanel mit Mausereignissen hinzu
        add(new DrawingPanel());
        fullscreenButton = new JButton("Toggle Fullscreen");
        fullscreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleFullscreen();
            }
        });
        add(fullscreenButton, BorderLayout.NORTH);
        setVisible(true);
    }

    private class DrawingPanel extends JPanel {

        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = e.getY() / cellSize;
                    int col = e.getX() / cellSize;

                    // Klick auf die Zelle umschalten (ein/aus)
                    grid[row][col] = !grid[row][col];

                    // Aktualisiere das Panel, um die Änderungen zu reflektieren
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Zeichne das Grid
            g.setColor(gridColor);
            for (int i = 0; i <= gridSize; i++) {
                g.drawLine(i * cellSize, 0, i * cellSize, gridSize * cellSize);  // Vertikale Linien
                g.drawLine(0, i * cellSize, gridSize * cellSize, i * cellSize);  // Horizontale Linien
            }

            // Zeichne die markierten Zellen
            g.setColor(Color.BLUE);
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    if (grid[row][col]) {
                        g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Grid());
    }

    private void toggleFullscreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        if (isFullscreen) {
            dispose();  // Beende den Vollbildmodus
            setUndecorated(false);
            gd.setFullScreenWindow(null);
            setSize(gridSize * cellSize, gridSize * cellSize);
            setLocationRelativeTo(null);  // Zentriere das Fenster
            isFullscreen = false;
        } else {
            dispose();  // Beende den Fenstermodus
            setUndecorated(true);
            gd.setFullScreenWindow(this);
            isFullscreen = true;
        }
        setVisible(true);
    }
}
