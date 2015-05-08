package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the respository handling.
 *
 * @author lumiha
 * @since 08/05/15.
 * <p>
 * ToTest: lookup of barcode
 * ToTest: output of price for barcode
 * ToTest: output invalid barcode
 * ToTest: output of not found item for barcode
 */
public class PointOfSaleRepositoryHandlingTest {

    private MockItemRepository itemRepository = new MockItemRepository();
    private MockOutputDevice outputDevice = new MockOutputDevice();
    private PointOfSale pointOfSale = new PointOfSale(itemRepository, outputDevice);

    @Test
    public void onBarcode_withValidBarcode_looksUpRepository() {
        final String barcode1 = "123456789012";
        pointOfSale.onBarcode(barcode1);
        assertThat(itemRepository.getLookupBarcode(), is(barcode1));

        final String barcode2 = "234567890123";
        pointOfSale.onBarcode(barcode2);
        assertThat(itemRepository.getLookupBarcode(), is(barcode2));
    }
}
