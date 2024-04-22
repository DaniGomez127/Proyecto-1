package consola;

import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.List;
import java.util.ArrayList;
import logica.cajero;
import logica.pago;

public class pruebas {
    private static List<pago> payments = new ArrayList<>();

    public static void main(String[] args) {
        String fileName = "D:\\dpoo\\proyectogaleria1\\src\\consola\\testf.csv";
        loadPayments(fileName);
        displayPayments();
    }

    private static void loadPayments(String filePath) {
        try (Reader in = new FileReader(filePath);
             CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withHeader().withFirstRecordAsHeader().withTrim())) {
            for (CSVRecord record : parser) {
                String description = record.get("description");
                String cashierName = record.get("cajero_nombre");
                String cashierContact = record.get("cajero_contacto");

                String paymentIdStr = record.get("pago_id").replaceAll("[^\\d]", ""); 
                int paymentId = Integer.parseInt(paymentIdStr);
                double paymentAmount = Double.parseDouble(record.get("pago_monto"));
                String paymentMethod = record.get("pago_metodo");
                String paymentDetails = record.get("pago_detalles");
                boolean paymentConfirmed = Boolean.parseBoolean(record.get("pago_confirmado"));

                cajero cashier = new cajero(cashierName, cashierContact);
                pago newPayment = new pago(String.valueOf(paymentId), paymentAmount, paymentMethod, paymentDetails, paymentConfirmed);
                payments.add(newPayment);
                cashier.confirmarPago(newPayment); 
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + filePath);
            e.printStackTrace();
        }
    }

    private static void displayPayments() {
        System.out.println("Payment Records:");
        for (pago payment : payments) {
            System.out.println(payment.getPago()); 
        }
        System.out.println("---");
    }
}
