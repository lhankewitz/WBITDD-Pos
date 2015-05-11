package com.sepoe.wbitdd.pos;

/**
 * Interface representing an output device for the PointOfSale.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public interface OutputDevice {

    void writeItemPrice(String itemPrice);

    void displayException(final Exception e);

    void displayNotFound(final String barcode);

    void displayPrice(final Double price);
}
