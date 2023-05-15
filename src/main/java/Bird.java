import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird {
    private double y;
    private int framesSinceLastJump = 5;
    private Image deadSprite = ImageIO.read(new File("src/main/java/Dead Bird.png"));
    private Image sprite = ImageIO.read(new File("src/main/java/Bird.png"));
    private boolean alive;
    private double terminalVelocity = 30;
    private double velocity;
    private double acceleration = 0.75;
    private int doubleJumpBonus = 5;
    public Bird() throws IOException {
        this.sprite = sprite.getScaledInstance(64, 48, 0);
        this.deadSprite = deadSprite.getScaledInstance(64, 48, 0);
        this.alive = true;
        this.y = 400;
        this.velocity = 0;
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
        if (!Main.running && (alive || y==546)) {return;}
        framesSinceLastJump++;
        this.velocity += acceleration;
        if (velocity > terminalVelocity) {
            velocity = terminalVelocity;
        }
        // this sets the angle of the bird relative to the velocity
        y += velocity;
        if (y >= 546) {
            y = 546;
            this.die();
        }
        if (this.inPipe()) {this.die();}
        // moves the bird velocity distance.
        Main.d.screen.repaint();
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
        Main.running = false;
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
}
/*
Inputs for AI:
- y pos of bird
- bird velocity
- leftX of next 2 pipes
- topY of bottom next 2 pipes
- frames since last jump
 */