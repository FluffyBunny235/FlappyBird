import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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
        frame.setResizable(false);
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
            screen.requestFocus();
        }
        if (e.getActionCommand().equals("Settings")){
            Main.s.frame.setVisible(true);
            this.frame.setVisible(false);
            settings.setSelected(false);
            screen.requestFocus();
        }
    }

    static class Screen extends JPanel implements MouseListener, KeyListener {
        private Image background;
        private Image floor;
        private final int speed;
        int floorOffset;
        public Screen() throws IOException {
            background = ImageIO.read(new File("src/main/java/Background.png"));
            int WIDTH = 432;
            int HEIGHT = 768;
            background = background.getScaledInstance(WIDTH +2, HEIGHT +4, 0);
            floor = ImageIO.read(new File("src/main/java/Flappy Bird Floor.png"));
            floor = floor.getScaledInstance(floor.getWidth(this)*5, floor.getHeight(this)*5, 0);
            floorOffset = 0;
            speed = 3;
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
            //draw background
            g.drawImage(background,-2,-4,this);
            //draw pipes
            for (int i = 0; i < 3; i++) {
                Pipe p = Main.pipes[i];
                g.drawImage(p.getSprite(), p.getDisplayX(), p.getTopOfLowerPipe(), this);
                g.drawImage(p.getTopSprite(), p.getDisplayX(), p.getTopOfUpperPipe(), this);
            }
            //draw bird
            g.drawImage(Main.b.getSprite(),76, Main.b.getY(), this);
            //draw floor
            g.drawImage(floor, -floorOffset, 768-174, this);
            if (Main.running){floorOffset += speed;}
            if (93-floorOffset < 0) {
                floorOffset = 27;
                //this resets the floor so to give it the infinite feel without choppiness
            }
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
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) {
                Main.b.jump();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //empty
        }
    }
}
