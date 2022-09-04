import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class online {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RandomWordGenerator randObj = new RandomWordGenerator();
        double loadFactor = 0.75;

        System.out.print("Enter the length of hash table: ");
        int N = scanner.nextInt();  //N buckets (entry/buckets = load factor)

            int entry = (int) Math.ceil(N*loadFactor);

            List<String > wordList = new ArrayList<>();

            for(int i=0; i<entry; i++) {
                wordList.add(randObj.getWord());
            }

            // Option -> 0 - separate chaining; 1 - double hashing; 2 - linear prob; 3 - quadratic prob;
            //HashTable ht1 = new HashTable(N, 0);
            HashTable ht2 = new HashTable(N, 1);
            HashTable ht3 = new HashTable(N, 1);
            HashTable ht4 = new HashTable(N, 1);

            int[] numberProbes = new int[8];

            for(int i=0; i<8; i++) {
                numberProbes[i] = 0;
            }

            int m = 0;
            int count = 0;

            // inserting words
            for(int i=0; i<wordList.size(); i++) {

               numberProbes[0] += ht2.insert(wordList.get(i), i+1);
               m++;
               count++;

                if(m>=100){
                    if(numberProbes[0]/count > 2) {
                        ht3.reHashing();
                        for(int j=0; j<wordList.size(); j++)
                            ht3.reHashInsert(wordList.get(j), j+1);

                        m=0;
                    }
                }
            }

            int newEntry = (int) Math.ceil(0.1* wordList.size()); // 10% of the inserted elements

            List<String> wordList2 = randObj.getRandomWord(wordList,newEntry);

            long res2 = 0;
            long res3 = 0;
            long res4 = 0;
            long res5 = 0;

            // searching words before & after rehashing
            for(int i=0; i< wordList2.size(); i++) {

                long start2 = System.nanoTime();
                numberProbes[1] += ht2.search(wordList2.get(i));
                long end2 = System.nanoTime();
                res2 += end2-start2;

                long start3 = System.nanoTime();
                numberProbes[2] += ht3.search(wordList2.get(i));
                long end3 = System.nanoTime();
                res3 += end3-start3;

            }

            List<String> wordList3 = randObj.getRandomWord(wordList,newEntry);

            // deleting words
            int n = 0;
            for(int i=0; i<wordList3.size(); i++) {

                ht2.delete(wordList3.get(i));
                n++;
                double tempCount = entry - n;
                double tempLoadFactor = tempCount / N;

                if(tempLoadFactor < 0.4){
                    ht4.reHashing2();
                    for(int j=0; j<wordList.size(); j++)
                        ht4.reHashInsert(wordList.get(j), j+1);
                }
            }

            // searching words before &after rehashing
            for(int i=0; i< wordList2.size(); i++) {

                long start2 = System.nanoTime();
                numberProbes[3] += ht2.search(wordList2.get(i));
                long end2 = System.nanoTime();
                res4 += end2-start2;

                long start3 = System.nanoTime();
                numberProbes[4] += ht4.search(wordList2.get(i));
                long end3 = System.nanoTime();
                res5 += end3-start3;

            }

            // processing the report
            System.out.println();
            System.out.println("Load Factor " + loadFactor +  "                                   Before rehashing                                     After Rehashing");
            System.out.println("for Double Hashing Insertion:           Avg search time (ns) = " + res2/newEntry + " | Avg # of probes = " + numberProbes[1]/newEntry + "  | Avg search time (ns) = " + res3/newEntry + " | Avg # of probes = " + numberProbes[2]/newEntry);
            System.out.println("for Double Hashing Deletion:           Avg search time (ns) = " + res4/newEntry + " | Avg # of probes = " + numberProbes[3]/newEntry + "  | Avg search time (ns) = " + res5/newEntry + " | Avg # of probes = " + numberProbes[4]/newEntry);

            System.out.println();

    }
}
