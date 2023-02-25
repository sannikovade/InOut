import java.io.*;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private int[] amountOfProducts;
    private int[] costOfProducts;
    private boolean isFilled[];

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        amountOfProducts = new int[products.length];
        costOfProducts = new int[products.length];
        isFilled = new boolean[products.length];
    }

    static Basket loadFromBinFile(File file) {
        Basket basket = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (Basket) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
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

    public void saveBin(File file, Basket basket) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(basket);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
