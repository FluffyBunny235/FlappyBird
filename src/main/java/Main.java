import java.io.IOException;
import java.util.TimerTask;

public class Main {
    public static int highScore = 0;
    public static Bird b;
    public static Display d;
    public static volatile boolean running;
    public static void main(String[] args) throws IOException {
        b = new Bird();
        b.getSprite();
        d = new Display();
        running = false;
        java.util.Timer t = new java.util.Timer();
        TimerTask t2 = new TimerTask() {
            @Override
            public void run() {
                b.act();
            }
        };
        long delay = 5;
        t.scheduleAtFixedRate(t2, delay, 30);
    }
    public static void reset() throws IOException {
        b = new Bird();
        running = false;
        d.screen.repaint();
    }
}