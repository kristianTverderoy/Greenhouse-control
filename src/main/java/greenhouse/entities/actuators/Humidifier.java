package greenhouse.entities.actuators;

/**
 * Actuator responsible for raising humidity in the air.
 * The humidifier can adjust the rate at which it increases humidity levels
 * in the greenhouse environment.
 * 
 * <p>This actuator extends {@link Actuator} with Float type parameter,
 * representing humidity percentage values.
 */
public class Humidifier extends Actuator {
  private float humidityRate;

  /**
   * Constructs a new Humidifier with the specified parameters.
   *
   * @param type the type identifier for this actuator
   * @param id the unique identifier for this humidifier
   * @param humidityRate the rate at which humidity is increased (percentage per time unit)
   */
  public Humidifier(String type, int id, float humidityRate) {
    super(type, id);
    setHumidityRate(humidityRate);
  }

  /**
   * Sets the rate at which this humidifier increases humidity.
   *
   * @param humidityRate the new humidity rate (percentage per time unit)
   */
  public final void setHumidityRate(float humidityRate) {
    if (humidityRate < 0) {
      throw new IllegalArgumentException();
    }
    this.humidityRate = humidityRate;
  }

  /**
   * Gets the current humidity rate of this humidifier.
   *
   * @return the humidity rate (percentage per time unit)
   */
  public float getHumidityRate() {
    return this.humidityRate;
  }
  
}
