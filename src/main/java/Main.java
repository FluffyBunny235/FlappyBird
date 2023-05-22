import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class Main {
    public static double acceleration = 0.75;
    public static int tv = 30;
    public static int bonus = 5;
    public static int highScore = 0;
    public static ArrayList<Bird> b = new ArrayList<>(1);
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
        b.add(new Bird());
        d = new Display();
        running = false;
        java.util.Timer t = new java.util.Timer();
        TimerTask t2 = new TimerTask() {
            @Override
            public void run() {
                ArrayList<Bird> copy = (ArrayList<Bird>) b.clone();
                int amountAlive= 0;
                for (Bird bird : copy) {
                    bird.act();
                    if (bird.isAlive()) {amountAlive++;}
                }
                for (int i =0; i < 3; i++) {
                    try {
                        pipes[i].act();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print("\b\b" + amountAlive);
                d.screen.repaint();
            }
        };
        long delay = 5;
        t.scheduleAtFixedRate(t2, delay, 30);
    }
    public static void reset() throws IOException {
        ArrayList<Bird> birds = new ArrayList<>(1);
        birds.add(new Bird());
        birds.get(0).setDoubleJumpBonus(bonus);
        birds.get(0).setAcceleration(acceleration);
        birds.get(0).setTerminalVelocity(tv);
        b = birds;
        for (int i =0; i < 3; i++) {
            pipes[i] = new Pipe(432+216*i);
        }
        running = false;
        currentScore = 0;
        d.screen.repaint();
    }
    public static void resetAllButBirds() throws IOException {
        for (int i =0; i < 3; i++) {
            pipes[i] = new Pipe(432+216*i);
        }
        running = false;
        currentScore = 0;
        d.screen.repaint();
    }
    public static Brain findBestBrain() {
        Brain best = b.get(0).getBrain();
        double score = Integer.MIN_VALUE;
        for (Bird bird : b) {
            if (bird.getBrain().getScore() > score) {
                score = bird.getBrain().getScore();
                best = bird.getBrain();
            }
        }
        return best;
    }
    public static void nextGeneration(Brain brain) throws IOException {
        b.clear();
        Bird bi = new Bird();
        bi.giveBrain(brain);
        b.add(bi);
        for (int i = 1; i < 25; i++) {
            Bird bird = new Bird();
            bird.giveBrain(brain.getMutation());
            b.add(bird);
        }
        for (int i = 25; i < 30; i++) {
            Bird bird = new Bird();
            bird.giveBrain();
            b.add(bird);
        }
    }
}