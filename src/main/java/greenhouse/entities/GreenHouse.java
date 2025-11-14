package greenhouse.entities;

import greenhouse.entities.sensors.Sensor;
import greenhouse.entities.sensors.SensorContract;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GreenHouse {

  private final Map<Integer, Sensor<?>> sensors;
  private List<Sensorable> sensorableObjects;
  private final int greenHouseID;

  public GreenHouse(int greenHouseID) {
    this.sensors = new ConcurrentHashMap<>();
    this.greenHouseID = greenHouseID;
  }

  public void addSensor(Sensor<?> sensor) {
    this.sensors.put(sensor.getId(), sensor);
  }

  public int getID(){
    return this.greenHouseID;
  }

  public void getSensor(int id) {
    this.sensors.get(id);
  }

  /**
   * Due to zero based indexing the next available id is at the last id + 1.
   * Since sensors.size doesnt care about zero based indexing, the next available id is just
   * the number we get from taking the length of the list.
   * 
   * @return The next available id number for a sensor.
   */
  public int getNextAvailableSensorId(){
    return sensors.size();
  }


}