package greenhouse.entities.sensors;

public class PHSensor<T> extends Sensor<T> {

    private double latestPhReading;
//    private PHAppliance appliance;

    public PHSensor(int id) {
        super("PHSensor", id);
    }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public String toString() {
    return "PHSensor{" +
            "id=" + getId() +
            ", latestPhReading=" + latestPhReading +
            ", isActive=" + isActive() +
            ", isConnected=" + isConnected() +
            ", isAlertState=" + isInAlertState() +
            '}';
  }
}
