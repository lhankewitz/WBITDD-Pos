package com.sepoe.wbitdd.pos;

import java.util.Optional;

import static java.lang.String.format;

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

    private boolean isInvalidBarcode(final String barcode) {
        return barcode == null || barcode.isEmpty() || !barcode.trim().matches(barcodePattern);
    }

    private void handleInvalidBarcode(final String barcode) {
        final String errorMessage = format("Invalid barcode '%s'", barcode);
        outputDevice.writeItemPrice(errorMessage);
    }

    private void handleValidBarcode(final String barcode) {
        try {
            final String normalizedBarcode = getNormalizedBarCode(barcode);

            final Optional<Double> priceInformation = findPrice(normalizedBarcode);

            if(priceInformation.isPresent()){
                formatAndDisplayPrice(priceInformation.get());
            } else {
                generateAndDisplayNotFoundMessage(barcode);
            }
        } catch (Exception e) {
            displayException(e);
        }
    }


    private String getNormalizedBarCode(final String barcode) {
        return barcode.trim();
    }

    private Optional<Double> findPrice(final String trimmedBarcode) {
        return itemRepository.lookupPrice(trimmedBarcode);
    }

    public void formatAndDisplayPrice(final Double price) {
        final String formattedPrice = format("$%.2f", price);
        outputDevice.writeItemPrice(formattedPrice);
    }

    private void generateAndDisplayNotFoundMessage(final String barcode) {
        final String notFoundMessage = format("No item for barcode %s", barcode);
        outputDevice.writeItemPrice(notFoundMessage);
    }

    private void displayException(final Exception e) {
        outputDevice.writeItemPrice(format("ERROR '%s'", e.getMessage()));
    }

}
