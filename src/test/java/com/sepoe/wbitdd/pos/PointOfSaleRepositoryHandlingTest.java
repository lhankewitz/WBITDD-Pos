package com.sepoe.wbitdd.pos;

import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the respository handling.
 *
 * @author lumiha
 * @since 08/05/15.
 * <p>
 * ToTest: output invalid barcode
 * ToTest: output of not found item for barcode
 * ToRefactor: output should write string to output errors
 * ToRefactor: price to price object
 * ToRefactor: wrap Barcode into a wrapper class (inspired by object calisthenics)
 */
public class PointOfSaleRepositoryHandlingTest {

    private MockItemRepository itemRepository = new MockItemRepository();
    private MockOutputDevice outputDevice = new MockOutputDevice();
    private PointOfSale pointOfSale = new PointOfSale(itemRepository, outputDevice);

    @Test
    public void onBarcode_withValidBarcode_looksUpRepository() {
        final String barcode1 = generateBarCode();
        pointOfSale.onBarcode(barcode1);
        assertThat(itemRepository.getLookupBarcode(), is(barcode1));
    }

    @Test
    public void onBarcode_withExistingItem_passesPriceToOutputDevice() {
        final String barcode1 = generateBarCode();
        final double price1 = 42.42;
        itemRepository.when(barcode1, price1);

        pointOfSale.onBarcode(barcode1);
        assertThat(outputDevice.getWrittenPrice(), is(price1));
    }

    private String generateBarCode() {
        char[] digitArray = new char[12];

        for (int i = 0; i < 12; i++) {
            final int digit = new Random().nextInt(10);
            digitArray[i] = Integer.toString(digit).charAt(0);
        }

        return new String(digitArray);
    }

}
