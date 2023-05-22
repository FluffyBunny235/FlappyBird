import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Pipe {
    private boolean countedScore;
    private int leftX;
    private int rightX;
    private int bottomEdgeY;
    private int topEdgeY;
    private final Image pipe;
    private final Image flippedPipe;
    //Gap between pipes should be 156
    //3 pipes are active at a time, when one exits the screen, it resets randomly.
    public Pipe(int leftX) throws IOException {
        this.countedScore = false;
        this.flippedPipe = ImageIO.read(new File("src/main/java/Flipped Pipe.png"));
        this.pipe = ImageIO.read(new File("src/main/java/Pipe.png"));
        this.leftX = leftX;
        this.rightX = leftX+60;
        this.bottomEdgeY = 558-(int)(Math.random()*300);
        this.topEdgeY = bottomEdgeY-150;
    }
    public void checkScore() {
        if (countedScore) {return;}
        if (this.getDisplayX() < 138) {
            ArrayList<Bird> copy = (ArrayList<Bird>) Main.b.clone();
            for (Bird bird : copy) {
                if (bird.isAlive()) {
                    bird.addPipesPassed();
                }
            }
            Main.currentScore++;
            countedScore = true;
            if (Main.currentScore > Main.highScore) {
                Main.highScore = Main.currentScore;
                Main.d.highScore.setText("Session High: " + Main.highScore);
            }
        }
    }
    public void act() throws IOException {
        if (!Main.running) {
            return;
        }
        leftX-=3;
        rightX-=3;
        if (rightX == 0) {
            int maxX = 0;
            for (int i = 0; i < 3; i++) {
                if (Main.pipes[i].getDisplayX() > maxX) {
                    maxX = Main.pipes[i].getDisplayX();
                }
            }
            leftX = maxX+216;
            rightX = leftX+60;
            this.bottomEdgeY = 558-(int)(Math.random()*400);
            this.topEdgeY = bottomEdgeY-150;
            countedScore = false;
        }
        boolean oneIsAlive = false;
        for (Bird bir : Main.b) {
            if (bir.isAlive()) {
                oneIsAlive = true;
            }
        }
        if (oneIsAlive) {
            checkScore();
        }
        else {
            if (Main.b.size() > 1){
                Main.nextGeneration(Main.findBestBrain());
                Main.resetAllButBirds();
                Main.running = true;
            }
            else {
                Main.running = false;
            }
        }
    }
    public int getDisplayX() {
        return this.leftX;
    }
    public int getTopOfLowerPipe() {
        return this.bottomEdgeY;
    }
    public int getTopOfUpperPipe() {
        return this.topEdgeY-600;
    }
    public Image getSprite(){
        return this.pipe;
    }
    public Image getTopSprite() {
        return this.flippedPipe;
    }
}
