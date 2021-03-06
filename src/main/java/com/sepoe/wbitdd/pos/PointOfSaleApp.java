package com.sepoe.wbitdd.pos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Class to handle input from command line and output to commandline.
 * It is just meant as POC.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSaleApp {


    public static void main(String[] args) {

        System.out.println("Started PointOfSaleApp");
        final PointOfSaleApp app = new PointOfSaleApp();

        final PointOfSale pointOfSale = new PointOfSale(app.itemRepository, app.outputDevice);

        queryForInput(pointOfSale::onBarcode, pointOfSale::onTotal);
    }

    // the real application would provide the price data here.
    final ItemRepository itemRepository = new ItemRepository() {
        private HashMap<String, Double> prices = new HashMap<>();

        {
            prices.put("12345", 42.2);
            prices.put("123456", 142.2);
            prices.put("1234567", 2142.2);
        }

        @Override
        public Optional<Double> lookupPrice(final String barcode) {
            return Optional.ofNullable(prices.get(barcode));
        }
    };


    // the real application would route this to the display here.
    final OutputDevice outputDevice = new DefaultOutputDevice() {

        protected void write(final String message) {
            System.out.println(message);
        }
    };

    private static void queryForInput(final Consumer<String> inputHandler, Callback totalHandler) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            String barcode;
            System.out.print("Enter 'quit' or barcode: ");
            barcode = bufferedReader.readLine();

            while (!barcode.equals("quit")) {
                if (!barcode.equals("total")) {
                    inputHandler.accept(barcode);
                } else {
                    totalHandler.call();
                }

                System.out.print("Enter 'quit' or barcode: ");

                barcode = bufferedReader.readLine();
            }

            System.out.println("Ended PointOfSaleApp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface Callback{
        void call();
    }
}
