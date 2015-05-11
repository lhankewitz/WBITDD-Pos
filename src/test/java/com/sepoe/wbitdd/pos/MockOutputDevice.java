package com.sepoe.wbitdd.pos;

import static java.lang.String.format;

/**
 * Class to ...
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockOutputDevice implements OutputDevice {

    private String itemPrice;

    @Override
    public void writeItemPrice(final String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getOutputToWrite() {
        return itemPrice;
    }

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
}
