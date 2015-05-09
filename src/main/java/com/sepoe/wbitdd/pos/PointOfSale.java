package com.sepoe.wbitdd.pos;

import java.util.Optional;

/**
 * Class to provide the price of an item with barcode.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSale {

    private String barcodePattern = "\\d{5,12}";
    private final ItemRepository itemRepository;
    private final OutputDevice outputDevice;

    public PointOfSale(final ItemRepository itemRepository, final OutputDevice outputDevice) {
        if (itemRepository == null) throw new IllegalArgumentException("Missing item repository");
        if (outputDevice == null) throw new IllegalArgumentException("Missing output device");

        this.itemRepository = itemRepository;
        this.outputDevice = outputDevice;
    }

    public void onBarcode(final String barcode) {
        try {
            if (isInvalidBarcode(barcode)) {
                handleInvalidBarcode(barcode);
            } else {
                handleValidBarcode(barcode);
            }
        } catch (Throwable throwable){
            // in case the output itself throws an exception we cannot write the error to the output
            // which would cause and endless loop.
            throwable.printStackTrace();
        }
    }

    private void handleInvalidBarcode(final String barcode) {
        outputDevice.writeItemPrice(String.format("Invalid barcode '%s'", barcode));
    }

    private void handleValidBarcode(final String barcode) {
        String output;
        String output2;

        try {
            final String trimmedBarcode = barcode.trim();
            final Optional<Double> price = itemRepository.lookupPrice(trimmedBarcode);

            output = generateOutput(trimmedBarcode, price);
        } catch (Exception e) {
            output = String.format("ERROR '%s'", e.getMessage());
        }

        outputDevice.writeItemPrice(output);
    }

    private boolean isInvalidBarcode(final String barcode) {
        return barcode == null || barcode.isEmpty() || !barcode.trim().matches(barcodePattern);
    }

    private String generateOutput(final String barcode, final Optional<Double> price) {
        return (price == Optional.<Double>empty()) ? String.format("No item for barcode %s", barcode) : String.format("$%.2f", price.get());
    }
}
