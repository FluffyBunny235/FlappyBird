import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird {
    private double y;
    private Image deadSprite = ImageIO.read(new File("src/main/java/Dead Bird.png"));
    private Image sprite = ImageIO.read(new File("src/main/java/Bird.png"));
    private boolean alive;
    private double terminalVelocity = 20;
    private double velocity;
    private double acceleration = 0.75;
    public Bird() throws IOException {
        this.sprite = sprite.getScaledInstance(64, 48, 0);
        this.deadSprite = deadSprite.getScaledInstance(64, 48, 0);
        this.alive = true;
        this.y = 400;
        this.velocity = 0;
    }
    public void act() {
        if (!Main.running) {return;}
        this.velocity += acceleration;
        if (velocity > terminalVelocity) {
            velocity = terminalVelocity;
        }
        // this sets the angle of the bird relative to the velocity
        y += velocity;
        if (y >= 706-160) {
            y = 706-160;
            die();
        }
        // moves the bird velocity distance.
        Main.d.screen.repaint();
    }
    public void jump() {
        if (!alive) {return;}
        velocity = -terminalVelocity/2;
        Main.running = true;
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
    public void setAcceleration(int acc) {
        this.acceleration = acc;
    }
    public Image getSprite() {
        if (!alive) {return this.deadSprite;}
        return this.sprite;
    }
}
