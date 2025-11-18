package greenhouse.entities.filehandling;

import java.util.List;

public class GreenHouseDTO {
  private int greenHouseId;
  private List<SensorDTO> sensors;
  private List<ApplianceDTO> appliances;
  private AirDTO air;
  private SoilDTO soil;

  public GreenHouseDTO() {
    
  }

  public GreenHouseDTO(int greenHouseId, List<SensorDTO> sensors, List<ApplianceDTO> appliances, 
                       AirDTO air, SoilDTO soil) {
    this.greenHouseId = greenHouseId;
    this.sensors = sensors;
    this.appliances = appliances;
    this.air = air;
    this.soil = soil;
  }

  public int getGreenHouseId() {
    return greenHouseId;
  }

  public void setGreenHouseId(int greenHouseId) {
    this.greenHouseId = greenHouseId;
  }

  public List<SensorDTO> getSensors() {
    return sensors;
  }

  public void setSensors(List<SensorDTO> sensors) {
    this.sensors = sensors;
  }

  public List<ApplianceDTO> getAppliances() {
    return appliances;
  }

  public void setAppliances(List<ApplianceDTO> appliances) {
    this.appliances = appliances;
  }

  public AirDTO getAir() {
    return air;
  }

  public void setAir(AirDTO air) {
    this.air = air;
  }

  public SoilDTO getSoil() {
    return soil;
  }

  public void setSoil(SoilDTO soil) {
    this.soil = soil;
  }
}
