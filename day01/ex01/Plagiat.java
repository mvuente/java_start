import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Vector;
import java.util.Scanner;

public class Plagiat {

    public static int       vectmult(Vector<Integer> v1, Vector<Integer> v2)
    {
        int length = v1.size();
        int sum = 0;
        for (int i = 0; i < length; ++i)
            sum += v1.elementAt(i) * v2.elementAt(i);
        return sum;
    }

    public static void mapper(BufferedReader bfr, Map<String, Integer> wordscounter) throws Exception
    {
        int                     smbret;
        String                  word = new String();

        try {
            while ((smbret = bfr.read()) != -1)
            {
                if (Character.isAlphabetic(smbret))
                    word += (char) smbret;
                else
                {
                    if (word.length() != 0)
                    {
                        if (wordscounter.get(word) != null)
                            wordscounter.put(word.toLowerCase(), wordscounter.get(word) + 1);
                        else
                            wordscounter.put(word.toLowerCase(), 1);
                    }
                    word = new String();
                }
            }
        }
        catch (IOException e) {
            System.out.println("File couldn't be opened");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception
    {
        Scanner                 scanner = new Scanner(System.in);
        String                  filename = new String();
        Set<String>             dict = new LinkedHashSet<String>();
        String                  res = new String("0");
        filename = scanner.nextLine();
        String                  files[] = filename.split(" ");
        try {
            BufferedReader          bfr = new BufferedReader(new FileReader(files[0]));
            Map<String, Integer>    wordscounter1 = new HashMap<String, Integer>();
            mapper(bfr, wordscounter1);
            for (String name : wordscounter1.keySet())
                dict.add(name);
            bfr = new BufferedReader(new FileReader(files[1]));
            Map<String, Integer>    wordscounter2 = new HashMap<String, Integer>();
            mapper(bfr, wordscounter2);
            for (String name : wordscounter2.keySet())
                dict.add(name);
            Vector<Integer>     freq1 = new Vector<>();
            Vector<Integer>     freq2 = new Vector<>();
            for (String d : dict)
            {
                if (wordscounter1.get(d) != null)
                    freq1.add(wordscounter1.get(d));
                else
                    freq1.add(0);
                if (wordscounter2.get(d) != null)
                    freq2.add(wordscounter2.get(d));
                else
                    freq2.add(0);
            }
            double  vmod1, vmod2;
            if ((vmod1 = vectmult(freq1, freq1)) != 0 && (vmod2 = vectmult(freq2, freq2)) != 0)
                res = String.format("%.2f", vectmult(freq1, freq2) / Math.sqrt(vmod1 * vmod2));
            System.out.println("Similarity = " + res);
        }
        catch (IOException e) {
            System.out.println("File couldn't be opened");
        }
    }
}
