package com.sepoe.wbitdd.pos;

/**
 * Class to ...
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockOutputDevice implements OutputDevice {

    private Double price;

    public Double getWrittenPrice() {
        return price;
    }

    @Override
    public void writePriceInformation(final Double price) {
        this.price = price;
    }
}
