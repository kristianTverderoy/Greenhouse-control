package greenhouse.entities.sensors;

public class LightSensor<T> extends Sensor<T> {
  public LightSensor(int id) {
    super("LightSensor", id);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public String toString() {
    return "LightSensor{" +
            "id=" + getId() +
            ", isActive=" + isActive() +
            ", isConnected=" + isConnected() +
            ", isAlertState=" + isInAlertState() +
            '}';
  }
}
