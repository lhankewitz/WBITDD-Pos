package com.sepoe.wbitdd.pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Class to handle input from command line and output to commandline.
 * It is just meant as POC.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSaleApp {

    public PointOfSaleApp() {
        super();
    }

    public static void main(String[] args) {

        System.out.println("Started PointOfSaleApp");
        final PointOfSaleApp app = new PointOfSaleApp();

        final PointOfSale pointOfSale = new PointOfSale(app.itemRepository, app.outputDevice);

        handleInput(pointOfSale::onBarcode);
    }

    private static void handleInput(final Consumer<String> inputHandler) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            String barcode;
            System.out.print("Enter 'quit' or barcode: ");
            barcode = bufferedReader.readLine();

            while (!barcode.equals("quit")) {
                inputHandler.accept(barcode);

                System.out.print("Enter 'quit' or barcode: ");

                barcode = bufferedReader.readLine();
            }

            System.out.println("Ended PointOfSaleApp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // the real application woudl provide the price data here.
    final ItemRepository itemRepository = new ItemRepository() {
        private HashMap<String, Double> prices = new HashMap<>();

        {
            prices.put("12345", 42.2);
            prices.put("123456", 142.2);
            prices.put("1234567", 2142.2);
        }

        @Override
        public Double lookupItem(final String barcode) {
            return prices.get(barcode);
        }
    };

    // the real application would route this to the display here.
    final OutputDevice outputDevice = System.out::println;
}
