import java.io.IOException;

public class Battleship {
    public static void main(String[] args) throws IOException {
        Game.start();
    };

    public static void restart() throws IOException{
        Game.reset();
        Game.start();
    }
    
}
