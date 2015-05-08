package com.sepoe.wbitdd.pos;

/**
 * Class to provide the price of an item with barcode.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSale {

    public PointOfSale(final Object outputDevice, final Object itemRepository) {
        if (itemRepository == null) throw new IllegalArgumentException("Missing item repository");
       throw new IllegalArgumentException("Missing output device");
    }
}
