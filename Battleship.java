import Dependancies.Rules;

import java.io.IOException;

public class Battleship {
    // ANCHOR start the game by running the start method of the game class
    public static void main(String[] args) throws IOException, InterruptedException {
        Rules.displayRules();
        Game.start();
    };

    // ANCHOR restart the game by calling the restart and start methods of the game class
    public static void restart() throws IOException, InterruptedException{
        Game.reset();
        Game.start();
    }
    
}