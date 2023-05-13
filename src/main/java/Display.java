import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Display implements ActionListener {
    Screen screen;
    JFrame frame;
    JPanel panel;
    JButton playAgain;
    JButton settings;
    JLabel highScore;
    public Display() throws IOException {
        frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);

        playAgain = new JButton("Play Again");
        playAgain.addActionListener(this);
        playAgain.setSelected(false);
        panel.add(playAgain);

        settings = new JButton("Settings");
        settings.addActionListener(this);
        settings.setSelected(false);
        panel.add(settings);

        highScore = new JLabel("High Score: " + Main.highScore);
        panel.add(highScore);

        screen = new Screen();
        frame.add(screen, BoxLayout.Y_AXIS);

        frame.pack();
        frame.setVisible(true);
        screen.setFocusable(true);
        screen.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Play Again")) {
            Main.b.die();
            try {
                Main.reset();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            playAgain.setSelected(false);
        }
        if (e.getActionCommand().equals("Settings")){
            System.out.println("Settings");
            settings.setSelected(false);
        }
    }

    class Screen extends JPanel implements MouseListener, KeyListener {
        private final int WIDTH = 432;
        private final int HEIGHT = 768;
        private Image background;
        public Screen() throws IOException {
            background = ImageIO.read(new File("src/main/java/Background.png"));
            background = background.getScaledInstance(WIDTH+2, HEIGHT+4, 0);
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.addMouseListener(this);
            this.addKeyListener(this);
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.setVisible(true);
            repaint();
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background,-2,-4,this);
            //rotate image
            g.drawImage(Main.b.getSprite(),108-32, Main.b.getY(), this);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            Main.b.jump();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //empty
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //empty
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //empty
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //empty
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP ) {
                Main.b.jump();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            //empty
        }
    }
}
