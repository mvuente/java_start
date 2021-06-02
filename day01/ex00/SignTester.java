import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignTester {
    public static void main(String[] args) throws Exception
    {
        BufferedReader          reader = new BufferedReader(new FileReader("./signatures.txt"));
        String                  line, smbline, signline;
        Map<String, String>     mytemplate = new HashMap<>();
        String                  finline;
        while ((line = reader.readLine()) != null)
        {
            smbline = line.substring(line.indexOf(',') + 2);
            signline = line.substring(0, line.indexOf(','));
            String[]            smbls = smbline.split(" ");
            finline = new String();
            for (int i = 0; i < smbls.length; ++i)
            {
                int myint = Integer.valueOf(smbls[i], 16);
                finline += (char) myint;
            }
            mytemplate.put(finline, signline);
        }
        Scanner                 scanner = new Scanner(System.in);
        String                  newfileaddr;
        File                    file = new File("./result.txt");
        FileOutputStream        fos = new FileOutputStream(file, true);
        while (true)
        {
            int                 val;
            newfileaddr = scanner.nextLine();
            if ((val = newfileaddr.compareTo("42")) == 0)
            {
                fos.close();
                System.exit(0);
            }
            FileInputStream     fis = new FileInputStream(newfileaddr);
            byte[]              buffer = new byte[8];
            int                 count = fis.read(buffer);
            String              linetocheck = new String();
            for (int i = 0; i < count; ++i)
            {
               int              myint = (int) buffer[i] & 0xff;
                linetocheck += (char) myint;
                if (mytemplate.containsKey(linetocheck))
                {
                    String      out = mytemplate.get(linetocheck) + System.lineSeparator();
                    fos.write(out.getBytes());
                    break;
                }
            }
            System.out.println("PROCESSED");
            fis.close();
        }
    }
}

