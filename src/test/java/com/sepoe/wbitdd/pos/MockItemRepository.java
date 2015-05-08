package com.sepoe.wbitdd.pos;

/**
 * Class to Mock an Item repository.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockItemRepository implements ItemRepository {

    private String barcode;

    public String getLookupBarcode() {
        return barcode;
    }

    @Override
    public String lookupItem(final String barcode) {
        this.barcode = barcode;
        return barcode;
    }
}
