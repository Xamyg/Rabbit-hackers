import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Test {
    public static void test1(){
        try {
            PrintWriter pw = new PrintWriter("./testOutput.txt");
            pw.println(4);
            pw.print("rabbit 9");

            Main.readFile(new File("./testOutput.txt"));
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
