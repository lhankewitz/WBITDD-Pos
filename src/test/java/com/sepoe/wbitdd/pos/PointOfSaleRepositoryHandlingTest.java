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
 * ToRefactor: output should write string to output errors - next
 * ToRefactor: price to price object
 * ToRefactor: wrap Barcode into a wrapper class (inspired by object calisthenics)
 */
public class PointOfSaleRepositoryHandlingTest {

    private MockItemRepository mockItemRepository = new MockItemRepository();
    private MockOutputDevice mockOutputDevice = new MockOutputDevice();
    private PointOfSale pointOfSale = new PointOfSale(mockItemRepository, mockOutputDevice);

    @Test
    public void onBarcode_withValidBarcode_looksUpRepository() {
        final String barcode1 = generateBarCode();
        pointOfSale.onBarcode(barcode1);
        assertThat(mockItemRepository.getLookupBarcode(), is(barcode1));
    }


    @Test
    public void onBarcode_withExistingItem_passesPriceInformationToOutputDevice() {
        final String barcode1 = generateBarCode();
        final Double price1 = 42.42;
        mockItemRepository.when(barcode1, price1);

        pointOfSale.onBarcode(barcode1);
       assertThat(mockOutputDevice.getOutputToWrite(), is("$" + price1.toString()));
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
