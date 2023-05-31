import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class Main {
    public static double acceleration = 0.75;
    public static ArrayList<Double> probabilityOfPassing = new ArrayList<>(1);
    public static int tv = 30;
    public static int bonus = 5;
    public static int highScore = 0;
    public static Brain currentBest;
    public static ArrayList<Bird> b = new ArrayList<>(1);
    public static int currentScore = 0;
    public static Display d;
    public static Settings s;
    public static ArrayList<Brain> winners = new ArrayList<>(50);
    public static volatile boolean running;
    public static Pipe[] pipes;
    public static void main(String[] args) throws IOException {
        currentBest = new Brain(null);
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
                //int amountAlive= 0;
                for (Bird bird : copy) {
                    bird.act();
                    //if (bird.isAlive()) {amountAlive++;}
                }
                for (int i =0; i < 3; i++) {
                    try {
                        pipes[i].act();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                //System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b" + "Birds Alive: " + amountAlive);
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
        int birdsPassedPipe = 0;
        double score = Integer.MIN_VALUE;
        double tieBreaker = Integer.MIN_VALUE;
        for (Bird bird : b) {
            if (bird.getBrain().getScore() > 200) {
                birdsPassedPipe++;
            }
            if (bird.getBrain().getScore() > score) {
                score = bird.getBrain().getScore();
                best = bird.getBrain();
                tieBreaker = bird.getBrain().getTieBreaker();
            }
            else if (bird.getBrain().getScore() == score && bird.getBrain().getTieBreaker()>tieBreaker) {
                score = bird.getBrain().getScore();
                best = bird.getBrain();
                tieBreaker = bird.getBrain().getTieBreaker();
            }
        }
        probabilityOfPassing.add(((double)birdsPassedPipe)/50);
        double total = 0;
        for (double i : probabilityOfPassing) {
            total+=i;
        }
        System.out.println("Trials Run: " + probabilityOfPassing.size());
        System.out.println("Current AI Score: " + best.getScore());
        System.out.println("Current Probability: " + (2*birdsPassedPipe) + "%");
        System.out.println("Average Probability: " + (100*total/probabilityOfPassing.size()) + "%");
        System.out.println();
        winners.add(best);
        return best;
    }
    public static void nextGeneration(Brain brain) throws IOException {
        b.clear();
        Image s = ImageIO.read(new File("src/main/java/Previous Best Bird.png"));
        s = s.getScaledInstance(64, 48, 0);
        if (winners.size() == 50) {
            for (int i = 0; i < 50; i++) {
                Bird bird = new Bird();
                bird.giveBrain(winners.get(i));
                bird.setSprite(s);
                b.add(bird);
            }
            winners.clear();
            return;
        }
        for (int i = 0; i < 43; i++) {
            Bird bird = new Bird();
            bird.giveBrain(brain.getMutation());
            b.add(bird);
        }
        for (int i = 43; i < 48; i++) {
            Bird bird = new Bird();
            bird.giveBrain();
            b.add(bird);
        }
        Bird bi = new Bird();
        bi.giveBrain(brain);
        Bird bi2 = new Bird();
        bi2.giveBrain(currentBest);
        bi.setSprite(s);
        b.add(bi2);
        b.add(bi);
        currentBest = brain;
    }
}