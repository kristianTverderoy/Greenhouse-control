package greenhouse.entities.sensors;

public interface Sensor<T> {
    // Identity and metadata
    String getId();
    String getType();
    String getLocation();

    // Core functionality
    T getCurrentReading();
    T getMinReading();
    T getMaxReading();
    T getAverageReading();

    // Status and lifecycle
    boolean isActive();
    boolean isConnected();
    void start();
    void stop();
    void reset();

    // Configuration
    void setThreshold(T min, T max);
    boolean isInAlertState();
}
