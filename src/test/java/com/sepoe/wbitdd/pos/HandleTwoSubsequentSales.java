package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class to test to subsequent tests.
 * @author lumiha
 * @since 15/05/15.
 */
public class HandleTwoSubsequentSales {

    private final BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
    private MockOutputDevice display = new MockOutputDevice();
    private MockItemRepository mockItemRepository = new MockItemRepository();
    private PointOfSale pos = new PointOfSale(mockItemRepository, display);

    // ToTest: subsequent sales
    @Test
    //@Ignore("extract test data generator first")
    public void onTotal_forTwoSubsequentSales_calculatesTwoTotals() {

        final String[] barcodes = barcodeGenerator.generateBarcodes();
        mockItemRepository.register(barcodes, 3.25, 7.00, 2.25);

        for (String barcode : barcodes) {
            pos.onBarcode(barcode);
        }

        pos.onTotal();
        assertThat(display.getOutputToWrite(), is("Total $12.50"));


        pos.onBarcode(barcodes[0]);
        pos.onBarcode(barcodes[1]);

        pos.onTotal();
        assertThat(display.getOutputToWrite(), is("Total $10.25"));
    }
}
