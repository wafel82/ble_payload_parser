package com.wafel.ble.payloadparser;

import java.nio.ByteBuffer;

// EXAMPLE IMPLEMENTATION
// Added just to present how to use BaseDataExtractor
// to extract different types
public class FloatDataExtractor extends BaseDataExtractor<Float> {
    public FloatDataExtractor(int startByte, int startBit, int numberOfBits) {
        super(startByte, startBit, numberOfBits);
    }

    @Override
    protected Float getValueFromBytes(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }

    @Override
    protected Float onErrorValue() {
        return 0.0f;
    }
}
