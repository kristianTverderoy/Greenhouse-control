package greenhouse.entities.sensors;

public interface SensorContract<T> {
    // Identity and metadata
    int getId();
    String getType();


    // Status and lifecycle
    boolean isActive();
    boolean isConnected();
    void start();
    void stop();
    void reset();

    // Configuration
    boolean isInAlertState();
}
