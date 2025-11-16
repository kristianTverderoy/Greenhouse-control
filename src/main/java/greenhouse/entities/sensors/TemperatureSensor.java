package greenhouse.entities.sensors;

import greenhouse.entities.Air;
import greenhouse.entities.AirSubscriber;

public class TemperatureSensor<T> extends Sensor<T> implements AirSubscriber {

  private double latestTemperatureReading;

  public TemperatureSensor(int id,Air air) {
    super("TemperatureSensor", id);
    update(air);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {
    
  }

  @Override
  public void subscribe(Air air) {
    air.addSubscriber(this);
  }

  @Override
  public void update(Air air) {
    this.latestTemperatureReading = air.getTemperature();
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
