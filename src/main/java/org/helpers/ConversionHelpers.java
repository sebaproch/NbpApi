package org.helpers;

public class ConversionHelpers {

    public static Double convertToDouble(Object rateValue) {
        if (rateValue instanceof Float) {
            return ((Float) rateValue).doubleValue();
        } else if (rateValue instanceof Double) {
            return (Double) rateValue;
        } else {
            throw new RuntimeException("Unsupported type for rate value: " + rateValue.getClass().getName());
        }
    }
}