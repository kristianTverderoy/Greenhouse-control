package greenhouse.entities.sensors;

import greenhouse.entities.Air;
import greenhouse.entities.AirSubscriber;

public class HumiditySensor<T> extends Sensor<T> implements AirSubscriber {

  private Float latestHumidityReading;

  public HumiditySensor(int id, Air air) {
    super("HumiditySensor", id);
    update(air);
  }

  @Override
  public void subscribe(Air air) {
    air.addSubscriber(this);
  }

  @Override
  public void update(Air air) {
    this.latestHumidityReading = air.getHumidity();
  }
  
  @Override
  public String toString() {
    return "HumiditySensor{" +
            "id=" + getId() +
            ", humidityLevel=" + this.latestHumidityReading +
            '}';
  }

  public double getHumidity() {
    return latestHumidityReading;
  }


}
