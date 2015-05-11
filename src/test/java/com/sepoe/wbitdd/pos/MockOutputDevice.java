package com.sepoe.wbitdd.pos;

/**
 * Class to test the use of the output device
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockOutputDevice extends DefaultOutputDevice {

    private String itemPrice;

    @Override
    public void writeItemPrice(final String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getOutputToWrite() {
        return itemPrice;
    }

}
