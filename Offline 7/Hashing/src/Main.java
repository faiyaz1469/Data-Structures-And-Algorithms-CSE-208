import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RandomWordGenerator randObj = new RandomWordGenerator();
        double loadFactor = 0.4;

        System.out.print("Enter the length of hash table: ");
        int N = scanner.nextInt();  //N buckets (entry/buckets = load factor)

        while (loadFactor <= 0.9){

            int entry = (int) Math.ceil(N*loadFactor);

            List<String > wordList = new ArrayList<>();

            for(int i=0; i<entry; i++) {
                wordList.add(randObj.getWord());
            }

            // Option -> 0 - separate chaining; 1 - double hashing; 2 - linear prob; 3 - quadratic prob;
            HashTable ht1 = new HashTable(N, 0);
            HashTable ht2 = new HashTable(N, 1);
            HashTable ht3 = new HashTable(N, 2);
            HashTable ht4 = new HashTable(N, 3);

            int[] numberProbes = new int[8];

            for(int i=0; i<8; i++) {
               numberProbes[i] = 0;
            }

            // inserting words
            for(int i=0; i<wordList.size(); i++) {
                ht1.insert(wordList.get(i), i+1);
                ht2.insert(wordList.get(i), i+1);
                ht3.insert(wordList.get(i), i+1);
                ht4.insert(wordList.get(i), i+1);
            }

            int newEntry = (int) Math.ceil(0.1* wordList.size()); // 10% of the inserted elements

            List<String> wordList2 = randObj.getRandomWord(wordList,newEntry);

            long res1 = 0;
            long res2 = 0;
            long res3 = 0;
            long res4 = 0;

            // searching words before deletion
            for(int i=0; i< wordList2.size(); i++) {

                long start1 = System.nanoTime();
                numberProbes[0] += ht1.search(wordList2.get(i));
                long end1 = System.nanoTime();
                res1 += end1-start1;

                long start2 = System.nanoTime();
                numberProbes[1] += ht2.search(wordList2.get(i));
                long end2 = System.nanoTime();
                res2 += end2-start2;

                long start3 = System.nanoTime();
                numberProbes[2] += ht3.search(wordList2.get(i));
                long end3 = System.nanoTime();
                res3 += end3-start3;

                long start4 = System.nanoTime();
                numberProbes[3] += ht4.search(wordList2.get(i));
                long end4 = System.nanoTime();
                res4 += end4-start4;

            }

            List<String> wordList3 = randObj.getRandomWord(wordList,newEntry);

            // deleting words
            for(int i=0; i<wordList3.size(); i++) {
                ht1.delete(wordList3.get(i));
                ht2.delete(wordList3.get(i));
                ht3.delete(wordList3.get(i));
                ht4.delete(wordList3.get(i));
            }

            long res5 = 0;
            long res6 = 0;
            long res7 = 0;
            long res8 = 0;

            // searching words after deletion
            for(int i=0; i< wordList2.size(); i++) {

                long start1 = System.nanoTime();
                numberProbes[4] += ht1.search(wordList2.get(i));
                long end1 = System.nanoTime();
                res5 += end1-start1;

                long start2 = System.nanoTime();
                numberProbes[5] += ht2.search(wordList2.get(i));
                long end2 = System.nanoTime();
                res6 += end2-start2;

                long start3 = System.nanoTime();
                numberProbes[6] += ht3.search(wordList2.get(i));
                long end3 = System.nanoTime();
                res7 += end3-start3;

                long start4 = System.nanoTime();
                numberProbes[7] += ht4.search(wordList2.get(i));
                long end4 = System.nanoTime();
                res8 += end4-start4;

            }

            // processing the report
            System.out.println();
            System.out.println("Load Factor " + loadFactor +  "                                   Before Deletion                                     After Deletion");
            System.out.println("for Separate Chaining Method: Avg search time (ns) = " + res1/newEntry + " | Avg # of probes = NA | Avg search time (ns) = " + res5/newEntry + " | Avg # of probes = NA");
            System.out.println("for Double Hashing:           Avg search time (ns) = " + res2/newEntry + " | Avg # of probes = " + numberProbes[1]/newEntry + "  | Avg search time (ns) = " + res6/newEntry + " | Avg # of probes = " + numberProbes[5]/newEntry);
            System.out.println("for Linear Probing:           Avg search time (ns) = " + res3/newEntry + " | Avg # of probes = " + numberProbes[2]/newEntry + "  | Avg search time (ns) = " + res7/newEntry + " | Avg # of probes = " + numberProbes[6]/newEntry);
            System.out.println("for Quadratic Probing:        Avg search time (ns) = " + res4/newEntry + " | Avg # of probes = " + numberProbes[3]/newEntry + "  | Avg search time (ns) = " + res8/newEntry + " | Avg # of probes = " + numberProbes[7]/newEntry);
            System.out.println();

            loadFactor *= 10;
            loadFactor += 1;
            loadFactor /= 10;
        }
    }
}
