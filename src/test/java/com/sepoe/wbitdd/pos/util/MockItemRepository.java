package com.sepoe.wbitdd.pos.util;

import com.sepoe.wbitdd.pos.ItemRepository;

import java.util.HashMap;
import java.util.Optional;

/**
 * Class to Mock an Item repository.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockItemRepository implements ItemRepository {

    private String barcode;
    private HashMap<String, Double> itemStore = new HashMap<>();
    private boolean doThrowException;

    public String getLookupBarcode() {
        return barcode;
    }


    @Override
    public Optional<Double> lookupPrice(final String barcode) {
        if (doThrowException) throw new RuntimeException("Some error occurred");
        this.barcode = barcode;
        return Optional.ofNullable(itemStore.get(barcode));
    }

    public void when(final String barcode, final double price) {
        itemStore.put(barcode, price);
    }

    public void throwExceptionInLookup() {
        doThrowException = true;
    }

    public void register(final String[] barcodes, final double price_0, final double price_1, final double price_2) {
        when(barcodes[0], price_0);
        when(barcodes[1], price_1);
        when(barcodes[2], price_2);
    }
}
