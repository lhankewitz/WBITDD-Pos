package com.sepoe.wbitdd.pos;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class to test to subsequent tests.
 * @author lumiha
 * @since 15/05/15.
 */
public class HandleTwoSubsequentSales {

    private MockOutputDevice display = new MockOutputDevice();

    // ToTest: subsequent sales
    @Test
    @Ignore("extract test data generator first")
    public void onTotal_forTwoSubsequentSales_calculatesTwoTotals() {


        assertThat(display.getOutputToWrite(), is("Total $12.50"));


        assertThat(display.getOutputToWrite(), is("Total $7.50"));

    }
}
