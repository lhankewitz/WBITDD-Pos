package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Class to test the barcode validation.
 *
 * @author lumiha
 * @since 08/05/15.
 *
 */
public class PointOfSaleBarcodeValidationTest {

    private MockOutputDevice mockOutputDevice = new MockOutputDevice();
    private MockItemRepository mockItemRepository = new MockItemRepository();
    private PointOfSale pointOfSale = new PointOfSale(mockItemRepository, mockOutputDevice);


    @Test
    public void onBarcode_forInvalidBarCode_xc_passesErrorMessageToOutput() {
        final String invalidBarCode = "xc";
        pointOfSale.onBarcode(invalidBarCode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(getInvalidBarcodeMessage(invalidBarCode)));
    }

    @Test
    public void onBarcode_forEmptyBarCode_passesErrorMessageToOutput() {
        final String invalidBarCode = "";
        pointOfSale.onBarcode(invalidBarCode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(getInvalidBarcodeMessage(invalidBarCode)));
    }


    @Test
    public void onBarcode_forNullBarCode_passesErrorMessageToOutput() {
        pointOfSale.onBarcode(null);
        assertThat(mockOutputDevice.getOutputToWrite(), is(getInvalidBarcodeMessage(null)));
    }

    @Test
    public void onBarcode_forTooShortBarCode_passesErrorMessageToOutput() {
        final String tooShortBarcode = "1234";
        pointOfSale.onBarcode(tooShortBarcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(getInvalidBarcodeMessage(tooShortBarcode)));
    }
    
    @Test
    public void onBarcode_forTooLongBarCode_passesErrorMessageToOutput() {
        final String tooLongBarcode = "1234567890123";
        pointOfSale.onBarcode(tooLongBarcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(getInvalidBarcodeMessage(tooLongBarcode)));
    }

    @Test
    public void onBarcode_forNewlineEndingBarCode_passesGeneratesCorrectToOutput() {
        final String barcode = "123456789012";
        mockItemRepository.when(barcode, 13.13);
        pointOfSale.onBarcode(barcode + "\n");
        assertThat(mockOutputDevice.getOutputToWrite(), is("$13.13"));
    }

    @Test
    public void onBarcode_forTabulatorEndingBarCode_passesGeneratesCorrectToOutput() {
        final String barcode = "123456789012";
        mockItemRepository.when(barcode, 13.13);

        pointOfSale.onBarcode(barcode + "\t");
        assertThat(mockOutputDevice.getOutputToWrite(), is("$13.13"));
    }

    @Test
    public void onBarcode_forCarriageReturnEndingBarCode_passesGeneratesCorrectToOutput() {
        final String barcode = "123456789012";
        mockItemRepository.when(barcode, 13.13);

        pointOfSale.onBarcode(barcode + "\r");
        assertThat(mockOutputDevice.getOutputToWrite(), is("$13.13"));
    }




    private String getInvalidBarcodeMessage(final String invalidBarCode) {
        return String.format("Invalid barcode '%s'", invalidBarCode);
    }
}
