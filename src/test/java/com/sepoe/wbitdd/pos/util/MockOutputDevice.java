package com.sepoe.wbitdd.pos.util;

import com.sepoe.wbitdd.pos.DefaultOutputDevice;

/**
 * Class to test the use of the output device
 *
 * @author lumiha
 * @since 08/05/15.
 */
public class MockOutputDevice extends DefaultOutputDevice {

    private String itemPrice;

    @Override
    public void write(final String message) {
        this.itemPrice = message;
    }

    public String getOutputToWrite() {
        return itemPrice;
    }

}
