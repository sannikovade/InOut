import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Basket {
    protected String[] products;
    protected int[] prices;

    protected int[] amountOfProducts;
    protected int[] costOfProducts;
    protected boolean isFilled[];

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        amountOfProducts = new int[products.length];
        costOfProducts = new int[products.length];
        isFilled = new boolean[products.length];
    }

    static Basket loadFromTextFile(File file) {
        try (FileReader reader = new FileReader("cart.txt")) {
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String[] inputProducts = scanner.nextLine().split(" ");

                String[] inPrices = scanner.nextLine().split(" ");
                int[] inputPrices = Arrays.stream(inPrices)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                String[] inAmount = scanner.nextLine().split(" ");
                int[] inputAmount = Arrays.stream(inAmount)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                Basket basket = new Basket(inputProducts, inputPrices);
                for (int i = 0; i < inputAmount.length; i++) {
                    basket.addToCart(i, inputAmount[i]);
                }
                return basket;
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void addToCart(int productNum, int amount) {
        amountOfProducts[productNum] += amount;
        costOfProducts[productNum] += (amount * prices[productNum]);
        isFilled[productNum] = true;
    }

    public void printCart() {
        System.out.println("Your cart:");
        for (int i = 0; i < products.length; i++) {
            if (isFilled[i]) {
                System.out.println(products[i] + " x " + amountOfProducts[i] + "pc. - " + costOfProducts[i] + " RUB");
            }
        }
        int total = 0;
        for (int prodCost :
                costOfProducts) {
            total += prodCost;
        }
        System.out.println("Total: " + total + " RUB");
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (String product : products) {
                writer.print(product + " ");
            }
            writer.println();
            for (int price : prices) {
                writer.print(price + " ");
            }
            writer.println();
            for (int i : amountOfProducts) {
                writer.write(i + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
