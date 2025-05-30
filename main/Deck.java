import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card>cards;
    private final String[]values = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[]types = {"DIAMONDS", "HEARTS", "SPADES", "CLUBS"};

    public Deck() {
        cards = new ArrayList<>();
        createCards();
        shuffleCards();
    }

    private void createCards() {
        for(String value:values) {
            for(String type:types) {
                cards.add(new Card(value,type));
            }
        }
        cards.add(new Card("JOKER", "JOKER"));
    }

    private void shuffleCards() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

}
