import java.util.List;
import java.util.ArrayList;

public class HashTable {
    private int length;
    private int finLength;
    private final int option;
    private final List<KeyValuePair>[] array;

    public HashTable(int length, int option) {
        this.length = length;
        this.finLength = length;
        this.option = option;
        array = new List[this.length];

        for(int i=0; i<this.length; i++) {
            array[i] = new ArrayList<>(2);  // **
        }
    }

    private long hashFunction1(String key) {
        //polynomial rolling hash function
        int p = 31;
        long hash = 0;
        long pow = 1;

        for(int i=0; i<key.length(); i++) {
            hash = (hash + (key.charAt(i)-'a'+1)*pow) % length;
            pow = (pow*p) % length;
        }

        return hash;
    }

    private long hashFunction2(String key) {
        //djb2
        long hash = 5381;

        for(int i=0; i<key.length(); i++) {
            hash = ((hash<<5) + hash) + Character.getNumericValue(key.charAt(i));
        }

        return (hash % length);
    }

    private long doubleHashing(String key, int i) {
        return (hashFunction1(key) + i*hashFunction2(key)) % length;
    }

    // insert operation
    private int insertClosedDouble(String key, int val) {
        // double hashing (closed hashing, open addressing)
        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) doubleHashing(key, tempIndex);

            if(array[index].isEmpty()) {
                array[index].add(new KeyValuePair(key, val));
                break;
            }

            if(array[index].get(0).getKey().equals("")) {
                array[index].remove(0);  // **
                array[index].add(new KeyValuePair(key, val));
                break;
            }
        }

        if(tempIndex == length) {
            return tempIndex;
        }

        return (tempIndex + 1);
    }

    public void reHashing(){
        length = 2 * length;
        while(!isPrime(length)){
            length++;
        }
    }

    public void reHashing2(){
        length = finLength/2;
        while(!isPrime(length)){
            length++;
        }
    }

    public void reHashInsert(String key, int val){

        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) doubleHashing(key, tempIndex);

            if(array[index].isEmpty()) {
                array[index].add(new KeyValuePair(key, val));
                break;
            }

            if(array[index].get(0).getKey().equals("")) {
                array[index].remove(0);  // **
                array[index].add(new KeyValuePair(key, val));
                break;
            }
        }

    }


    static boolean isPrime(int n)
    {
        if (n <= 1)
            return false;

        else if (n == 2)
            return true;

        else if (n % 2 == 0)
            return false;

        for (int i = 3; i <= Math.sqrt(n); i += 2)
        {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    // search operation
    private int searchClosedDouble(String key) {
        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) doubleHashing(key, tempIndex);

            if(array[index].isEmpty() || array[index].get(0).getKey().equals(key)) {
                break;
            }

        }

        if(tempIndex == length) {
            return tempIndex;
        }

        return (tempIndex + 1);
    }

    // deletion operation
    private void deleteClosedDouble(String key) {
        int index = -1;  // **

        for(int i=0; i<length; i++) {
            index = (int) doubleHashing(key, i);

            if(array[index].isEmpty() || array[index].get(0).getKey().equals(key)) {
                break;
            }

        }

        array[index].remove(0);
        array[index].add(new KeyValuePair("", -1));  // ** - dummy nd
    }


    // public methods
    public int insert(String key, int value) {

        if(option%4 == 1)
            return insertClosedDouble(key, value);

        return -1;
    }

    public int search(String key) {

        if(option%4 == 1)
            return searchClosedDouble(key);

        return -1;
    }

    public void delete(String key) {

        if(option%4 == 1)
            deleteClosedDouble(key);
    }
}
