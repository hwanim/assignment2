import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Compression {

    private static Map<String, String> mDictionaryMap;
    private static StringBuffer mResultBuffer, mInputBuffer;

    public static void main(String[] args) throws FileNotFoundException{
        File dictionary = new File("src/dictionary.txt");
        File input = new File("src/input.txt");
        String result = null;
        Scanner dicScanner = new Scanner(dictionary);
        Scanner inpScanner = new Scanner(input);
        mDictionaryMap = new HashMap<>();

        String[] inputArray = inpScanner.nextLine().split(",");
        System.out.println(Arrays.toString(inputArray));
        while (dicScanner.hasNext()){
            String[] dicInputArray = dicScanner.nextLine().split(",");
            switch (inputArray[1]) {
                case "C":
                    mDictionaryMap.put(dicInputArray[0], dicInputArray[1]);
                    break;

                case "D":
                    mDictionaryMap.put(dicInputArray[1], dicInputArray[0]);
            }
        }
        System.out.println(mDictionaryMap.toString());

        result = operate(inputArray[2]);

        PrintStream printStream = new PrintStream("output.txt");
        printStream.println(result);
        printStream.close();
    }


    private static String operate(String string) {
        mResultBuffer = new StringBuffer();
        mInputBuffer = new StringBuffer(string);
        System.out.println(mInputBuffer);

        while (mInputBuffer.length() != 0) {
            excuteCompare();
        }

        return mResultBuffer.toString();
    }

    private static void excuteCompare() {
        for (String key : mDictionaryMap.keySet()){
            if (key.equals(mInputBuffer.substring(0, key.length()))) {
                mResultBuffer.append(mDictionaryMap.get(key));
                mInputBuffer.delete(0,key.length());
                System.out.println(mInputBuffer);
                System.out.println(mResultBuffer);
                break;
            }
        }
    }
}
