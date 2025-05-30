import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player>players;
    private final List<Thread> threads;
    private final Deck deck;
    private final Mediator mediator;
    private Player currentPlayer;

    public Game(int numberOfPlayers) {
        deck = new Deck();
        threads = new ArrayList<>();
        mediator = new Mediator();
        mediator.setGame(this);
        players = createPlayers(numberOfPlayers);
        currentPlayer = players.get(0);
    }

    private List<Player> createPlayers(int size) {
        List<Player> players = new ArrayList<>();
        for(int i=1;i<=size;i++) {
            players.add(new Player("Player"+i, mediator));
        }
        return players;
    }

    public void startGame() {
        distributeCardsToPlayers();
        for (Player player:players)
            player.printCards();
        startThreads();
        waitThreadsToEnd();
        System.out.println(players.get(0).name() + " IS THE LOOOOOOOSER!!");
    }

    private void distributeCardsToPlayers() {
        int index = 0;
        for(Card card:deck.getCards()) {
            players.get(index).addCard(card);
            index++;
            if(index==players.size())
                index=0;
        }
    }

    private void startThreads() {
        for(Player player:players) {
            Thread thread = new Thread(player);
            threads.add(thread);
            thread.start();
        }
    }

    private void waitThreadsToEnd() {
        for (Thread thread:threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removePlayerFromGame(Player player) {
        players.remove(player);
        System.out.println(player.name() + " IS OUT!!!!!!!!!");
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    public boolean endGame() {
        return players.size()<=1;
    }

    public Player getNextPlayer() {
        int index = getIndexOfNextPlayer();
        return players.get(index);
    }

    public void nextPlayer() {
        int index = getIndexOfNextPlayer();
        currentPlayer = players.get(index);
        notifyAll();
    }

    private int getIndexOfNextPlayer() {
        int index = players.indexOf(currentPlayer) + 1;
        if(index==players.size()) index=0;
        return index;
    }

    public boolean isPlayerTurn(Player player) {
        return player==currentPlayer;
    }

}
