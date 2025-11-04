package greenhouse.entities;

import greenhouse.entities.sensors.Sensor;
import greenhouse.entities.sensors.SensorContract;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GreenHouse<T> {

  Map<Integer, Sensor<T>> sensors;
  List<Sensorable> sensorableObjects;

  public GreenHouse() {
    this.sensors = new ConcurrentHashMap<>();
  }

  public void addSensor(Sensor<T> sensor) {
    this.sensors.put(sensor.getId(), sensor);
  }

  public void getSensor(int id) {
    this.sensors.get(id);
  }
}
