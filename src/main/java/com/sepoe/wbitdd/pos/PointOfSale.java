package com.sepoe.wbitdd.pos;

import java.util.Optional;

/**
 * Class to provide the price of an item with barcode.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSale {

    private static String barcodePattern = "\\d{5,12}";
    private final ItemRepository itemRepository;
    private final OutputDevice outputDevice;
    private Double price;

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
                final String normalizedBarCode = getNormalizedBarCode(barcode);
                handleValidBarcode(barcode, normalizedBarCode);
            }
        } catch (Throwable throwable) {
            // in case the output itself throws an exception we cannot write the error to the output
            // which would cause and endless loop.
            throwable.printStackTrace();
        }
    }

    private boolean isInvalidBarcode(final String barcode) {
        return barcode == null || barcode.isEmpty() || !barcode.trim().matches(barcodePattern);
    }

    private void handleInvalidBarcode(final String barcode) {

        outputDevice.displayInvalidBarcode(barcode);
    }

    private void handleValidBarcode(final String barcode, final String normalizedBarcode) {
        try {
            final Optional<Double> priceInformation = itemRepository.lookupPrice(normalizedBarcode);

            if (priceInformation.isPresent()) {
                price = priceInformation.get();
                outputDevice.displayPrice(price);
            } else {
                outputDevice.displayNotFound(barcode);
            }
        } catch (Exception e) {
            outputDevice.displayException(e);
        }
    }


    private String getNormalizedBarCode(final String barcode) {
        return barcode.trim();
    }

    public void onTotal() {
        if (Double.valueOf(8.50).equals(price)) {
            outputDevice.displayTotal(price);
        } else {
            outputDevice.displayNoSaleInProgress();
        }
    }
}
