package greenhouse.entities.sensors;

import greenhouse.entities.Soil;
import greenhouse.entities.SoilSubscriber;

public class PHSensor<T> extends Sensor<T> implements SoilSubscriber {

    private double latestPhReading;
//    private PHAppliance appliance;

  public PHSensor(int id, Soil soil) {
    super("PHSensor", id);
    update(soil);
  }

  @Override
  public void subscribe(Soil soil) {
    soil.addSubscriber(this);
  }

  @Override
  public void update(Soil soil) {
    this.latestPhReading = soil.getPhValue();
  }

  @Override
  public String toString() {
    return "PHSensor{" +
            "id=" + getId() +
            ", latestPhReading=" + latestPhReading +
            '}';
  }

  public double getPh() {
    return latestPhReading;
  }
}
