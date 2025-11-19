package greenhouse.entities;

import greenhouse.entities.appliances.Appliance;
import greenhouse.entities.appliances.SoilAppliance;
import greenhouse.entities.sensors.Sensor;
import greenhouse.entities.sensors.*;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a greenhouse with soil and air.
 * Holds sensors that measure different values in the soil and air,
 * as well as Appliances that changes the values in the soil and air.
 */
public class GreenHouse {
  private final Map<Integer, Sensor<?>> sensors;
  private final Map<Integer, Appliance> appliances;
  private final int greenHouseID;
  private Soil soil;
  private Air air;
  private int nextSensorId = 0;
  private int nextApplianceId = 0;

  /**
   * Creates an instance of the GreenHouse.
   *
   * @param greenHouseID The id of the green house.
   */
  public GreenHouse(int greenHouseID) {
    this.sensors = new ConcurrentHashMap<>();
    this.appliances = new ConcurrentHashMap<>();
    this.greenHouseID = greenHouseID;
    initiateAirAndSoil();
  }

  /**
   * Adds the given sensor to the green house.
   *
   * @param sensor The sensor being added to the green house.
   */
  public void addSensor(Sensor<?> sensor) {
    this.sensors.put(sensor.getId(), sensor);

    if (sensor instanceof AirSubscriber airSubscriber) {
      airSubscriber.subscribe(this.air);
    }
    if (sensor instanceof SoilSubscriber soilSubscriber) {
      soilSubscriber.subscribe(this.soil);
    }
  }

  /**
   * Adds a humidity sensor to the green house.
   */
  public void addHumiditySensor() {
    addSensor(new HumiditySensor<>(getNextAvailableSensorId(), air));
  }

  /**
   * Adds a light sensor to the green house.
   */
  public void addLightSensor() {
    addSensor(new LightSensor<>(getNextAvailableSensorId(), air));
  }

  /**
   * Adds a ph sensor to the green house.
   */
  public void addPhSensor() {
    addSensor(new PHSensor<>(getNextAvailableSensorId(), soil));
  }

  /**
   * Adds a moisture sensor to the green house.
   */
  public void addMoistureSensor() {
    addSensor(new MoistureSensor<>(getNextAvailableSensorId(), soil));
  }

  /**
   * Adds a temperature sensor to the green house.
   */
  public void addTemperatureSensor() {
    addSensor(new TemperatureSensor<>(getNextAvailableSensorId(), air));
  }

  /**
   * Adds a nitrogen sensor to the green house.
   */
  public void addNitrogenSensor() {
    addSensor(new NitrogenSensor<>(getNextAvailableSensorId(), soil));
  }

  /**
   * Adds the given appliance to the green house.
   *
   * @param appliance The appliance being added to the green house.
   */
  public void addAppliance(Appliance appliance) {
    if (appliance instanceof SoilAppliance) {
      ((SoilAppliance) appliance).addSoil(this.soil);
    }
    this.appliances.put(appliance.getId(), appliance);
  }

  /**
   * Returns the id of the green house.
   *
   * @return The id of the green house.
   */
  public int getID(){
    return this.greenHouseID;
  }

  /**
   * Returns the sensor with the given id.
   *
   * @param id The id of the sensor being returned.
   * @return The sensor with the given id.
   */
  public Sensor<?> getSensor(int id) {
    return this.sensors.get(id);
  }

  /**
   * Initiates Air and Soil with default values.
   */
  private void initiateAirAndSoil() {
    this.soil = new Soil(50, 7, 20);
    this.air = new Air(20, 0.6f, 10000);
  }
  /**
   * Due to zero-based indexing, the next available id is at the last id + 1.
   * Since sensors. Size doesn't care about zero based indexing, the next available id is just
   * the number we get from taking the length of the list.
   *
   * @return The next available id number for a sensor.
   */
  public int getNextAvailableSensorId(){
    return this.nextSensorId++;
  }

  /**
   * Due to zero-based indexing, the next available id is at the last id + 1.
   * Since appliances.size() doesn't care about zero based indexing, the next available id is just
   * the number we get from taking the length of the map.
   *
   * @return The next available id number for an appliance.
   */
  public int getNextAvailableApplianceId(){
    return this.nextApplianceId++;
  }

  public String getAllSensorsInformation() {
    StringBuilder sb = new StringBuilder();
    sensors.forEach((id, sensor) -> sb.append(sensor.toString()).append("\n"));
    return sb.toString();
  }

  /**
   * Returns the appliance with the given id.
   *
   * @param id The id of the appliance being returned.
   * @return The appliance with the given id.
   */
  public Appliance getAppliance(int id) {
    return this.appliances.get(id);
  }

  /**
   * Returns the information on all the different appliances in the
   * green house.
   *
   * @return The information in all the different appliances in the
   * green house.
   */
  public String getAllAppliancesInformation() {
    StringBuilder sb = new StringBuilder();
    appliances.forEach((id, appliance) -> sb.append(appliance.toString()).append("\n"));
    return sb.toString();
  }

  /**
   * Removes the sensor with the given id.
   * @param sensorId The id of the sensor being removed.
   */
  public void removeSensor(int sensorId) {
    sensors.remove(sensorId);
  }

  /**
   * Removes the appliance with the given id.
   * @param applianceId The id of the appliance being removed.
   */
  public void removeAppliance(int applianceId) {
    appliances.remove(applianceId);
  }

  /**
   * Activates the appliance with the given id.
   *
   * @param id The id of the appliance being activated.
   */
  public void actuateAppliance(int id) {
    this.appliances.get(id).actuate();
  }

  /**
   * Updates the target temperature of the air in the greenhouse.
   *
   * @param targetTemperature The new target temperature for the air.
   */
  public void updateAirTemperatureTarget(double targetTemperature) {
    this.air.setTargetTemperature(targetTemperature);
  }

  /**
   * Updates the target humidity of the air in the greenhouse.
   *
   * @param targetHumidity The new target humidity for the air.
   */
  public void updateAirHumidityTarget(float targetHumidity) {
    this.air.setTargetHumidity(targetHumidity);
  };

}