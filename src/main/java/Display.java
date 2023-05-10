import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Display implements ActionListener {
    Screen screen;
    JFrame frame;
    JPanel panel;
    JButton playAgain;
    JButton settings;
    JLabel highScore;
    public Display() {
        frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);

        playAgain = new JButton("Play Again");
        playAgain.addActionListener(this);
        panel.add(playAgain);

        settings = new JButton("Settings");
        settings.addActionListener(this);
        panel.add(settings);

        highScore = new JLabel("High Score: " + Main.highScore);
        panel.add(highScore);

        screen = new Screen();
        frame.add(screen, BoxLayout.Y_AXIS);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Play Again")) {
            Main.b.die();
            Main.reset();
        }
        if (e.getActionCommand().equals("Settings")){
            System.out.println("Settings");
        }
    }

    class Screen extends JPanel implements MouseListener, MouseMotionListener {


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
