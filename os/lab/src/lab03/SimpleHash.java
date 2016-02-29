package lab03;

import lab03.Main.Hashing;

public class SimpleHash implements Hashing {
    
    private static final int[] WEIGHT = new int[] {
        31,  37,  41,  43,  47,  53,  59,  61,  67,  71
    };

    @Override
    public String hash(String value) {
        if (value.length() > 9) throw new IllegalArgumentException();
        int key = 0;
        for (int i = 0; i < value.length(); i++) key += stoi(value.charAt(i)) * WEIGHT[i];
        return itos(key % 1000);
    }
    
    private int stoi(char ch) {
        return ch - '0';
    }
    
    private String itos(int value) {
        if (value < 10) return "00" + ('0' + value);
        else if (value < 100) return "0" + value;
        else return "" + value;
    }

}
