package com.sepoe.wbitdd.pos;

import java.util.Optional;

/**
 * Interface to represent an item repository.
 *
 * @author lumiha
 * @since 08/05/15.
 */
public interface ItemRepository {
    Optional<Double> lookupPrice(String barcode);
}
