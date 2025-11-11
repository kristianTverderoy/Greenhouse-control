package greenhouse.entities.sensors;

import greenhouse.entities.Air;

public class TemperatureSensor<T> extends Sensor<T> {


  public TemperatureSensor(int id, T minimumReading, T maximumReading) {
    super("TemperatureSensor", id, minimumReading, maximumReading);
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {
    
  }
}
