package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the respository handling.
 *
 * @author lumiha
 * @since 08/05/15.
 *
 * ToTest: lookup of barcode
 * ToTest: output of price for barcode
 * ToTest: output invalid barcode
 * ToTest: output of not found item for barcode
 */
public class PointOfSaleRepositoryHandlingTest {
    @Test
    public void onBarcode_withValidBarcode_looksUpRepository() {
        final MockItemRepository itemRepository = new MockItemRepository();
        final String barcode = "123456789012";
        new PointOfSale(itemRepository, new MockOutputDevice()).onBarcode(barcode);
        assertThat(itemRepository.getLookupBarcode(), is(barcode));
    }
}
