import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignTester {
    public static void main(String[] args) throws Exception
    {
        BufferedReader          reader = new BufferedReader(new FileReader("./signatures.txt"));
        BufferedReader          smplreader = new BufferedReader(new FileReader("./sample.txt"));
        String                  line, smbline, smplline, signline;
        Map<String, String>     mytemplate = new HashMap<>();
        smplline = smplreader.readLine();
        StringBuffer            finline;
        while ((line = reader.readLine()) != null)
        {
            smbline = line.substring(line.indexOf(',') + 2);
            signline = line.substring(0, line.indexOf(','));
            String[]            smbls = smbline.split(" ");
            finline = new StringBuffer();
            for (int i = 0; i < smbls.length; ++i)
                finline.insert(finline.length(), (char) Integer.parseUnsignedInt(smbls[i], 16));
            mytemplate.put(finline.toString(), signline);
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
            StringBuffer        linetocheck = new StringBuffer();
            for (int i = 0; i < count; ++i)
            {
                linetocheck.insert(linetocheck.length(), (char) buffer[i]);
                if (mytemplate.containsKey(linetocheck.toString()))
                {
                    String      out = mytemplate.get(linetocheck.toString()) + System.lineSeparator();
                    fos.write(out.getBytes());
                    break;
                }
            }
            System.out.println("PROCESSED");
            fis.close();
        }
    }
}

