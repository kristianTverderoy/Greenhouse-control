package greenhouse.entities.filehandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import greenhouse.entities.Air;
import greenhouse.entities.GreenHouse;
import greenhouse.entities.Soil;
import greenhouse.entities.actuators.Actuator;
import greenhouse.entities.sensors.Sensor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for writing greenhouse data to JSON files using Gson.
 */
public class JsonWriter {

  private final Gson gson;

  /**
   * Creates a new JsonWriter with pretty printing enabled.
   */
  public JsonWriter() {
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  /**
   * Saves a greenhouse and its state (sensors, appliances, air, soil) to a JSON file.
   *
   * @param greenhouse The greenhouse to save
   * @param sensors The list of sensors in the greenhouse
   * @param appliances The list of appliances (actuators) in the greenhouse
   * @param filePath The file path where the JSON should be saved
   * @throws IOException If an error occurs while writing to the file
   */
  public void saveGreenhouse(GreenHouse greenhouse, List<Sensor<?>> sensors, 
                             List<Actuator> appliances, String filePath) throws IOException {
    GreenHouseDTO dto = convertToDTO(greenhouse, sensors, appliances);
    writeToFile(dto, filePath);
  }

  /**
   * Saves a greenhouse DTO to a JSON file.
   *
   * @param dto The greenhouse DTO to save
   * @param filePath The file path where the JSON should be saved
   * @throws IOException If an error occurs while writing to the file
   */
  public void saveGreenhouse(GreenHouseDTO dto, String filePath) throws IOException {
    writeToFile(dto, filePath);
  }

  /**
   * Converts a GreenHouse object and its components to a GreenHouseDTO.
   *
   * @param greenhouse The greenhouse to convert
   * @param sensors The list of sensors in the greenhouse
   * @param appliances The list of appliances (actuators) in the greenhouse
   * @return A GreenHouseDTO containing all the greenhouse data
   */
  private GreenHouseDTO convertToDTO(GreenHouse greenhouse, List<Sensor<?>> sensors, 
                                      List<Actuator> appliances) {
    GreenHouseDTO dto = new GreenHouseDTO();
    dto.setGreenHouseId(greenhouse.getID());
    
    // Convert sensors
    List<SensorDTO> sensorDTOs = new ArrayList<>();
    for (Sensor<?> sensor : sensors) {
      SensorDTO sensorDTO = new SensorDTO(
        sensor.getId(),
        sensor.getType(),
        sensor.isActive(),
        sensor.isConnected()
      );
      sensorDTOs.add(sensorDTO);
    }
    dto.setSensors(sensorDTOs);
    
    // Convert appliances (actuators)
    List<ApplianceDTO> applianceDTOs = new ArrayList<>();
    for (Actuator appliance : appliances) {
      ApplianceDTO applianceDTO = new ApplianceDTO(
        appliance.getId(),
        appliance.getType(),
        appliance.getPowerState()
      );
      applianceDTOs.add(applianceDTO);
    }
    dto.setAppliances(applianceDTOs);
    
    // Note: Air and Soil need to be accessed from the greenhouse
    // This is a placeholder - you may need to add getters to GreenHouse class
    // dto.setAir(convertAirToDTO(air));
    // dto.setSoil(convertSoilToDTO(soil));
    
    return dto;
  }

  /**
   * Converts an Air object to an AirDTO.
   *
   * @param air The air object to convert
   * @return An AirDTO containing all air data
   */
  public AirDTO convertAirToDTO(Air air) {
    return new AirDTO(
      air.getHumidity(),
      air.getLux(),
      air.getTemperature(),
      air.getTargetHumidity(),
      air.getTargetTemperature(),
      air.getLuxTarget()
    );
  }

  /**
   * Converts a Soil object to a SoilDTO.
   *
   * @param soil The soil object to convert
   * @return A SoilDTO containing all soil data
   */
  public SoilDTO convertSoilToDTO(Soil soil) {
    return new SoilDTO(
      soil.getSoilMoisture(),
      soil.getPhValue(),
      soil.getNitrogen()
    );
  }

  /**
   * Writes a GreenHouseDTO to a JSON file.
   *
   * @param dto The greenhouse DTO to write
   * @param filePath The file path where the JSON should be saved
   * @throws IOException If an error occurs while writing to the file
   */
  private void writeToFile(GreenHouseDTO dto, String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath)) {
      gson.toJson(dto, writer);
    }
  }

  /**
   * Converts any object to a JSON string.
   *
   * @param object The object to convert
   * @return A JSON string representation of the object
   */
  public String toJson(Object object) {
    return gson.toJson(object);
  }
}
