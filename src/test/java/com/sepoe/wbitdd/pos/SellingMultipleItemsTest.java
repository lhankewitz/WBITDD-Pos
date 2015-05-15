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
    private MockOutputDevice display = new MockOutputDevice();
    private final MockItemRepository mockItemRepository = new MockItemRepository();
    private PointOfSale pos = new PointOfSale(mockItemRepository, display);

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
}
