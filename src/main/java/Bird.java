import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird {
    private double y;
    private int pipesPassed = 0;
    private int x;
    private int framesSinceLastJump = 5;
    private int framesSurvived = 0;
    private Image deadSprite = ImageIO.read(new File("src/main/java/Dead Bird.png"));
    private Image sprite = ImageIO.read(new File("src/main/java/Bird.png"));
    private boolean alive;
    private double terminalVelocity = 30;
    private double velocity;
    private double acceleration = 0.75;
    private int doubleJumpBonus = 5;
    private Brain brain;
    private boolean controlledByAI;
    public Bird() throws IOException {
        x = 76;
        controlledByAI = false;
        this.sprite = sprite.getScaledInstance(64, 48, 0);
        this.deadSprite = deadSprite.getScaledInstance(64, 48, 0);
        this.alive = true;
        this.y = 400;
        this.velocity = 0;
    }
    public void giveBrain() {
        this.brain = new Brain(this);
        controlledByAI = true;
    }
    public void giveBrain(Brain brain) {
        this.brain = brain;
        this.brain.setBody(this);
        controlledByAI = true;
    }
    public boolean inPipe() {
        for (int i = 0; i < 3; i++) {
            Pipe p =Main.pipes[i];
            //these if statements outline the image of the bird to get an accurate hit box
            if (y < p.getTopOfUpperPipe()+600 || y+4 > p.getTopOfLowerPipe()) {
                if ((76+24>p.getDisplayX() && 76+24< p.getDisplayX()+60) || (76+44>p.getDisplayX() && 76+44 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+4 < p.getTopOfUpperPipe()+600 || y+8 > p.getTopOfLowerPipe()) {
                if ((76+16>p.getDisplayX() && 76+16< p.getDisplayX()+60) || (76+48>p.getDisplayX() && 76+48 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+8 < p.getTopOfUpperPipe()+600 || y+12 > p.getTopOfLowerPipe()) {
                if ((76+12>p.getDisplayX() && 76+12< p.getDisplayX()+60) || (76+52>p.getDisplayX() && 76+52 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+12 < p.getTopOfUpperPipe()+600 || y+16 > p.getTopOfLowerPipe()) {
                if ((76+4>p.getDisplayX() && 76+4< p.getDisplayX()+60) || (76+56>p.getDisplayX() && 76+56 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+16 < p.getTopOfUpperPipe()+600 || y+20 > p.getTopOfLowerPipe()) {
                if ((76>p.getDisplayX() && 76< p.getDisplayX()+60) || (76+56>p.getDisplayX() && 76+56 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+20 < p.getTopOfUpperPipe()+600 || y+24 > p.getTopOfLowerPipe()) {
                if ((76>p.getDisplayX() && 76< p.getDisplayX()+60) || (76+56>p.getDisplayX() && 76+56 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+24 < p.getTopOfUpperPipe()+600 || y+28 > p.getTopOfLowerPipe()) {
                if ((76>p.getDisplayX() && 76< p.getDisplayX()+60) || (76+60>p.getDisplayX() && 76+60 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+28 < p.getTopOfUpperPipe()+600 || y+32 > p.getTopOfLowerPipe()) {
                if ((76+4>p.getDisplayX() && 76+4< p.getDisplayX()+60) || (76+64>p.getDisplayX() && 76+64 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+32 < p.getTopOfUpperPipe()+600 || y+36 > p.getTopOfLowerPipe()) {
                if ((76+8>p.getDisplayX() && 76+8< p.getDisplayX()+60) || (76+60>p.getDisplayX() && 76+60 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+36 < p.getTopOfUpperPipe()+600 || y+40 > p.getTopOfLowerPipe()) {
                if ((76+16>p.getDisplayX() && 76+16< p.getDisplayX()+60) || (76+60>p.getDisplayX() && 76+60 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+40 < p.getTopOfUpperPipe()+600 || y+44 > p.getTopOfLowerPipe()) {
                if ((76+20>p.getDisplayX() && 76+20< p.getDisplayX()+60) || (76+60>p.getDisplayX() && 76+60 < p.getDisplayX()+60)) {
                    return true;
                }
            }
            if (y+44 < p.getTopOfUpperPipe()+600 || y+48 > p.getTopOfLowerPipe()) {
                if ((76+28>p.getDisplayX() && 76+28< p.getDisplayX()+60) || (76+40>p.getDisplayX() && 76+40 < p.getDisplayX()+60)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void act() {
        if (!alive && controlledByAI && Main.running) {
            x-=3;
        }
        if (!Main.running && (alive || y==546)) {return;}
        framesSinceLastJump++;
        framesSurvived++;
        if (controlledByAI) {
            brain.update();
            if (brain.computeWillJump()) {
                this.jump();
            }
        }
        this.velocity += acceleration;
        if (velocity > terminalVelocity) {
            velocity = terminalVelocity;
        }
        if (controlledByAI && 546-y <= velocity) {
            this.jump();
        }
        // this sets the angle of the bird relative to the velocity
        y += velocity;
        if (y >= 546) {
            y = 546;
            this.die();
        }
        if (this.inPipe()) {this.die();}
        // moves the bird velocity distance.
    }
    public void jump() {
        if (!alive) {return;}
        if (framesSinceLastJump<=4) {
            velocity = -terminalVelocity/3-doubleJumpBonus;
        }
        else {velocity = -terminalVelocity/3;}
        Main.running = true;
        framesSinceLastJump = 0;
    }
    public int getY() {
        return (int)this.y;
    }
    public void die() {
        if (!alive) {return;}
        if (!controlledByAI) {Main.running = false;}
        if (controlledByAI) {
            Pipe p1 = Main.pipes[0];
            for (int i = 1; i < 3; i++) {
                if (Main.pipes[i].getDisplayX()<p1.getDisplayX() || p1.getDisplayX()<x) {
                    p1 = Main.pipes[i];
                }
            }
            double diffInY = Math.abs((int)(y-p1.getTopOfLowerPipe()));
            this.brain.setScore(200*pipesPassed + 2*framesSurvived, 20/diffInY);
        }
        this.alive = false;
        this.velocity = terminalVelocity;
    }
    public void setTerminalVelocity(int tv) {
        this.terminalVelocity = tv;
    }
    public void setAcceleration(double acc) {
        this.acceleration = acc;
    }
    public void setDoubleJumpBonus(int bonus) {
        this.doubleJumpBonus = bonus;
    }
    public Image getSprite() {
        if (!alive) {return this.deadSprite;}
        return this.sprite;
    }
    public boolean isAlive() {
        return alive;
    }
    public double getVelocity() {
        return this.velocity;
    }
    public int getFramesSinceJumped() {
        return this.framesSinceLastJump;
    }
    public int getX() {
        return x;
    }
    public Brain getBrain() {
        return this.brain;
    }
    public void addPipesPassed() {
        this.pipesPassed++;
    }
    public void setSprite(Image sprite) {
        this.sprite = sprite;
        this.deadSprite = sprite;
    }
}