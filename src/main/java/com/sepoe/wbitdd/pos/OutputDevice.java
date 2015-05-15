package com.sepoe.wbitdd.pos;

/**
 * Interface representing an output device for the PointOfSale.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public interface OutputDevice {

    void displayException(Exception e);

    void displayNotFound(String barcode);

    void displayPrice(Double price);

    void displayInvalidBarcode(String barcode);

    void displayNoSaleInProgress();

    void displayTotal(Double price);
}
