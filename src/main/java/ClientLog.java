import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ClientLog {
    private ArrayList<String[]> log = new ArrayList<>();


    public void log(int productNum, int amount) {
        String[] clientInp = (productNum + "," + amount).split(",");
        log.add(clientInp);
    }

    public void exportAsCSV(File textFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(textFile))) {
            writer.writeNext(new String[]{"productNum" + ", " + "amount"});
            for (String[] i : log) {
                writer.writeNext(i);
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
