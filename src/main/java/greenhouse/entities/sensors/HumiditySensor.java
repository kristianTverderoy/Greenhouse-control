package greenhouse.entities.sensors;

public class HumiditySensor<T> extends Sensor<T>{


    public HumiditySensor(T type, String id, String location, T minimumReading, T maximumReading) {
        super("HumiditySensor", id, location, minimumReading, maximumReading);
    }
}
