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
 * ToTest: test invalid barcode null
 * ToTest: correct barcode format "\d{12}"
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

    private String getInvalidBarcodeMessage(final String invalidBarCode) {
        return String.format("Invalid barcode '%s'", invalidBarCode);
    }
}
