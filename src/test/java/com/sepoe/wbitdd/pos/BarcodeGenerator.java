package com.sepoe.wbitdd.pos;

import java.util.Random;

public class BarcodeGenerator {
    public BarcodeGenerator() {
    }

    String generateBarCode() {
        char[] digitArray = new char[12];

        for (int i = 0; i < 12; i++) {
            final int digit = new Random().nextInt(10);
            digitArray[i] = Integer.toString(digit).charAt(0);
        }

        return new String(digitArray);
    }

    String[] generateBarcodes() {
        return new String[]{
                    generateBarCode()
                    , generateBarCode()
                    , generateBarCode()
            };
    }
}