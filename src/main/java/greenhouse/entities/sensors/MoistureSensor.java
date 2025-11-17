package greenhouse.entities.sensors;

import greenhouse.entities.Soil;
import greenhouse.entities.SoilSubscriber;

public class MoistureSensor<T> extends Sensor<T> implements SoilSubscriber {

  private double latestMoistureReading;

  
  public MoistureSensor(int id, Soil soil) {
    super("MoistureSensor", id);
    update(soil);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public void subscribe(Soil soil) {
    soil.addSubscriber(this);
  }

  @Override
  public void update(Soil soil) {
    this.latestMoistureReading = soil.getSoilMoisture();
  }

  @Override
  public String toString() {
    return "MoistureSensor{" +
            "id=" + getId() +
            ", latestMoistureReading=" + latestMoistureReading +
            '}';
  }

  public double getMoisture() {
    return latestMoistureReading;
  }
}
