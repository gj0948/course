package lab03;

public class Main {
    
    /**
     * 12 social security numbers
     */
    private static String[] ssn = {
            "123456789",
            "234567890",
            "345678912",
            "456789012",
            "567896789",
            "123456798",
            "987654321",
            "887766554",
            "012345678",
            "113456745",
            "665544332",
            "723456789"
    };

    public static void main(String[] args) {
//        Set<String> set = new HashSet<>();
        Hashing hashing = new SimpleHash();
        for (int i = 0; i < ssn.length; i++) {
//            set.add(hashing.hash(ssn[i]));
            System.out.println(ssn[i] + "\t" + hashing.hash(ssn[i]));
        }
//        System.out.println(set.size());
    }
    
    public interface Hashing {
        String hash(String value);
    }
    
}