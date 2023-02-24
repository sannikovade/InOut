import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] products = {"Milk", "Butter", "Oatmeal"};
        int[] prices = {60, 100, 50};
        System.out.println("Products available for purchase:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + " " + products[i] + " " + prices[i] + " RUB/1pc");
        }

        Basket basket = new Basket(products,prices);
        File file = new File("./basket.bin");

        if(file.exists()){
            basket = Basket.loadFromBinFile(file);
        }

        while (true) {

            System.out.println("Select item and quantity or enter 'end'");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            basket.saveBin(file, basket);

        }
        basket.printCart();
    }
}