import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 23.03.13
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("Test");

        List<Integer> hashArray = new ArrayList<Integer>();
        for(int i=0; i<10; i++) {
            hashArray.add(null);
        }

        hashArray.set(4, 5);

        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + hashArray.get(i));
        }

        Map<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        hashMap.put(49, 5);
        hashMap.put(48, 5);

        System.out.println(hashMap.keySet());
        System.out.println(hashMap.toString());


        String tmp = "test: " + null + ";";
        System.out.println(tmp);
    }
}
