package net.tv.twitch.chrono_fish.ito_paper.Manager;

import java.util.ArrayList;

public class NumberManager {
    private ArrayList<Integer> numbers = new ArrayList<>();

    public NumberManager(){
        for(int i=1; i<=100; i++){
            numbers.add(i);
        }
    }

    public ArrayList<Integer> getNumbers() {return numbers;}
}
