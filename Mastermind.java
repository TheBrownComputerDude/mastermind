import java.util.LinkedList;

public class Mastermind{
    private char[] combinaiton;
    public Mastermind(){
        generateCombination();
    }
    private boolean checkWin(int[] r){
        return r[0] == 4;
    }
    private void generateCombination(){
        combinaiton = new char[4];
        for(int i = 0; i < 4; i++){
            combinaiton[i] = (char)('f'-Math.floor(Math.random()*6));
        }
    }
    public int[] guess(String UserGuess){
        int[] result = new int[2]; //0 = right color and position, 1 = right color
        boolean found = false;
        LinkedList<Character> AllRight = new LinkedList<>();
        LinkedList<Character> used = new LinkedList<>();
        LinkedList<Character> checked = new LinkedList<>();
        for(int i = 0; i < UserGuess.length(); i++){
            if(UserGuess.charAt(i) == combinaiton[i]){
                result[0]++;
                AllRight.push(UserGuess.charAt(i));
                continue;
            }
            for(char c : combinaiton){
                if(UserGuess.charAt(i) == c){
                    if(countCharL(used,c) < countCharA(c)) {
                        used.push(c);
                        found = true;
                        break;
                    }
                }
            }
            if(found){
                result[1]++;
                found = false;
            }
        }
        for(char c: combinaiton){
            if(countCharA(c) == countCharL(AllRight,c) && !checked.contains(c)){
                checked.push(c);
                result[1] -= countCharL(used,c);
            }
        }

        return result;
    }
    private int countCharA(char c){
        int count = 0;
        for (char x:combinaiton){
            if(x == c) count++;

        }
        return count;
    }
    private int countCharL(LinkedList<Character> l, char c){
        int count = 0;
        for (char x:l){
            if(x == c) count++;

        }
        return count;
    }
}