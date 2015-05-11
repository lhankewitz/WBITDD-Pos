package com.sepoe.wbitdd.pos;

import static java.lang.String.format;

/**
 * Class to handle default display.
 *
 * @author lumiha
 * @since 11/05/15.
 */
public abstract class DefaultOutputDevice implements OutputDevice {


    @Override
    public void displayException(final Exception e) {
        writeItemPrice(format("ERROR '%s'", e.getMessage()));
    }

    @Override
    public void displayNotFound(final String barcode) {
        final String notFoundMessage = format("No item for barcode %s", barcode);
        writeItemPrice(notFoundMessage);
    }

    @Override
    public void displayPrice(final Double price) {
        final String formattedPrice = format("$%.2f", price);
        writeItemPrice(formattedPrice);
    }

    @Override
    public void displayInvalidBarcode(final String barcode) {
        final String errorMessage = format("Invalid barcode '%s'", barcode);
        writeItemPrice(errorMessage);
    }

    protected abstract void writeItemPrice(String itemPrice);
}
