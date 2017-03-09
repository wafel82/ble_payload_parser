#  BLE packets containing telemetry data extractor

This project provide implementation capable to extract telemetry data from Beacons' additional BLE packets.
The idea is to create "extractors" dedicated for particular data types like battery voltage, temperature etc.

There is dedicated extractor for voltage implemented.
Additionally "entry points" to easily create extractor for other data types are provided.

## How to use extractors api
There is a single entry point to extractors api: `DataExtractors`. It contains preconfigured extractors for all supported telemetry data types (Actually - for purpose of this example implementation - only Battery Voltage extractor is available). 
Usage of `DataExtractors` api is rather straight forward - just choose appropriate extractor and call extract method:
```Java
private final byte payload[] = new byte[]{ 18, 36, 174, 136 ... } // Here is example BLE Package payload

final short voltage = DataExtractors.voltageExtractor.extract(payload);
```

## Batery Voltage extractor
Just like mentioned above - choose `voltageExtractor` from `DataExtractors` api and call extract - as simple as that :)
**Checkout tests prepared for `voltageExtractor` - they are available in `DataExtractorsTest` class
Don't be hesitate and play with them :)**

## What about other data types?
It's pretty obvious that Battery Voltage is not the only stuff available in Beacons' additional BLE packet.
This simple project present implementation for voltage extractor only.
However there are also an "entry points" to make it easy to create extractor for other types.
All you need to do is:
 - create extractor for java type capable to hold telemetry data you'd like to deal with. 
 There are already available extractor for `Short`, `Integer` and `Float. Creation of extractor for different type is as easy as
 extending `BaseDataExtractor` with appropriate type parameter. Here is example for double type:
 ```Java
 public class DoubleDataExtractor extends BaseDataExtractor<Double> {
    public DoubleDataExtractor(int startByte, int startBit, int numberOfBits) {
        super(startByte, startBit, numberOfBits);
    }

    @Override
    protected Double getValueFromBytes(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    @Override
    protected Double onErrorValue() {
        return 0.0;
    }
}
 ```
 - register your extractor in `DataExtractors` and provide configuration parameters (`starting byte in Payload array`, `first-significant bit in first byte` and the `bits count`)
 - that's it :)
