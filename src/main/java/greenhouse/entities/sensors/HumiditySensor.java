package greenhouse.entities.sensors;

public class HumiditySensor<T> implements Sensor<T> {

    private String id;
    private String type;
    private String location;
    private T currentReading;
    private T minimumReading;
    private T maximumReading;
    private T averageReading;
    private boolean isActive;
    private boolean isConnected;

    public HumiditySensor(T type, String id, String location, T minimumReading, T maximumReading){
        this.id = id;
        this.type = "HumiditySensor";
        this.location = location;
        this.minimumReading = minimumReading;
        this.maximumReading = maximumReading;
    }
    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String getLocation() {
        return "";
    }

    @Override
    public T getCurrentReading() {
        if (this.currentReading == null){
        throw new SensorNotYetActiveException("Current reading can not be read if the sensor does not have a " +
                "reading to give");
        }
        return currentReading;
    }

    @Override
    public T getMinReading() {
        return this.minimumReading;
    }

    @Override
    public T getMaxReading() {
        return this.maximumReading;
    }

    @Override
    public T getAverageReading() {
        if (this.averageReading == null){
            throw new SensorNotYetActiveException("Average reading can not be read if the sensor does not have a" +
                    " reading to give");
        }
        return this.averageReading;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void setThreshold(Object min, Object max) {

    }

    @Override
    public boolean isInAlertState() {
        return false;
    }
}
