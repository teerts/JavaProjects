// notes
/* 
How to choose a word randomly?
Use the "StdRandom.bernoulli(p)" function.
Remember the variable "i" I was talking about?

"Knuth’s method: when reading the ith word, 
select it with probability 1/i to be the champion,
replacing the previous champion. After reading
all of the words, print the surviving champion."
*/

// compile
// javac -cp algs4.jar: RandomWord.java
// java -cp algs4.jar: RandomWord < animals8.txt

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args)
    {
        int replacementCount = 1;
        double bernoulliNumerator = 1.0;        
        String theChampion = "";

        while (!StdIn.isEmpty())
        {
            String replacementChampion = StdIn.readString();

            if (StdRandom.bernoulli(bernoulliNumerator / (double) replacementCount))
                theChampion = replacementChampion;

            replacementCount++;
        }
        
        StdOut.println(theChampion);                
    }
}