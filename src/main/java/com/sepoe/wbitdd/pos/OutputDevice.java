package com.sepoe.wbitdd.pos;

/**
 * Interface representing an output device for the PointOfSale.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public interface OutputDevice {
    void writePriceInformation(Double price);

    void writeItemPrice(String itemPrice);
}
