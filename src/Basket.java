import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String[] products;
    private static int[] prices;

    private static int[] amountOfProducts;
    private static int[] costOfProducts;
    private static boolean isFilled[];
    private static int[] in;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        amountOfProducts = new int[products.length];
        costOfProducts = new int[products.length];
        isFilled = new boolean[products.length];
    }



    public void addToCart(int productNum, int amount){
        amountOfProducts[productNum] += amount;
        costOfProducts[productNum] += (amount * prices[productNum]);
        isFilled[productNum] = true;
    }
    public void printCart(){
        System.out.println("Your cart:");
        for (int i = 0; i < products.length; i++) {
            if (isFilled[i]) {
                System.out.println(products[i] + " x " + amountOfProducts[i] + "pc. - " + costOfProducts[i] + " RUB");
            }
        }
        int total = 0;
        for (int prodCost:
             costOfProducts) {
            total += prodCost;
        }
        System.out.println("Total: " + total + " RUB");
    }

    public void saveTxt(File textFile) throws IOException {
        try (FileWriter writer = new FileWriter(textFile)) {
            for (int i : amountOfProducts) {
                writer.write(i + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
     static Basket loadFromTextFile(File file) {
        Basket basket = new Basket(products, prices);
        try (FileReader reader = new FileReader("cart.txt")){
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()){
                String[] input = scanner.nextLine().split(" ");
                in = Arrays.stream(input)
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (int i = 0; i < in.length; i++) {
            basket.addToCart(i,in[i]);
        }

        return basket;
    }

    public void saveBin(File file, Basket basket) {
        try (FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(basket);
            }  catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis)){
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }

}
