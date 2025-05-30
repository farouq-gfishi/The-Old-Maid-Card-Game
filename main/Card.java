import java.util.Objects;

public class Card {

    private final String value;
    private final String type;

    public Card(String value, String type) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(value, card.value) && Objects.equals(type, card.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
