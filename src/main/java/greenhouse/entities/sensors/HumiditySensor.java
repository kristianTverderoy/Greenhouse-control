package greenhouse.entities.sensors;

public class HumiditySensor<T> extends Sensor<T> {


    public HumiditySensor(int id) {
        super("HumiditySensor", id);
    }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public String toString() {
    return "HumiditySensor{" +
            "id=" + getId() +
            ", isActive=" + isActive() +
            ", isConnected=" + isConnected() +
            ", isAlertState=" + isInAlertState() +
            '}';
  }
}
