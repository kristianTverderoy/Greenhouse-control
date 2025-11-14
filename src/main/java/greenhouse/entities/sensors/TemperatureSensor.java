package greenhouse.entities.sensors;

public class TemperatureSensor<T> extends Sensor<T>{


  public TemperatureSensor(int id) {
    super("TemperatureSensor", id);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {
    
  }

  @Override
  public String toString() {
    return "TemperatureSensor{" +
            "id=" + getId() +
            ", isActive=" + isActive() +
            ", isConnected=" + isConnected() +
            ", isAlertState=" + isInAlertState() +
            '}';
  }
}
