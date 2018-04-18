import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Warehouse {

    private static ComputerPart[] computerParts;
    private static int TxInitValue = 0;

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("stock.txt");
        File tx = new File("tx.txt");
        StringBuffer buffer = new StringBuffer();

        if (tx.exists()) {
            Scanner tempScanner2 = new Scanner(tx);
            int count = 0;
            while (tempScanner2.hasNext()){
                buffer.append(tempScanner2.nextLine() + "\n");
                count++;
            }
            TxInitValue += count;
        }

        Scanner scanner = new Scanner(file);
        Scanner tempScanner = new Scanner(file);

//        System.out.println(file.toString());

        int count1 = 0;
        while (tempScanner.hasNext()) {
            tempScanner.nextLine();
            count1++;
        }
//        System.out.println(count1);
        computerParts = new ComputerPart[count1];

        count1 =0;
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] array = line.split(",");
//            System.out.println(line);
            computerParts[count1] = new ComputerPart(array[0], array[1], array[2], array[3], array[4]);
            count1++;
        }

        Scanner scanner2 = new Scanner(new File("src/input.txt"));
        int count = TxInitValue;
        while (scanner2.hasNext()){
            count++;
            String line = scanner2.nextLine();
            String[] inputs = line.split(",");
            ComputerPart part = getCP(computerParts, inputs);

            switch (inputs[0]) {
                case "I":
                    if (part == null) {
                        part = addNewPart(computerParts, inputs);
//                        System.out.println("add new parts : " + part.name + " " + part.quantity);
                    } else {
                        part.quantity += Integer.parseInt(inputs[4]);
                    }

                    if (part.price > Integer.parseInt(inputs[3])) {
                        System.out.println("The part became cheap.");
                    } else if (part.price < Integer.parseInt(inputs[3])){
                        System.out.println("The part became expensive.");
                    }

                    buffer.append
                            (count + "," + inputs[0] + "," + part.id + "," + part.price + "," + inputs[4] +"," + Integer.parseInt(inputs[3]) * Integer.parseInt(inputs[4]) + "\n");

                    part.price = Integer.parseInt(inputs[3]);
//                    System.out.println("input : " + part.name + " " + inputs[4] + " " + part.quantity );

                    break;

                case "S":
                    if (part== null || part.quantity < Integer.parseInt(inputs[3])) {
                        exitProgram(buffer);
                    }
                    buffer.append
                            (count + "," + inputs[0] + "," + part.id + "," + part.price + "," + inputs[3] +"," + Integer.parseInt(inputs[3]) * part.price + "\n");
//                    System.out.println("before sell : " + part.quantity + "" );
                    part.quantity -= Integer.parseInt(inputs[3]);
//                    System.out.println("after sell : " + part.quantity + "" );
//                    System.out.println("sell : " + part.name + " " + inputs[3] );
                    break;

                case "E":
                    if (part == null) {
                        exitProgram(buffer);
                    }

                    buffer.append
                            (count + "," + inputs[0] + "," + part.id + "," + part.price + "," + part.quantity +"," + part.quantity * part.price + "\n");

                    if (part.quantity < Integer.parseInt(inputs[3])) {
                        System.out.println("The part partially exchanged.");
                    }
                    break;
            }
        }

        exitProgram(buffer);

    }

    private static void exitProgram(StringBuffer txBuffer) throws FileNotFoundException {
        //1. update input.txt , tx.txt, stock.txt
        //2. exit program
//        System.out.println("exit");
        StringBuffer stockBuffer = new StringBuffer();
        for (ComputerPart cp : computerParts){
            stockBuffer.append(cp.id + "," + cp.type + "," + cp.name  + "," + cp.price + "," + cp.quantity + "\n");
        }
        PrintStream stock = new PrintStream(new File("stock.txt"));
        stock.print(stockBuffer);
        PrintStream tx = new PrintStream(new File("tx.txt"));
        tx.append(txBuffer);
        System.out.println(stockBuffer);
        System.out.println(txBuffer);

        System.exit(0);
    }

    private static ComputerPart addNewPart(ComputerPart[] computerPartsVari, String[] inputs) {
        ComputerPart[] computerParts1 = new ComputerPart[computerPartsVari.length + 1];
        for (int i = 0; i < computerPartsVari.length; i++){
            computerParts1[i] = computerPartsVari[i];
        }
        computerParts1[computerPartsVari.length] = new ComputerPart(computerParts1.length, inputs[1], inputs[2], inputs[3], inputs[4]);
        computerParts = computerParts1;

        return computerParts[computerParts.length - 1];
    }

    private static ComputerPart getCP(ComputerPart[] cps, String[] inputs){

        for (ComputerPart cp : cps) {
            if (cp.name.equals(inputs[2])) {
                return cp;
            }
        }
        return null;
    }



    public static class ComputerPart {
        String  type, name;
        int price, quantity, id;

        public ComputerPart(int id, String type, String name, int price, int quantity) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public ComputerPart(String id, String type, String name, String s, String s1) {
            this.id = Integer.parseInt(id);
            this.type = type;
            this.name = name;
            this.price = Integer.parseInt(s);
            this.quantity = Integer.parseInt(s1);
        }

        public ComputerPart(int length, String input, String input1, String input2, String input3) {
            this.id = length;
            this.type = input;
            this.name = input1;
            this.price = Integer.parseInt(input2);
            this.quantity = Integer.parseInt(input3);

        }
    }
}
