package greenhouse.entities.filehandling;

public class SoilDTO {
  private double soilMoisture;
  private float phValue;
  private double nitrogen;

  public SoilDTO() {
  }

  public SoilDTO(double soilMoisture, float phValue, double nitrogen) {
    this.soilMoisture = soilMoisture;
    this.phValue = phValue;
    this.nitrogen = nitrogen;
  }

  public double getSoilMoisture() {
    return soilMoisture;
  }

  public void setSoilMoisture(double soilMoisture) {
    this.soilMoisture = soilMoisture;
  }

  public float getPhValue() {
    return phValue;
  }

  public void setPhValue(float phValue) {
    this.phValue = phValue;
  }

  public double getNitrogen() {
    return nitrogen;
  }

  public void setNitrogen(double nitrogen) {
    this.nitrogen = nitrogen;
  }
}
