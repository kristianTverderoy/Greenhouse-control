package greenhouse.entities.sensors;

import java.util.ArrayList;

/**
 * Abstract base class for all sensor types in the greenhouse system.
 * This class provides common functionality for managing sensor state, readings,
 * and alert thresholds.
 *
 * @param <T> the type of data this sensor reads (e.g., Double, Integer)
 */
public abstract class Sensor<T> {
  private int id;
  private final String type;
  private T currentReading;
  private T averageReading;
  private boolean isActive;
  private boolean isConnected;
  private boolean isAlertState = false;


  /**
   * Constructs a new Sensor with the specified parameters.
   *
   * @param type the type of sensor (e.g., "temperature", "humidity", "soil moisture")
   * @param id the unique identifier for this sensor
   */
  public Sensor(String type, int id) {
    this.id = id;
    this.type = type;
  }

  /**
   * Sets the sensor's id to an int value.
   *
   * @param newId The new unique identifier to assign to this sensor.
   */
  public void setId(int newId) {
    this.id = newId;
  }


  /**
   * Sets the alert state of this sensor.
   *
   * @param newState true if the sensor is in alert state, false otherwise
   */
  public void setAlertState(boolean newState) {
    this.isAlertState = newState;
  }

  /**
   * Updates the connection status of this sensor.
   *
   * @param newConnectionStatus true if connected, false if disconnected
   */
  public void setConnectionStatus(boolean newConnectionStatus) {
    this.isConnected = newConnectionStatus;
  }

  /**
   * Activates this sensor, enabling it to take readings.
   */
  public void setActive() {
    this.isActive = true;
  }

  /**
   * Deactivates this sensor, disabling it from taking readings.
   */
  public void setInactive() {
    this.isActive = false;
  }

  /**
   * Gets the unique identifier of this sensor.
   *
   * @return the sensor's ID
   */
  public int getId() {
    return this.id;
  }


  /**
   * Gets the type of this sensor.
   *
   * @return the sensor type
   */
  public String getType() {
    return this.type;
  }


  /**
   * Gets the current reading from this sensor.
   *
   * @return the current sensor reading
   * @throws SensorNotYetActiveException if the sensor has no current reading available
   */
  public T getCurrentReading() {
    if (this.currentReading == null) {
      throw new SensorNotYetActiveException("Current reading can not be read if the sensor  "
              + "does not have a reading to give");
    }
    return currentReading;
  }


  /**
   * Gets the average of all readings taken by this sensor.
   *
   * @return the average reading
   * @throws SensorNotYetActiveException if the sensor has no readings to calculate an average
   */
  public T getAverageReading() {
    if (this.averageReading == null) {
      throw new SensorNotYetActiveException("Average reading can not be read if the sensor "
              + " does not have a reading to give");
    }
    return this.averageReading;
  }

  /**
   * Checks if this sensor is currently active.
   *
   * @return true if the sensor is active, false otherwise
   */
  public boolean isActive() {
    return this.isActive;
  }

  /**
   * Checks if this sensor is currently connected.
   *
   * @return true if the sensor is connected, false otherwise
   */
  public boolean isConnected() {
    return this.isConnected;
  }

  public abstract void start();


  public abstract void stop();


  public void reset() {

  }


  /**
   * Checks if this sensor is currently in an alert state.
   *
   * @return true if the sensor is in alert state, false otherwise
   */
  public boolean isInAlertState() {
    return this.isAlertState;
  }

  @Override
  public abstract String toString();
}
