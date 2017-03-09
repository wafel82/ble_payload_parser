package com.wafel.ble.payloadparser;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class DataExtractorsTest {

    private final byte payload[] = new byte[]{
            (byte) 18,  (byte) 36,  (byte) 174, (byte) 136, (byte) 46, (byte) 6,  (byte) 34,  (byte) 23,
            (byte) 189, (byte) 1,   (byte) 0,   (byte) 0,   (byte) 0,  (byte) 57, (byte) 251, (byte) 101,
            (byte) 99,  (byte) 208, (byte) 92,  (byte) 98};


    @Test
    public void shouldReturnValidVoltageValue() {
        final short voltage = DataExtractors.voltageExtractor.extract(payload);
        assertThat(voltage).isEqualTo(5940);
    }


    @Test
    public void shouldReturnZeroValueWhenNullPassedAsPayload() {
        assertThat(DataExtractors.voltageExtractor.extract(null)).isEqualTo(0);
    }

    @Test
    public void shouldReturnZeroIfPayloadIsToShortToHaveVoltageData() {
        assertThat(DataExtractors.voltageExtractor.extract(new byte[17])).isEqualTo(0);
    }

    @Test
    public void shouldReturnValidVoltageWhenValueIsStoredOnLessSignificantBitOnly() {
        assertThat(DataExtractors.voltageExtractor.extract(getPayload( 0x04,  0x00))).isEqualTo(1);
        assertThat(DataExtractors.voltageExtractor.extract(getPayload(0xFF,  0x00))).isEqualTo(63);
    }

    @Test
    public void shouldProperlyHandleSignBitOnLessSignificantBit() {
        assertThat(DataExtractors.voltageExtractor.extract(getPayload( 0x80,  0x00))).isEqualTo(32);
    }

    @Test
    public void shouldReturnValidVoltageWhenValueIsStoredOnMostSignificantBitOnly() {
        assertThat(DataExtractors.voltageExtractor.extract(getPayload( 0x00,  0x01))).isEqualTo(64);
        assertThat(DataExtractors.voltageExtractor.extract(getPayload(0x00, 0x7F))).isEqualTo(8128);
    }

    @Test
    public void shouldProperlyHandleSignBitOnMostSignificantBit() {
        assertThat(DataExtractors.voltageExtractor.extract(getPayload( 0x00,  0x80))).isEqualTo(8192);
    }

    private byte[] getPayload(int lessSignificantByte, int mostSignificantByte) {
        final byte[] blePayload = new byte[19];
        blePayload[17] = (byte) lessSignificantByte;
        blePayload[18] = (byte) mostSignificantByte;
        return blePayload;
    }
}