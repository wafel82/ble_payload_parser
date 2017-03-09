package com.wafel.ble.payloadparser;

public class DataExtractors {
    public interface DataExtractor<T> {
        T extract(byte[] blePayload);
    }

    public static final DataExtractor<Short> voltageExtractor = new ShortDataExtractor(17, 2, 14);

    // Here should be defined extractors for other data available in payload bytes.
    // Similar to the voltageExtractor - other extractors should be accessible
    // by static final reference
}
