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
        try {
            if (isInvalidBarcode(barcode)) {
                outputDevice.writeItemPrice(String.format("Invalid barcode '%s'", barcode));
            } else {
                String output;

                try {
                    final String trimmedBarcode = barcode.trim();
                    final Double price = itemRepository.lookupItem(trimmedBarcode);

                    output = generateOutput(trimmedBarcode, price);
                } catch (Exception e) {
                    output = String.format("ERROR '%s'", e.getMessage());
                }

                outputDevice.writeItemPrice(output);
            }
        } catch (Throwable throwable){
            // in case the output itself throws an exception we cannot write the error to the output
            // which would cause and endless loop.
            throwable.printStackTrace();
        }
    }

    private boolean isInvalidBarcode(final String barcode) {
        return barcode == null || barcode.isEmpty() || !barcode.trim().matches(barcodePattern);
    }

    private String generateOutput(final String barcode, final Double price) {
        return (price == null) ? String.format("No item for barcode %s", barcode) : String.format("$%.2f", price);
    }
}
