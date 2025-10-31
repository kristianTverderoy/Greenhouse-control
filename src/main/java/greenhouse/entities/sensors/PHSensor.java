package greenhouse.entities.sensors;

public class PHSensor<T> extends Sensor<T>{

    private double phValue;
//    private PHActuator actuator;

    public PHSensor(String id, String location, T minimumReading, T maximumReading) {
        super("PHSensor", id, location, minimumReading, maximumReading);
    }
}
