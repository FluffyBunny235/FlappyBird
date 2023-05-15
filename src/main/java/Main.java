import java.io.IOException;
import java.util.TimerTask;

public class Main {
    public static double acceleration = 0.75;
    public static int tv = 30;
    public static int bonus = 5;
    public static int highScore = 0;
    public static Bird b;
    public static int currentScore = 0;
    public static Display d;
    public static Settings s;
    public static volatile boolean running;
    public static Pipe[] pipes;
    public static void main(String[] args) throws IOException {
        s = new Settings();
        pipes = new Pipe[3];
        for (int i =0; i < 3; i++) {
            pipes[i] = new Pipe(432+216*i);
        }
        b = new Bird();
        b.getSprite();
        d = new Display();
        running = false;
        java.util.Timer t = new java.util.Timer();
        TimerTask t2 = new TimerTask() {
            @Override
            public void run() {
                b.act();
                for (int i =0; i < 3; i++) {
                    pipes[i].act();
                }
            }
        };
        long delay = 5;
        t.scheduleAtFixedRate(t2, delay, 30);
    }
    public static void reset() throws IOException {
        b = new Bird();
        b.setDoubleJumpBonus(bonus);
        b.setAcceleration(acceleration);
        b.setTerminalVelocity(tv);
        for (int i =0; i < 3; i++) {
            pipes[i] = new Pipe(432+216*i);
        }
        running = false;
        currentScore = 0;
        d.screen.repaint();
    }
}