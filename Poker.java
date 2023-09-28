import java.util.Random;

class Poker {


    public static void main(String[] args) {
        int c = 0;
        while (true) {
            c++;
            Deck deck = new Deck();
            Card[] hand = new Card[5];
    
    
            for (int i=0; i<5; i++) {
                hand[i] = deck.DrawOne();
            }
    
            for (int i=0; i<5; i++) {
                System.out.print(hand[i].Serialize() + " ");
            }

            String result = HandInspector.inspect(hand);
            System.out.print("\n" + result + "\n");

            if (result.equals("straight flash")) {
                System.out.println("total: " + c);
                break;
            }
        }
    }
}

class Card {
    public int n;
    public int s;

    public static final int CLOVER = 0;
    public static final int DIAMOND = 1;
    public static final int HEART = 2;
    public static final int SPADE = 3;

    public Card(int n, int s) {
        this.n = n;
        this.s = s;
    }

    public String Serialize() {
        String[] symbols = {"♣️", "♦︎", "❤︎", "♠️"};
        return symbols[s] + n;
    }
}

class Deck {
    private Card[] cards;
    private int head_point;
    
    public Deck() {
        this.head_point = 0;
        this.cards = new Card[52]; // except joker
        for (int n=0; n<13; n++) {
            for (int s=0; s<4; s++) {
                cards[n + s * 13] = new Card(n+1, s);
            }
        }
        this.Shuffle();
    }

    public void Shuffle() {
        Random r = new Random();
        for (int i = this.cards.length - this.head_point - 1; i > 0; i--) {
            int index = r.nextInt(i + 1) + this.head_point;
            Card tmp = cards[index];
            cards[index] = cards[i];
            cards[i] = tmp;
        }
    }

    public Card DrawOne() {
        return this.cards[this.head_point++];
    }
}

class HandInspector {
    public static String inspect(Card[] hand) {
        int[] n = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] s = {0, 0, 0, 0};

        for (Card c: hand) {
            n[c.n-1]++;
            s[c.s]++;
        }

        boolean isFullSuit = false;
        for (int i=0; i<4; i++) {
            if (s[i] == 5) isFullSuit = true;
        }

        if ((n[0] + n[12] + n[11] + n[10] + n[9] == 5) && isFullSuit) {
            return "loyal straight flash";
        }

        int streak = 0;
        boolean isThreeCards = false;
        int pair = 0;
        for (int i=0; i<13; i++) {
            if (n[i] == 4) {
                return "four cards";
            }

            if (n[i] == 3) {
                isThreeCards = true;
            }

            if (n[i] == 2) {
                pair++;
            }

            if (n[i] == 1) {
                streak++;
            }

            if (n[i] == 0) {
                streak = 0;
            }

            if (streak == 5) {
                break;
            }
        }

        if (streak == 5 && isFullSuit) {
            return "straight flash";
        }

        if (isThreeCards && pair == 1) {
            return "full house";
        }

        if (isFullSuit) {
            return "flash";
        }

        if (streak == 5) {
            return "straight";
        }

        if (isThreeCards) {
            return "three cards";
        }

        if (pair == 2) {
            return "two pair";
        }

        if (pair == 1) {
            return "one pair";
        }

        return "no hand";
    }
}
