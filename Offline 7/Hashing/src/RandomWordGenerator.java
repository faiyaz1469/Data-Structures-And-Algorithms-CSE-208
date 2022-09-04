import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class RandomWordGenerator {
    private final Random rand;
    private final List<String> wList;

    public RandomWordGenerator() {
        rand = new Random();
        wList = new ArrayList<>();
    }

    private void addWord(String string) {
        wList.add(string);
    }

    public String getWord() {
        String string;

        while(true) {
            string = "";

            for(int i=0; i<7; i++) {
                string = string.concat(String.valueOf((char) (97 + rand.nextInt(26))));
            }

            if(!wList.contains(string)) {
                addWord(string);
                break;
            }
        }

        return string;
    }

    public List<String> getRandomWord(List<String> originalList, int totalItems)
    {
        Random rand = new Random();

        List<String> list = new ArrayList<>(originalList);

        List<String> newList = new ArrayList<>();

        for (int i = 0; i < totalItems; i++) {

            int randomIndex = rand.nextInt(list.size());

            newList.add(list.get(randomIndex));

            list.remove(randomIndex);
        }
        return newList;
    }

}

