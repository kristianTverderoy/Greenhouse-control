package greenhouse.entities;

import greenhouse.entities.appliances.Appliance;
import greenhouse.entities.sensors.Sensor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GreenHouse {

  private final Map<Integer, Sensor<?>> sensors;
  private final Map<Integer, Appliance> appliances;
  private List<Sensorable> sensorableObjects;
  private final int greenHouseID;

  public GreenHouse(int greenHouseID) {
    this.sensors = new ConcurrentHashMap<>();
    this.appliances = new ConcurrentHashMap<>();
    this.greenHouseID = greenHouseID;
  }

  public void addSensor(Sensor<?> sensor) {
    this.sensors.put(sensor.getId(), sensor);
  }

  public void addAppliance(Appliance appliance) {
    this.appliances.put(appliance.getId(), appliance);
  }

  public int getID(){
    return this.greenHouseID;
  }

  public Sensor<?> getSensor(int id) {
    return this.sensors.get(id);
  }

  /**
   * Due to zero-based indexing, the next available id is at the last id + 1.
   * Since sensors. Size doesn't care about zero based indexing, the next available id is just
   * the number we get from taking the length of the list.
   *
   * @return The next available id number for a sensor.
   */
  public int getNextAvailableSensorId(){
    return sensors.size();
  }

  /**
   * Due to zero-based indexing, the next available id is at the last id + 1.
   * Since appliances.size() doesn't care about zero based indexing, the next available id is just
   * the number we get from taking the length of the map.
   *
   * @return The next available id number for an appliance.
   */
  public int getNextAvailableApplianceId(){
    return appliances.size();
  }

  public String getAllSensorsInformation() {
    StringBuilder sb = new StringBuilder();
    sensors.forEach((id, sensor) -> sb.append(sensor.toString()).append("\n"));
    return sb.toString();
  }

  public Appliance getAppliance(int id) {
    return this.appliances.get(id);
  }

  public String getAllAppliancesInformation() {
    StringBuilder sb = new StringBuilder();
    appliances.forEach((id, appliance) -> sb.append(appliance.toString()).append("\n"));
    return sb.toString();
  }
}