import java.util.ArrayList;
import java.util.List;

public class Mediator {

    private List<Player> players;
    private Game game;

    public Mediator() {
        players = new ArrayList<>();
    }

    public void setPlayer(Player player) {
        players.add(player);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
