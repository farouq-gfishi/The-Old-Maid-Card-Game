import java.util.Scanner;

public class GameRunner {

    public static void main(String[]args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter The Number Of Players: ");
        int numberOfPlayers = in.nextInt();
        Game game = new Game(numberOfPlayers);
        game.startGame();
    }

}
