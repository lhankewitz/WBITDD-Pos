package com.sepoe.wbitdd.pos;

/**
 * Class to provide the price of an item with barcode.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSale {


    private final ItemRepository itemRepository;
    private final OutputDevice outputDevice;

    public PointOfSale(final ItemRepository itemRepository, final OutputDevice outputDevice) {
        if (itemRepository == null) throw new IllegalArgumentException("Missing item repository");
        if (outputDevice == null) throw new IllegalArgumentException("Missing output device");
        this.itemRepository = itemRepository;
        this.outputDevice = outputDevice;
    }

    public void onBarcode(final String barcode) {
        final Double price = itemRepository.lookupItem(barcode);
        outputDevice.writePriceInformation(price);
    }
}
