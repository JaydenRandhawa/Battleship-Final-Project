import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Debug {
    public static void printAllShips(int[][] carrier, int[][] battleship, int[][] submarine, int[][] destroyer){

        for(int i=0;i<2;i++){
            for(int ii=0;ii<5;ii++){
                System.out.print(carrier[i][ii] + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<2;i++){
            for(int ii=0;ii<4;ii++){
                System.out.print(battleship[i][ii] + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<2;i++){
            for(int ii=0;ii<3;ii++){
                System.out.print(submarine[i][ii] + ", ");
            }
            System.out.println();
        }

        for(int i=0;i<2;i++){
            for(int ii=0;ii<2;ii++){
                System.out.print(destroyer[i][ii] + ", ");
            }
            System.out.println();
        }
    }

    public static void showEnemyShips(JButton[][] cpuButtons, String[][] enemyCarrier, String[][] enemyBattleship, String[][] enemySubmarine, String[][] enemyDestroyer, ImageIcon ship){
        for(int i=0;i<5;i++){
            cpuButtons[Integer.parseInt(enemyCarrier[0][i])][Integer.parseInt(enemyCarrier[1][i])].setIcon(ship);
        }

        for(int i=0;i<4;i++){
            cpuButtons[Integer.parseInt(enemyBattleship[0][i])][Integer.parseInt(enemyBattleship[1][i])].setIcon(ship);
        }

        for(int i=0;i<3;i++){
            cpuButtons[Integer.parseInt(enemySubmarine[0][i])][Integer.parseInt(enemySubmarine[1][i])].setIcon(ship);
        }

        for(int i=0;i<2;i++){
            cpuButtons[Integer.parseInt(enemyDestroyer[0][i])][Integer.parseInt(enemyDestroyer[1][i])].setIcon(ship);
        }
    }
}