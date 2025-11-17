package greenhouse.entities.sensors;

import greenhouse.entities.Soil;
import greenhouse.entities.SoilSubscriber;

public class NitrogenSensor<T> extends Sensor<T> implements SoilSubscriber {
  private double latestNitrogenReading;

  public NitrogenSensor(int id, Soil soil) {
    super("NitrogenSensor", id);
    update(soil);
  }


  @Override
  public void subscribe(Soil soil) {
    soil.addSubscriber(this);
  }

  @Override
  public void update(Soil soil) {
    latestNitrogenReading = soil.getNitrogen();
  }

  public double getNitrogen() {
    return latestNitrogenReading;
  }

  @Override
  public String toString() {
    return "NitrogenSensor{" +
        "id=" + getId() +
        ", latestNitrogenReading=" + latestNitrogenReading +
        '}';
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }
}
