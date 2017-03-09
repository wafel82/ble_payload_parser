package com.wafel.ble.payloadparser;

import java.util.Arrays;

import static com.wafel.ble.payloadparser.DataExtractors.DataExtractor;

public abstract class BaseDataExtractor<T> implements DataExtractor<T> {
    private final int numberOfBits;
    private final int startByte;
    private final int startBit;

    BaseDataExtractor(int startByte, int startBit, int numberOfBits) {
        this.numberOfBits = numberOfBits;
        this.startByte = startByte;
        this.startBit = startBit;
    }

    @Override
    public T extract(byte[] payload) {
        if (payload == null || isPayloadToShort(payload)) return onErrorValue();
        final int bytesCount = getBytesCount(startBit, numberOfBits);
        final byte[] dataBytesMostSignificantFirst = getDataBytesMostSignificantFirst(startByte, bytesCount, payload);
        final byte[] array = copyShiftedToStartBit(dataBytesMostSignificantFirst);
        return getValueFromBytes(array);
    }

    private boolean isPayloadToShort(byte[] payload) {
        return payload.length < startByte + getBytesCount(startBit, numberOfBits);
    }

    protected abstract T getValueFromBytes(byte[] bytes);

    protected abstract T onErrorValue();

    private byte[] copyShiftedToStartBit(byte[] bytes) {
        final byte[] copy = Arrays.copyOf(bytes, bytes.length);
        for (int i = copy.length - 1; i > 0; i--)
            copy[i] = (byte) ((byte) ((copy[i] & 0xFF) >> startBit) | (byte) (copy[i - 1] << 8 - startBit));
        copy[0] = (byte) ((copy[0] & 0xFF) >> startBit);
        return copy;
    }

    private byte[] getDataBytesMostSignificantFirst(int startByte, int bytesCount, byte[] payload) {
        return reversedCopy(Arrays.copyOfRange(payload, startByte, startByte + bytesCount));
    }

    private byte[] reversedCopy(byte[] bytes) {
        byte[] copy = new byte[bytes.length];
        for (int index = bytes.length - 1; index >= 0; index--)
            copy[index] = bytes[bytes.length - 1 - index];
        return copy;
    }

    private int getBytesCount(int startBit, int numberOfBits) {
        return ((startBit + numberOfBits) % 8 == 0)
                ? (startBit + numberOfBits) / 8
                : ((startBit + numberOfBits) / 8) + 1;
    }
}

