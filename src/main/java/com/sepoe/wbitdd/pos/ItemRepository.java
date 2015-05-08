package com.sepoe.wbitdd.pos;

/**
 * Interface to represent an item repository.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public interface ItemRepository {
    Double lookupItem(String barcode);
}
