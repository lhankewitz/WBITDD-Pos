package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the repository handling.
 *
 * @author lumiha
 * @since 08/05/15.
 * <p>
 * ToRefactor: price to price object (optional)
 * ToRefactor: wrap Barcode into a wrapper class (inspired by object calisthenics) (optional)
 */
public class PointOfSaleRepositoryHandlingTest {

    private final BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
    private MockItemRepository mockItemRepository = new MockItemRepository();
    private MockOutputDevice mockOutputDevice = new MockOutputDevice();
    private PointOfSale pointOfSale = new PointOfSale(mockItemRepository, mockOutputDevice);

    @Test
    public void onBarcode_withValidBarcode_looksUpRepository() {
        final String barcode1 = barcodeGenerator.generateBarCode();
        pointOfSale.onBarcode(barcode1);
        assertThat(mockItemRepository.getLookupBarcode(), is(barcode1));
    }


    @Test
    public void onBarcode_withExistingItem_passesPriceInformationToOutputDevice() {
        final String barcode1 = barcodeGenerator.generateBarCode();
        final Double price1 = 42.42;
        mockItemRepository.when(barcode1, price1);

        pointOfSale.onBarcode(barcode1);
       assertThat(mockOutputDevice.getOutputToWrite(), is("$" + price1.toString()));
    }

    @Test
    public void onBarcode_withMoreThan3DigitFractionItemPrice_passes2DigitPriceInformationToOutputDevice() {
        final String barcode1 = barcodeGenerator.generateBarCode();
        final Double price1 = 42.424;
        mockItemRepository.when(barcode1, price1);

        pointOfSale.onBarcode(barcode1);
       assertThat(mockOutputDevice.getOutputToWrite(), is("$42.42"));
    }

    @Test
    public void onBarcode_forUnknownBarcode_outputsErrorMessage() {
        final String barcode = barcodeGenerator.generateBarCode();
        pointOfSale.onBarcode(barcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(String.format("No item for barcode %s", barcode)));
    }

    @Test
    public void onBarCode_throwingAnException_outputsAnErrorMessage() {
        final String barcode = barcodeGenerator.generateBarCode();
        mockItemRepository.throwExceptionInLookup();
        pointOfSale.onBarcode(barcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is("ERROR 'Some error occurred'"));
    }


}
