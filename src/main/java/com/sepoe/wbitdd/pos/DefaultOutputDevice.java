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
        write(format("ERROR '%s'", e.getMessage()));
    }

    @Override
    public void displayNotFound(final String barcode) {
        final String notFoundMessage = format("No item for barcode %s", barcode);
        write(notFoundMessage);
    }

    @Override
    public void displayPrice(final Double price) {
        final String formattedPrice = format("$%.2f", price);
        write(formattedPrice);
    }

    @Override
    public void displayInvalidBarcode(final String barcode) {
        final String errorMessage = format("Invalid barcode '%s'", barcode);
        write(errorMessage);
    }

    protected abstract void write(String message);
}
