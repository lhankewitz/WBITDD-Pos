package com.sepoe.wbitdd.pos;

/**
 * Class to ...
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockOutputDevice implements OutputDevice {

    private Double price;
    private String itemPrice;


    @Override
    public void writeItemPrice(final String itemPrice) {

        this.itemPrice = itemPrice;
    }

    public String getOutputToWrite() {
        return itemPrice;
    }
}
