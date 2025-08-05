// Chooser - a class badly in need of generics!
// Bloch 3rd edition, Chapter 5, Item 28:  Prefer lists to arrays
import java.util.*;
import java.util.Collection;
import java.util.concurrent.*;

public class Chooser {
    private final Object[] choiceArray;

    public Chooser (Collection choices) {
        choiceArray = choices.toArray();
    }

    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceArray [rnd.nextInt(choiceArray.length)];
    }
}