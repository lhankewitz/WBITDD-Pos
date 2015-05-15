package com.sepoe.wbitdd.pos;

import java.util.Optional;

/**
 * Class to provide the total of an item with barcode.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSale {

    private static final String barcodePattern = "\\d{5,12}";
    private static final double ZERO_TOTAL = 0.00;

    private final ItemRepository itemRepository;
    private final OutputDevice outputDevice;

    private double total = ZERO_TOTAL;

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
                final Double price = priceInformation.get();
                addPrice(price);
                outputDevice.displayPrice(price);
            } else {
                outputDevice.displayNotFound(barcode);
            }
        } catch (Exception e) {
            outputDevice.displayException(e);
        }
    }

    private void addPrice(final Double price) {
        total += price;
    }


    private String getNormalizedBarCode(final String barcode) {
        return barcode.trim();
    }

    public void onTotal() {
        if (isSaleInProgress()) {
            outputDevice.displayTotal(total);
        } else {
            outputDevice.displayNoSaleInProgress();
        }

        resetTotal();
    }

    private boolean isSaleInProgress() {
        return ZERO_TOTAL != total;
    }

    private void resetTotal() {
        total = ZERO_TOTAL;
    }
}
