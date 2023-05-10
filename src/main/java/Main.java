public class Main {
    public static int highScore = 0;
    public static Bird b;
    public static void main(String[] args) {
        b = new Bird();
        new Display();
    }
    public static void reset(){
        b = new Bird();
    }
}