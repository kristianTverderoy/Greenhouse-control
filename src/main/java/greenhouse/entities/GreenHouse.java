package greenhouse.entities;

import greenhouse.entities.sensors.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GreenHouse {

  private final Map<Integer, Sensor<?>> sensors;
  private final int greenHouseID;
  private Soil soil;
  private Air air;


  public GreenHouse(int greenHouseID) {
    this.sensors = new ConcurrentHashMap<>();
    this.greenHouseID = greenHouseID;
    initiateAirAndSoil();
  }

  public void addSensor(Sensor<?> sensor) {
    this.sensors.put(sensor.getId(), sensor);

    if (sensor instanceof AirSubscriber airSubscriber) {
      airSubscriber.subscribe(this.air);
    }
    if (sensor instanceof SoilSubscriber soilSubscriber) {
      soilSubscriber.subscribe(this.soil);
    }
  }

  public void addHumiditySensor() {
    addSensor(new HumiditySensor<>(getNextAvailableSensorId(), air));
  }

  public void addLightSensor() {
    addSensor(new LightSensor<>(getNextAvailableSensorId(), air));
  }

  public void addPhSensor() {
    addSensor(new PHSensor<>(getNextAvailableSensorId(), soil));
  }

  public void addMoistureSensor() {
    addSensor(new MoistureSensor<>(getNextAvailableSensorId(), soil));
  }

  public void addTemperatureSensor() {
    addSensor(new TemperatureSensor<>(getNextAvailableSensorId(), air));
  }

  public void addNitrogenSensor() {
    addSensor(new NitrogenSensor<>(getNextAvailableSensorId(), soil));
  }

  public int getID(){
    return this.greenHouseID;
  }

  public Sensor<?> getSensor(int id) {
    return this.sensors.get(id);
  }

  /**
   * Initiates Air and Soil with default values.
   */
  private void initiateAirAndSoil() {
    this.soil = new Soil(50, 7, 20);
    this.air = new Air(20, 60, 10000);
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