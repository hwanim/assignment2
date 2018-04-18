import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class Compression {

    private static StringBuffer mResultBuffer, mInputBuffer;
    private static ArrayList<String> mKeyArray,mValueArray;

    public static void main(String[] args) throws FileNotFoundException{
        File dictionary = new File("dictionary.txt");
        File input = new File("input.txt");
        String result = null;
        Scanner dicScanner = new Scanner(dictionary);
        Scanner inpScanner = new Scanner(input);
        mKeyArray = new ArrayList<>();
        mValueArray = new ArrayList<>();

        String[] inputArray = inpScanner.nextLine().split(",");
//        System.out.println(Arrays.toString(inputArray));
        while (dicScanner.hasNext()){
            String[] dicInputArray = dicScanner.nextLine().split(",");
            switch (inputArray[1]) {
                case "C":
                    mKeyArray.add(dicInputArray[0]);
                    mValueArray.add(dicInputArray[1]);
                    break;

                case "D":
                    mKeyArray.add(dicInputArray[1]);
                    mValueArray.add(dicInputArray[0]);
            }
        }
//        System.out.println(Arrays.toString(mKeyArray.toArray()));
//        System.out.println(Arrays.toString(mValueArray.toArray()));
        result = operate(inputArray[2]);

        PrintStream printStream = new PrintStream("output.txt");
        printStream.println(result);
        printStream.close();
    }


    private static String operate(String inputString) {
        mResultBuffer = new StringBuffer();
        mInputBuffer = new StringBuffer(inputString);
//        System.out.println(mInputBuffer);

        while (mInputBuffer.length() != 0) {
            excuteCompare();
        }

        return mResultBuffer.toString();
    }

    private static void excuteCompare() {
        for (String key : mKeyArray){
            if (key.equals(mInputBuffer.substring(0, key.length()))) {
                mResultBuffer.append(mValueArray.get(getIndexOf(mKeyArray,key)));
                mInputBuffer.delete(0,key.length());
//                System.out.println(mInputBuffer);
//                System.out.println(mResultBuffer);
                break;
            }
        }
    }

    private static int getIndexOf(ArrayList<String> array, String string) {
        int count = 0;
        for (String str : array) {
            if (str.equals(string)) return count;
            count++;
        }
        return -1;
    }

}
