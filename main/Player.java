import java.util.*;

public class Player extends Thread {

    private final String name;
    private final List<Card> handCards;
    private final Mediator mediator;
    private final Random random = new Random();

    public Player(String name, Mediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.setPlayer(this);
        handCards = new ArrayList<>();
    }

    @Override
    public void run() {
        dropMatchingPairs();
        waitPlayersToDropPairs();
        while(hasCards()) {
            synchronized (mediator.getGame()) {
                while(!mediator.getGame().isPlayerTurn(this)) {
                    try {
                        mediator.getGame().wait();
                        if(!mediator.getGame().hasPlayer(this)) {
                            return;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(mediator.getGame().endGame()) {
                    break;
                }
                play();
                if(!hasCards()) {
                    mediator.getGame().removePlayerFromGame(this);
                    return;
                }
            }
        }
    }

    private void dropMatchingPairs() {
        Set<Card> pairsToRemove = new HashSet<>();
        for (Card card1 : handCards) {
            for (Card card2 : handCards) {
                if (card1 != card2 && card1.getValue().equals(card2.getValue()) && sameColor(card1, card2)) {
                    pairsToRemove.add(card1);
                    pairsToRemove.add(card2);
                }
            }
        }
        handCards.removeAll(pairsToRemove);
        synchronized (mediator.getGame()) {
            System.out.println("------------------------------------------");
            System.out.println(name + " has " + handCards.size() + " cards.");
            System.out.println(name + " drop these cards: ");
            for(Card card:pairsToRemove) {
                System.out.println(card);
            }
            System.out.println("------------------------------------------");
        }
    }

    private boolean sameColor(Card card1, Card card2) {
        return(
                (card1.getType().equals("HEARTS") && card2.getType().equals("DIAMONDS")) ||
                (card1.getType().equals("DIAMONDS") && card2.getType().equals("HEARTS")) ||
                (card1.getType().equals("SPADES") && card2.getType().equals("CLUBS"))    ||
                (card1.getType().equals("CLUBS") && card2.getType().equals("SPADES") )
        );

    }

    private void waitPlayersToDropPairs() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasCards() {
        return !handCards.isEmpty();
    }

    private void play() {
        Player player = mediator.getGame().getNextPlayer();
        Card card = addRandomCard(player);
        System.out.println(name + " draw "+ card + " from " + player.name + " and now has " + handCards.size() + " cards.");
        discardTwoCards(card);
        mediator.getGame().nextPlayer();
    }

    private Card addRandomCard(Player player) {
        int randomNum = random.nextInt(player.getNumberOfCards());
        Card card = player.getCard(randomNum);
        handCards.add(card);
        player.removeCard(card);
        if(player.getNumberOfCards()==0) {
            mediator.getGame().removePlayerFromGame(player);
        }
        return card;
    }

    private void discardTwoCards(Card card) {
        Card temp = getMatchingCard(card);
        if (temp == null) {
            return;
        }
        if (handCards.contains(temp)) {
            handCards.remove(card);
            handCards.remove(temp);
            System.out.println(name + " drop these cards: ");
            System.out.println(card);
            System.out.println(temp);
        }
    }

    private Card getMatchingCard(Card card) {
        if(card.getType().equals("DIAMONDS")) {
            return new Card(card.getValue(), "HEARTS");
        }
        else if(card.getType().equals("HEARTS")) {
            return new Card(card.getValue(), "DIAMONDS");
        }
        else if(card.getType().equals("CLUBS")) {
            return new Card(card.getValue(), "SPADES");
        }
        else if(card.getType().equals("SPADES")) {
            return new Card(card.getValue(), "CLUBS");
        }
        return null;
    }

    private int getNumberOfCards() {
        return handCards.size();
    }

    private Card getCard(int index) {
        return handCards.get(index);
    }

    private void removeCard(Card card) {
        handCards.remove(card);
    }

    public String name() {
        return name;
    }

    public void addCard(Card card) {
        handCards.add(card);
    }

    public void printCards() {
        System.out.println("------------------------------------------");
        System.out.println(name + " has " + handCards.size() + " cards: ");
        for (Card card:handCards) {
            System.out.println(card);
        }
        System.out.println("------------------------------------------");
    }

}
