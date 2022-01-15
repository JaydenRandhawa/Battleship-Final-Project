import java.io.IOException;

public class Battleship {
    public static void main(String[] args) throws IOException, InterruptedException {
        Game.start();
    };

    public static void restart() throws IOException, InterruptedException{
        Game.reset();
        Game.start();
    }
    
}
