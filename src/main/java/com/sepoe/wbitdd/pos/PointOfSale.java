package com.sepoe.wbitdd.pos;

/**
 * Class to provide the price of an item with barcode.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class PointOfSale {

    private String barcodePattern = "\\d{12}";
    private final ItemRepository itemRepository;
    private final OutputDevice outputDevice;

    public PointOfSale(final ItemRepository itemRepository, final OutputDevice outputDevice) {
        if (itemRepository == null) throw new IllegalArgumentException("Missing item repository");
        if (outputDevice == null) throw new IllegalArgumentException("Missing output device");
        this.itemRepository = itemRepository;
        this.outputDevice = outputDevice;
    }

    public void onBarcode(final String barcode) {
        if(isInvalidBarcode(barcode)){
            outputDevice.writeItemPrice(String.format("Invalid barcode '%s'", barcode));
        } else {
            final Double price = itemRepository.lookupItem(barcode);

            final String itemPrice = generateOutput(barcode, price);
            outputDevice.writeItemPrice(itemPrice);
        }
    }

    private boolean isInvalidBarcode(final String barcode) {
        return barcode == null || barcode.isEmpty()|| !barcode.matches(barcodePattern);
    }

    private String generateOutput(final String barcode, final Double price) {
        return (price == null) ? String.format("No item for barcode %s", barcode) : String.format("$%.2f",price);
    }
}
