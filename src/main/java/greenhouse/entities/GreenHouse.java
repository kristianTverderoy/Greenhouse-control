package greenhouse.entities;

import greenhouse.entities.actuators.Actuator;
import greenhouse.entities.sensors.Sensor;

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

  public Sensor<?> getSensor(int id) {
    return this.sensors.get(id);
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

  public String getAllSensorsInformation() {
    StringBuilder sb = new StringBuilder();
    sensors.forEach((id, sensor) -> sb.append(sensor.toString()).append("\n"));
    return sb.toString();
  }

  //TODO: Implement getActuator and getAllActuatorsInformation
//  public Actuator getActuator(int i) {
//    return actuator.
//  }
//
//  public String getAllActuatorsInformation() {
//  }
}