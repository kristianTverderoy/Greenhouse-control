package greenhouse.entities.sensors;

public class SensorNotYetActiveException extends RuntimeException {
    public SensorNotYetActiveException(String message) {
        super(message);
    }
}
