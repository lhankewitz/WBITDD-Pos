package com.sepoe.wbitdd.pos;

import com.sepoe.wbitdd.pos.util.BarcodeGenerator;
import com.sepoe.wbitdd.pos.util.MockItemRepository;
import com.sepoe.wbitdd.pos.util.MockOutputDevice;
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

    private BarcodeGenerator barcodeGenerator = new BarcodeGenerator();

    @Test
    public void onTotal_forNoItems_returnsMessage() {
        pos.onTotal();
        assertThat(display.getOutputToWrite(), is("No sale in progress. Try scanning a product"));
    }

    @Test
    public void onTotal_whenSeveralItemsAreNotFound_returnsScanMessage() {
        pos.onBarcode(barcodeGenerator.generateBarcode());
        pos.onBarcode(barcodeGenerator.generateBarcode());
        pos.onBarcode(barcodeGenerator.generateBarcode());
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


    @Test
    public void beforeTotal_forSeveralItems_showSinglePriceForEachBeforeTotal() {
        final String[] barcodes = barcodeGenerator.generateBarcodes();

        mockItemRepository.register(barcodes, 8.50, 12.75, 3.30);

        pos.onBarcode(barcodes[0]);
        assertThat(display.getOutputToWrite(), is("$8.50"));
        pos.onBarcode(barcodes[1]);
        assertThat(display.getOutputToWrite(), is("$12.75"));
        pos.onBarcode(barcodes[2]);
        assertThat(display.getOutputToWrite(), is("$3.30"));


        pos.onTotal();

        assertThat(display.getOutputToWrite(), is("Total $24.55"));
    }

    @Test
    public void onTotal_forThreeFoundItems_resultsInTotal() {
        final String[] barcodes = barcodeGenerator.generateBarcodes();

        mockItemRepository.register(barcodes, 8.50, 12.75, 3.30);

        scan(barcodes);

        pos.onTotal();

        assertThat(display.getOutputToWrite(), is("Total $24.55"));
    }

    private void scan(final String[] barcodes) {
        for (String barcode : barcodes) {
            pos.onBarcode(barcode);
        }
    }

    @Test
    public void onTotal_forFoundAndNotFoundItems_resultsInTotal() {
        final String[] barcodes = barcodeGenerator.generateBarcodes();

        mockItemRepository.register(barcodes, 8.50, 12.50, 3.00);

        scan(barcodes);

        pos.onBarcode(barcodeGenerator.generateBarcode());

        pos.onTotal();

        assertThat(display.getOutputToWrite(), is("Total $24.00"));
    }
}
