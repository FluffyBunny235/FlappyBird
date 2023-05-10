public class Bird {
    private int y;
    private boolean alive;
    private double terminalVelocity = -5;
    private double velocity;
    private double acceleration = -0.327;
    private int direction;
    public Bird() {
        this.alive = true;
        this.y = 400;
        this.velocity = 0;
        this.direction = 0;
    }
    public void act() {
        this.velocity += acceleration;
        if (velocity < terminalVelocity) {
            velocity = terminalVelocity;
        }
        if (alive){direction = (int)(120*Math.atan(velocity)/3.14);}
        // this sets the angle of the bird relative to the velocity
        y += velocity;
        // moves the bird velocity distance.
    }
    public int getDirection() {
        return this.direction;
    }
    public void jump() {
        velocity = -terminalVelocity;
    }
    public int getY() {
        return this.y;
    }
    public void die() {
        this.alive = false;
        this.direction = -90;
        this.velocity = terminalVelocity;
    }
    public void setTerminalVelocity(int tv) {
        this.terminalVelocity = tv;
    }
    public void setAcceleration(int acc) {
        this.acceleration = acc;
    }
}
