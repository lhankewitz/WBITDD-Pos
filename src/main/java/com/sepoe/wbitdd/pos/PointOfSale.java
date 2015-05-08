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
        if(barcode == null || barcode.equals("xc") || barcode.isEmpty()){
            outputDevice.writeItemPrice(String.format("Invalid barcode '%s'", barcode));
        } else if(!barcode.matches(barcodePattern)){
            outputDevice.writeItemPrice(String.format("Invalid barcode '%s'", barcode));
        } else {
            final Double price = itemRepository.lookupItem(barcode);

            final String itemPrice = generateOutput(barcode, price);
            outputDevice.writeItemPrice(itemPrice);
        }
    }

    private String generateOutput(final String barcode, final Double price) {
        return (price == null) ? String.format("No item for barcode %s", barcode) : "$" + price.toString();
    }
}
