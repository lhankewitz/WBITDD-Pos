package com.sepoe.wbitdd.pos.util;

import java.util.Random;

public class BarcodeGenerator {
    public BarcodeGenerator() {
    }

    public String generateBarcode() {
        char[] digitArray = new char[12];

        for (int i = 0; i < 12; i++) {
            final int digit = new Random().nextInt(10);
            digitArray[i] = Integer.toString(digit).charAt(0);
        }

        return new String(digitArray);
    }

    public String[] generateBarcodes() {
        return new String[]{
                    generateBarcode()
                    , generateBarcode()
                    , generateBarcode()
            };
    }
}