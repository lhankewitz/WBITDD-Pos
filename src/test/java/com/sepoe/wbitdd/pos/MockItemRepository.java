package com.sepoe.wbitdd.pos;

/**
 * Class to Mock an Item repository.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockItemRepository implements ItemRepository {


    public String getLookupBarcode() {
        return "123456789012";
    }
}
