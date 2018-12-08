import java.util.Collections;
import java.util.LinkedList;

public class Computer {
    LinkedList<Character> colors = new LinkedList<>();
    LinkedList<String> combinations = new LinkedList<>();
    int guesses = 0;
    boolean won = false;
    Mastermind game;
    int[] result;

    public Computer(Mastermind game) {
        this.game = game;
    }

    public int[] getResult() {
        return result;
    }

    public String doTurn(){
        String guessString = "";
        if (guesses < 6 && colors.size() < 4 && !won) {
            //switch (guesses) {
                if(guesses == 0) {
                    guessString = "aaaa";
                    result = game.guess(guessString);
                    if (result[0] > 0) {
                        for (int i = 0; i < result[0]; i++) {
                            colors.push('a');
                        }
                    }
                }
                else if(guesses == 1) {
                    guessString = "bbbb";
                    result = game.guess(guessString);
                    if (result[0] > 0) {
                        for (int i = 0; i < result[0]; i++) {
                            colors.push('b');
                        }
                    }
                }
                else if(guesses == 2) {
                    guessString = "cccc";
                    result = game.guess(guessString);
                    if (result[0] > 0) {
                        for (int i = 0; i < result[0]; i++) {
                            colors.push('c');
                        }
                    }
                }
                else if(guesses == 3) {
                    guessString = "dddd";
                    result = game.guess(guessString);
                    if (result[0] > 0) {
                        for (int i = 0; i < result[0]; i++) {
                            colors.push('d');
                        }
                    }
                }
                if(guesses == 4) {
                    guessString = "eeee";
                    result = game.guess(guessString);
                    if (result[0] > 0) {
                        for (int i = 0; i < result[0]; i++) {
                            colors.push('e');
                        }
                    }
                }
                else if(guesses == 5) {
                    guessString = "ffff";
                    result = game.guess(guessString);
                    if (result[0] > 0) {
                        for (int i = 0; i < result[0]; i++) {
                            colors.push('f');
                        }
                    }
                }


                if (colors.size() == 4) {
                    generateCombinaitons();
                }

        } else if(!won){
            guessString = combinations.pop();
            result = game.guess(guessString);
            if(result[0] == 4){
                won = true;
            }else if(result[1] == 4){
                LinkedList<String> toRemove = new LinkedList<>();
                for(int i = 0; i < guessString.length(); i++){
                    for (String s: combinations){
                        if (s.charAt(i) == guessString.charAt(i)){
                            toRemove.push(s);
                        }
                    }
                }
                for (String s:toRemove){
                    combinations.remove(s);
                }
            }

        }
        guesses++;
        return guessString;
    }
    public boolean CpWin(){
        return won;
    }
    private void generateCombinaitons() {
        String s = "";
        boolean found = false;
        for (int i = 0; i < 4; i++) {
            s += colors.get(i);
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    s += colors.get(j);
                    for (int k = 0; k < 4; k++) {
                        if(k != i && k != j) {
                            s += colors.get(k);
                            for (int l = 0; l < 4; l++) {
                                if(l != i && l != j && l != k){
                                    s+= colors.get(l);
                                    for(String st : combinations){
                                        if(st.equals(s)){
                                            found = true;
                                        }
                                    }
                                    if(!found){
                                        combinations.push(s);
                                    }else {
                                        found = false;
                                    }
                                    s = s.substring(0,s.length()-1);
                                }
                            }
                            s = s.substring(0,s.length()-1);
                        }
                    }
                    s = s.substring(0,s.length()-1);
                }
            }
            s = s.substring(0,s.length()-1);
        }
        Collections.shuffle(combinations);
    }
}

