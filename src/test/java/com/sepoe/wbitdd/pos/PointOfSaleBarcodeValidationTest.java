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
    private MockItemRepository itemRepository = new MockItemRepository();
    private PointOfSale pointOfSale = new PointOfSale(itemRepository, mockOutputDevice);


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
        final String tooShortBarcode = "12345678901";
        pointOfSale.onBarcode(tooShortBarcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(getInvalidBarcodeMessage(tooShortBarcode)));
    }


    private String getInvalidBarcodeMessage(final String invalidBarCode) {
        return String.format("Invalid barcode '%s'", invalidBarCode);
    }
}
