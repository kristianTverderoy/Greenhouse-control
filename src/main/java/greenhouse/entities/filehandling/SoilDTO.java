package greenhouse.entities.filehandling;

/**
 * Data Transfer Object for soil state data.
 * Contains moisture, pH, and nitrogen levels of the soil.
 * Made using AI
 */
public class SoilDTO {
  private double soilMoisture;
  private float phValue;
  private double nitrogen;

  /**
   * Default constructor for Gson deserialization.
   */
  public SoilDTO() {
  }

  /**
   * Creates a SoilDTO with all soil state data.
   *
   * @param soilMoisture Current soil moisture level (%)
   * @param phValue Current pH value
   * @param nitrogen Current nitrogen level
   */
  public SoilDTO(double soilMoisture, float phValue, double nitrogen) {
    this.soilMoisture = soilMoisture;
    this.phValue = phValue;
    this.nitrogen = nitrogen;
  }

  /**
   * Gets the soil moisture level.
   *
   * @return Soil moisture level (%)
   */
  public double getSoilMoisture() {
    return soilMoisture;
  }

  /**
   * Sets the soil moisture level.
   *
   * @param soilMoisture Soil moisture level (%) to set
   */
  public void setSoilMoisture(double soilMoisture) {
    this.soilMoisture = soilMoisture;
  }

  /**
   * Gets the pH value.
   *
   * @return pH value
   */
  public float getPhValue() {
    return phValue;
  }

  /**
   * Sets the pH value.
   *
   * @param phValue pH value to set
   */
  public void setPhValue(float phValue) {
    this.phValue = phValue;
  }

  /**
   * Gets the nitrogen level.
   *
   * @return Nitrogen level
   */
  public double getNitrogen() {
    return nitrogen;
  }

  /**
   * Sets the nitrogen level.
   *
   * @param nitrogen Nitrogen level to set
   */
  public void setNitrogen(double nitrogen) {
    this.nitrogen = nitrogen;
  }
}
