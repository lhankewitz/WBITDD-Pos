package com.sepoe.wbitdd.pos;

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
    public Double lookupItem(final String barcode) {
        if (doThrowException) throw new RuntimeException("Some error occurred");
        this.barcode = barcode;
        return itemStore.get(barcode);
    }

    @Override
    public Optional<Double> lookupPrice(final String barcode) {
        this.barcode = barcode;
        return Optional.ofNullable(itemStore.get(barcode));
    }

    public void when(final String barcode, final double price) {
        itemStore.put(barcode, price);
    }

    public void throwExceptionInLookup() {
        doThrowException = true;
    }
}
