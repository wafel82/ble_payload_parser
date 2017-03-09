package com.wafel.ble.payloadparser;

import java.nio.ByteBuffer;

// EXAMPLE IMPLEMENTATION
// Added just to present how to use BaseDataExtractor
// to extract different types
public class IntDataExtractor extends BaseDataExtractor<Integer>{
    public IntDataExtractor(int startByte, int startBit, int numberOfBits) {
        super(startByte, startBit, numberOfBits);
    }

    @Override
    protected Integer getValueFromBytes(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    @Override
    protected Integer onErrorValue() {
        return 0;
    }
}
