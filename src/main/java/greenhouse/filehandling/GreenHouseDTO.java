package greenhouse.filehandling;

import java.util.List;

/**
 * Data Transfer Object for greenhouse data.
 * Used for JSON serialization/deserialization of greenhouse state.
 * Contains all information needed to restore a greenhouse including sensors, appliances, air, and soil state.
 */
public class GreenHouseDTO {
  private int greenHouseId;
  private List<SensorDTO> sensors;
  private List<ApplianceDTO> appliances;
  private AirDTO air;
  private SoilDTO soil;
  private int nextSensorId;
  private int nextApplianceId;

  /**
   * Default constructor for Gson deserialization.
   */
  public GreenHouseDTO() {
    
  }

  /**
   * Creates a GreenHouseDTO with all greenhouse data.
   *
   * @param greenHouseId The unique identifier of the greenhouse
   * @param sensors List of sensors in the greenhouse
   * @param appliances List of appliances in the greenhouse
   * @param air The air state data
   * @param soil The soil state data
   * @param nextSensorId The next available sensor ID
   * @param nextApplianceId The next available appliance ID
   */
  public GreenHouseDTO(int greenHouseId, List<SensorDTO> sensors, List<ApplianceDTO> appliances, 
                       AirDTO air, SoilDTO soil, int nextSensorId, int nextApplianceId) {
    this.greenHouseId = greenHouseId;
    this.sensors = sensors;
    this.appliances = appliances;
    this.air = air;
    this.soil = soil;
    this.nextSensorId = nextSensorId;
    this.nextApplianceId = nextApplianceId;
  }

  /**
   * Gets the greenhouse ID.
   *
   * @return The greenhouse ID
   */
  public int getGreenHouseId() {
    return greenHouseId;
  }

  /**
   * Sets the greenhouse ID.
   *
   * @param greenHouseId The greenhouse ID to set
   */
  public void setGreenHouseId(int greenHouseId) {
    this.greenHouseId = greenHouseId;
  }

  /**
   * Gets the list of sensors.
   *
   * @return The list of sensor DTOs
   */
  public List<SensorDTO> getSensors() {
    return sensors;
  }

  /**
   * Sets the list of sensors.
   *
   * @param sensors The list of sensor DTOs to set
   */
  public void setSensors(List<SensorDTO> sensors) {
    this.sensors = sensors;
  }

  /**
   * Gets the list of appliances.
   *
   * @return The list of appliance DTOs
   */
  public List<ApplianceDTO> getAppliances() {
    return appliances;
  }

  /**
   * Sets the list of appliances.
   *
   * @param appliances The list of appliance DTOs to set
   */
  public void setAppliances(List<ApplianceDTO> appliances) {
    this.appliances = appliances;
  }

  /**
   * Gets the air state data.
   *
   * @return The air DTO
   */
  public AirDTO getAir() {
    return air;
  }

  /**
   * Sets the air state data.
   *
   * @param air The air DTO to set
   */
  public void setAir(AirDTO air) {
    this.air = air;
  }

  /**
   * Gets the soil state data.
   *
   * @return The soil DTO
   */
  public SoilDTO getSoil() {
    return soil;
  }

  /**
   * Sets the soil state data.
   *
   * @param soil The soil DTO to set
   */
  public void setSoil(SoilDTO soil) {
    this.soil = soil;
  }

  /**
   * Sets the next available appliance ID.
   *
   * @param nextApplianceId The next appliance ID to set
   */
  public void setNextApplianceId(int nextApplianceId) {
    this.nextApplianceId = nextApplianceId;
  }

  /**
   * Sets the next available sensor ID.
   *
   * @param nextSensorId The next sensor ID to set
   */
  public void setNextSensorId(int nextSensorId) {
    this.nextSensorId = nextSensorId;
  }

  /**
   * Gets the next available appliance ID.
   *
   * @return The next appliance ID
   */
  public int getNextApplianceId() {
    return nextApplianceId;
  }

  /**
   * Gets the next available sensor ID.
   *
   * @return The next sensor ID
   */
  public int getNextSensorId() {
    return nextSensorId;
  }
}
