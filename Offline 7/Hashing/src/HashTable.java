import java.util.List;
import java.util.ArrayList;

public class HashTable {
    private final int length;
    private final int option;
    private final List<KeyValuePair>[] array;

    public HashTable(int length, int option) {
        this.length = length;
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

    public int count1(String[] words){
        List<Long> cnt = new ArrayList<>();

        for(int i=0; i<words.length; i++){
            long temp = hashFunction1(words[i]);
            int flag = 0;

            if(cnt.size() == 0){
                cnt.add(temp);
            }
            else {
                for(int j=0; j<cnt.size(); j++){
                    if(temp == cnt.get(j))
                        flag = 1;
                }
                if(flag == 0)
                    cnt.add(temp);
            }
        }

        return cnt.size();

    }

    public int count2(String[] words){
        List<Long> cnt = new ArrayList<>();

        for(int i=0; i<words.length; i++){
            long temp = hashFunction2(words[i]);
            int flag = 0;

            if(cnt.size() == 0){
                cnt.add(temp);
            }
            else {
                for(int j=0; j<cnt.size(); j++){
                    if(temp == cnt.get(j))
                        flag = 1;
                }
                if(flag == 0)
                    cnt.add(temp);
            }
        }

        return cnt.size();

    }

    public static void main(String[] args) {
        String[] words = new String[100];
        RandomWordGenerator object = new RandomWordGenerator();
        HashTable ht = new HashTable(100,1);

        for(int i=0; i<100; i++) {
            words[i] = object.getWord();
        }

        System.out.println(ht.count1(words));
        System.out.println(ht.count2(words));

    }


     /*private long hashFunction2(String str) {
        //sdbm
        long hash = 0;

        for (int i = 0; i < str.length(); i++)
        {
            hash = Character.getNumericValue(str.charAt(i)) + (hash << 6) + (hash << 16) - hash;
        }

        return (hash % length);
     }*/


    /*private long hashFunction2(String key) {
        long hash = 97;

        for(int i=0; i<key.length(); i++) {
            hash = ((hash<<3) + i*hash + (int) key.charAt(i));
        }

        return (hash % length);
    }*/

    private long linearProbing(String key, int i) {
        return (hashFunction1(key)+i) % length;
    }

    private long quadraticProbing(String key, int i) {
        long C1 = 509;
        long C2 = 91;

        return (hashFunction1(key) + C1*i + C2*i*i) % length;
    }

    private long doubleHashing(String key, int i) {
        return (hashFunction1(key) + i*hashFunction2(key)) % length;
    }


    // insert operation
    private void insertOpen(String key, int val) {
        // separate chaining (Open Hashing)
        int index;

        index = (int) hashFunction1(key);

        array[index].add(new KeyValuePair(key, val));
    }

    private void insertClosedDouble(String key, int val) {
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
    }

    private void insertClosedLinear(String key, int val) {
        // Linear Prob hashing (closed hashing, open addressing)
        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) linearProbing(key, tempIndex);

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

    private void insertClosedQuadratic(String key, int value) {
        // Quadratic Prob hashing (closed hashing, open addressing)
        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) quadraticProbing(key, tempIndex);

            if(array[index].isEmpty()) {
                array[index].add(new KeyValuePair(key, value));
                break;
            }

            if(array[index].get(0).getKey().equals("")) {
                array[index].remove(0);  // **
                array[index].add(new KeyValuePair(key, value));
                break;
            }
        }
    }

    // search operation
    private int searchOpen(String key) {

        int tempIndex,index;

        index = (int) hashFunction1(key);

        for(tempIndex=0; tempIndex<array[index].size(); tempIndex++) {
            if(array[index].get(tempIndex).getKey().equals(key)) {
                break;
            }
        }

        return (tempIndex + 1);  // for empty index, still need to access to hash table to check emptiness
    }

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

    private int searchClosedLinear(String key) {
        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) linearProbing(key, tempIndex);

            if(array[index].isEmpty() || array[index].get(0).getKey().equals(key)) {
                break;
            }
        }

        if(tempIndex == length) {
            return tempIndex;
        }

        return (tempIndex + 1);
    }

    private int searchClosedQuadratic(String key) {
        int index, tempIndex;

        for(tempIndex=0; tempIndex<length; tempIndex++) {
            index = (int) quadraticProbing(key, tempIndex);

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
    private void deleteOpen(String key) {
        int index, tempIndex;
        index = (int) hashFunction1(key);

        for(tempIndex=0; tempIndex<array[index].size(); tempIndex++) {
            if(array[index].get(tempIndex).getKey().equals(key)) {
                break;
            }
        }

        if(tempIndex < array[index].size()) {
            array[index].remove(tempIndex);
        }

    }

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

    private void deleteClosedLinear(String key) {
        int index = -1;

        for(int i=0; i<length; i++) {
            index = (int) linearProbing(key, i);

            if(array[index].isEmpty() || array[index].get(0).getKey().equals(key)) {
                break;
            }
        }

        array[index].remove(0);
        array[index].add(new KeyValuePair("", -1));  // ** - dummy nd
    }

    private void deleteClosedQuadratic(String key) {
        int index = -1;

        for(int i=0; i<length; i++) {
            index = (int) quadraticProbing(key, i);

            if(array[index].isEmpty() || array[index].get(0).getKey().equals(key)) {
                break;
            }
        }

        array[index].remove(0);
        array[index].add(new KeyValuePair("", -1));  // ** - dummy nd
    }


    // public methods
    public void insert(String key, int value) {
        if(option%4 == 0)
            insertOpen(key, value);

        if(option%4 == 1)
            insertClosedDouble(key, value);

        if(option%4 == 2)
            insertClosedLinear(key, value);

        if(option%4 == 3)
            insertClosedQuadratic(key, value);

    }

    public int search(String key) {
        if(option%4 == 0)
            return searchOpen(key);

        if(option%4 == 1)
            return searchClosedDouble(key);

        if(option%4 == 2)
            return searchClosedLinear(key);

        if(option%4 == 3)
            return searchClosedQuadratic(key);

        return -1;
    }

    public void delete(String key) {
        if(option%4 == 0)
            deleteOpen(key);

        if(option%4 == 1)
            deleteClosedDouble(key);

        if(option%4 == 2)
            deleteClosedLinear(key);

        if(option%4 == 3)
            deleteClosedQuadratic(key);
    }
}
