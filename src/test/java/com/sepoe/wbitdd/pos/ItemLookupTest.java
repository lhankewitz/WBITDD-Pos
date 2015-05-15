package com.sepoe.wbitdd.pos;

import com.sepoe.wbitdd.pos.util.BarcodeGenerator;
import com.sepoe.wbitdd.pos.util.MockItemRepository;
import com.sepoe.wbitdd.pos.util.MockOutputDevice;
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
public class ItemLookupTest {

    private final BarcodeGenerator barcodeGenerator = new BarcodeGenerator();
    private MockItemRepository mockItemRepository = new MockItemRepository();
    private MockOutputDevice mockOutputDevice = new MockOutputDevice();
    private PointOfSale pos = new PointOfSale(mockItemRepository, mockOutputDevice);

    @Test
    public void onBarcode_withValidBarcode_looksUpRepository() {
        final String barcode = barcodeGenerator.generateBarcode();
        pos.onBarcode(barcode);
        assertThat(mockItemRepository.getLookupBarcode(), is(barcode));
    }


    @Test
    public void onBarcode_withExistingItem_passesPriceInformationToOutputDevice() {
        final String barcode = barcodeGenerator.generateBarcode();
        final double price = 42.42;
        mockItemRepository.when(barcode, price);

        pos.onBarcode(barcode);
       assertThat(mockOutputDevice.getOutputToWrite(), is("$" + Double.valueOf(price).toString() ));
    }

    @Test
    public void onBarcode_withMoreThan3DigitFractionItemPrice_passes2DigitPriceInformationToOutputDevice() {
        final String barcode = barcodeGenerator.generateBarcode();
        mockItemRepository.when(barcode, 42.424);

        pos.onBarcode(barcode);
       assertThat(mockOutputDevice.getOutputToWrite(), is("$42.42"));
    }

    @Test
    public void onBarcode_forUnknownBarcode_outputsErrorMessage() {
        final String barcode = barcodeGenerator.generateBarcode();
        pos.onBarcode(barcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is(String.format("No item for barcode %s", barcode)));
    }

    @Test
    public void onBarCode_throwingAnException_outputsAnErrorMessage() {
        final String barcode = barcodeGenerator.generateBarcode();
        mockItemRepository.throwExceptionInLookup();
        pos.onBarcode(barcode);
        assertThat(mockOutputDevice.getOutputToWrite(), is("ERROR 'Some error occurred'"));
    }


}
