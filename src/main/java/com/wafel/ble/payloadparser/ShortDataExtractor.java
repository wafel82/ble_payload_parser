package com.wafel.ble.payloadparser;

import java.nio.ByteBuffer;

public class ShortDataExtractor extends BaseDataExtractor<Short>{
    public ShortDataExtractor(int startByte, int startBit, int numberOfBits) {
        super(startByte, startBit, numberOfBits);
    }

    @Override
    protected Short getValueFromBytes(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }

    @Override
    protected Short onErrorValue() {
        return 0;
    }
}
