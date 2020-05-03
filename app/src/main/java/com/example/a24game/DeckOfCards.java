package com.example.a24game;

import java.util.ArrayList;
import java.util.List;

public class DeckOfCards {
    private List<Integer> deck;
    private List<Integer> current;
    public DeckOfCards() {
        deck = new ArrayList<>();
        current = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 13; j++) {
                deck.add(new Integer(j));
            }
        }
    }

    public void randomSolvableFromTheDeck() {
        boolean solvable = false;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        Solvable now;
        while (solvable == false) {
            a = (int) (Math.random() * (deck.size() + 0.1));
            b = (int) (Math.random() * (deck.size() + 0.1));
            c = (int) (Math.random() * (deck.size() + 0.1));
            d = (int) (Math.random() * (deck.size() + 0.1));
            now = new Solvable(deck.get(a).intValue(), deck.get(b).intValue(),
                    deck.get(c).intValue(), deck.get(d).intValue());
            solvable = now.isSolvable();
        }
        current = new ArrayList<>();
        current.add(deck.get(a).intValue());
        current.add(deck.get(b).intValue());
        current.add(deck.get(c).intValue());
        current.add(deck.get(d).intValue());
        deck.remove(a);
        deck.remove(b);
        deck.remove(c);
        deck.remove(d);
    }
    public void returnWhenGiveUp() {
        deck.add(current.get(1));
        deck.add(current.get(2));
        deck.add(current.get(3));
        deck.add(current.get(0));
    }
}
