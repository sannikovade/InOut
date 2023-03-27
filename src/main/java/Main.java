import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

        Basket basket = new Basket(products, prices);
        ClientLog clientLog = new ClientLog();
        File fileCSV = new File("./log.csv");
        File fileJSON = new File("./basket.json");


        if (fileJSON.exists()) {
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(fileJSON));
                JSONObject jsonObject = (JSONObject) obj;
                String jsonText = String.valueOf(jsonObject);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                basket = gson.fromJson(jsonText, Basket.class);
            } catch (IOException | ParseException e) {
                e.getMessage();
            }
        }

        while (true) {

            System.out.println("Select item and quantity or enter 'end'");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equals("end")) {
                clientLog.exportAsCSV(fileCSV);
                break;
            }
            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            clientLog.log(productNumber + 1, productCount);

            try (FileWriter fileWriter = new FileWriter(fileJSON)) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                fileWriter.write(gson.toJson(basket));
                fileWriter.flush();
            }

        }
        basket.printCart();

    }
}