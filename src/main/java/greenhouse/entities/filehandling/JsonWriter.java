package greenhouse.entities.filehandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import greenhouse.entities.Air;
import greenhouse.entities.GreenHouse;
import greenhouse.entities.Soil;
import greenhouse.entities.appliances.Appliance;
import greenhouse.entities.appliances.AirAppliance;
import greenhouse.entities.sensors.Sensor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for writing greenhouse data to JSON files using Gson.
 */
public class JsonWriter {

  private final Gson gson;
  private static final String RESOURCES_PATH = "src/main/resources/greenhouses";

  /**
   * Creates a new JsonWriter with pretty printing enabled.
   */
  public JsonWriter() {
    this.gson = new GsonBuilder().setPrettyPrinting().create();
  }

  /**
   * Saves a greenhouse with its complete state (sensors, appliances, air, soil).
   * File will be saved as "greenhouse{id}.json" in the resources/greenhouses directory.
   * Overwrites existing files to prevent duplicates.
   *
   * @param greenhouse The greenhouse to save with its complete state
   * @throws IOException If an error occurs while writing to the file
   */
  public void saveGreenhouse(GreenHouse greenhouse) throws IOException {
    String fileName = "greenhouse" + greenhouse.getID() + ".json";
    String filePath = RESOURCES_PATH + "/" + fileName;
    GreenHouseDTO dto = convertToDTO(greenhouse);
    writeToFile(dto, filePath);
  }

  /**
   * Converts a GreenHouse object to a GreenHouseDTO with complete state.
   *
   * @param greenhouse The greenhouse to convert
   * @return A GreenHouseDTO containing all greenhouse data including air and soil state
   */
  private GreenHouseDTO convertToDTO(GreenHouse greenhouse) {
    GreenHouseDTO dto = new GreenHouseDTO();
    dto.setGreenHouseId(greenhouse.getID());
    
    // Convert sensors
    List<SensorDTO> sensorDTOs = new ArrayList<>();
    for (Sensor<?> sensor : greenhouse.getAllSensors()) {
      SensorDTO sensorDTO = new SensorDTO(
        sensor.getId(),
        sensor.getType()
      );
      sensorDTOs.add(sensorDTO);
    }
    dto.setSensors(sensorDTOs);
    
    // Convert appliances
    List<ApplianceDTO> applianceDTOs = new ArrayList<>();
    for (Appliance appliance : greenhouse.getAllAppliances()) {
      boolean powerState = false;
      if (appliance instanceof AirAppliance) {
        powerState = ((AirAppliance) appliance).getPowerState();
      }
      ApplianceDTO applianceDTO = new ApplianceDTO(
        appliance.getId(),
        appliance.getType(),
        powerState
      );
      applianceDTOs.add(applianceDTO);
    }
    dto.setAppliances(applianceDTOs);
    
    // Include air and soil state
    dto.setAir(convertAirToDTO(greenhouse.getAir()));
    dto.setSoil(convertSoilToDTO(greenhouse.getSoil()));
    
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
    // Ensure the directory exists
    File file = new File(filePath);
    File parentDir = file.getParentFile();
    if (parentDir != null && !parentDir.exists()) {
      parentDir.mkdirs();
    }
    
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
