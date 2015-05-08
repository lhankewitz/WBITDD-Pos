package com.sepoe.wbitdd.pos;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Class to test the point of sale component of the WBITTD of J.B. Rainsberger.
 *
 * @author L.Hankewitz
 * @since 08/05/15.
 * <p>
 */
public class PointOfSaleCreationTest {
    @Test
    public void creatingPointOfSale_withMissingOutputDevice_throwsException() {
        try {
            new PointOfSale(new ItemRepository(){}, null);
            fail("Creation of PointOfSale without OutputDevice should throw exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("Missing output device"));
        }
    }

    @Test
    public void createPointOfSale_withMissingItemRepository_throwsException() {
        try {
            new PointOfSale(null, new OutputDevice() {});
            fail("Creation of PointOfSale without item repository should throw exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("Missing item repository"));
        }

        try {
            new PointOfSale(null, null);
            fail("Creation of PointOfSale without item repository should throw exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("Missing item repository"));
        }
    }

    @Test
    public void createPointOfSale_withOutputDeviceAndItemRepository() {
        new PointOfSale(new ItemRepository() { }, new OutputDevice() {});
    }

}
