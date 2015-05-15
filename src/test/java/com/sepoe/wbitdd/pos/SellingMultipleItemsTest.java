package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class to ...
 *
 * @author lumiha
 * @since 15/05/15.
 */
public class SellingMultipleItemsTest {
    private static final int BARCODE_IDX = 0;
    private static final int PRICE_IDX = 1;
    private MockOutputDevice display = new MockOutputDevice();
    private final MockItemRepository mockItemRepository = new MockItemRepository();
    private PointOfSale pos = new PointOfSale(mockItemRepository, display);

    private BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    @Test
    public void onTotal_forNoItmems_returnsMessage() {
        pos.onTotal();
        assertThat(display.getOutputToWrite(), is("No sale in progress. Try scanning a product"));
    }

    @Test
    public void onTotal_withOneItemFound_resultsInPriceOfItem() {
        final String barcode = "123456";
        mockItemRepository.when(barcode, 8.50);
        pos.onBarcode(barcode);
        pos.onTotal();
        assertThat(display.getOutputToWrite(), is("Total $8.50"));
    }

    @Test
    public void onTotal_forThreeFoundItems_resultsInTotal() {
        final String[] barcodes = generateBarcodes();

        register(barcodes);

        scan(barcodes);

        pos.onTotal();

        assertThat(display.getOutputToWrite(), is("Total $24.55"));
    }

    private String[] generateBarcodes() {
        return new String[]{
                    barcodeGenerator.generateBarCode()
                    ,barcodeGenerator.generateBarCode()
                    ,barcodeGenerator.generateBarCode()
            };
    }

    private void register(final String[] barcodes) {
        mockItemRepository.when(barcodes[0], 8.50);
        mockItemRepository.when(barcodes[1], 12.75);
        mockItemRepository.when(barcodes[2], 3.30);
    }

    private void scan(final String[] barcodes) {
        for (String barcode : barcodes) {
            pos.onBarcode(barcode);
        }
    }
}
