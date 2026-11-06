import java.util.Objects;

public class Card {
    private String rank, suit;

    Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Card))
            return false;

        Card c = (Card) obj;

        return rank.equals(c.rank) &&
               suit.equals(c.suit);
    }

    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    public static void main(String[] args) {
        Card[] cards = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Queen", "Diamonds"),
            new Card("Ace", "Spades")
        };

        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < i; j++) {
                if (cards[i].equals(cards[j])) {
                    System.out.println(
                        "Duplicate found: " + cards[i]
                    );
                    return;
                }
            }
        }
    }
}