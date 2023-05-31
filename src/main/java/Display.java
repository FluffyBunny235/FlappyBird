import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Display implements ActionListener {
    Screen screen;
    JFrame frame;
    JPanel panel;
    JButton playAgain;
    JButton settings;
    JButton learn;
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

        learn = new JButton("AI");
        learn.addActionListener(this);
        learn.setSelected(false);
        panel.add(learn);

        highScore = new JLabel("Session High: " + Main.highScore);
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
            for (Bird bird : Main.b) {
                bird.die();
            }
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
        if (e.getActionCommand().equals("AI")) {
            ArrayList<Bird> birds = new ArrayList<>(50);
            for (int i = 0; i < 50; i++ ){
                Bird b;
                try {
                    b = new Bird();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                b.giveBrain();
                birds.add(b);
            }
            Main.b = birds;
            try {
                Main.resetAllButBirds();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Main.running = true;
            settings.setSelected(false);
            screen.requestFocus();
        }
    }

    static class Screen extends JPanel implements MouseListener, KeyListener {
        private Image background;
        private Image floor;
        private final int speed;
        private Image[] scores;
        int floorOffset;
        public Screen() throws IOException {
            scores = new Image[10];
            for (int i = 0; i < 10; i++) {
                Image a = ImageIO.read(new File("src/main/java/Score" + i + ".png"));
                a = a.getScaledInstance(64, 64, 0);
                scores[i] = a;
            }
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
            ArrayList<Bird> copy;
            copy = (ArrayList<Bird>) Main.b.clone();
            for (Bird bird : copy){g.drawImage(bird.getSprite(), bird.getX(), bird.getY(), this);}
            //draw floor
            g.drawImage(floor, -floorOffset, 768-174, this);
            if (Main.running){floorOffset += speed;}
            if (93-floorOffset < 0) {
                floorOffset = 27;
                //this resets the floor so to give it the infinite feel without choppiness
            }
            //draw score
            int sc = Main.currentScore;
            int x = 368;
            while (sc != -1) {
                g.drawImage(scores[sc%10], x, 4, this);
                sc = sc/10;
                x-=60;
                if (sc == 0) {
                    sc = -1;
                }
            }
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (Main.b.size() <= 2){Main.b.get(0).jump();}
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
            if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) && Main.b.size() <= 2) {
                Main.b.get(0).jump();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //empty
        }
    }
}
