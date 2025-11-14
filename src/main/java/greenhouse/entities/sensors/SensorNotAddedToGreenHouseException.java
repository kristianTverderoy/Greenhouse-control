package greenhouse.entities.sensors;

/**
 * Exception thrown when a sensor cannot be added to a greenhouse.
 * This can occur due to various reasons such as duplicate sensor IDs,
 * invalid sensor configuration, or greenhouse capacity issues.
 */
public class SensorNotAddedToGreenHouseException extends Exception {

  /**
   * Constructs a new exception with no detail message.
   */
  public SensorNotAddedToGreenHouseException() {
    super("Sensor could not be added to the greenhouse.");
  }

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message the detail message explaining why the sensor wasn't added
   */
  public SensorNotAddedToGreenHouseException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of this exception
   */
  public SensorNotAddedToGreenHouseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause.
   *
   * @param cause the cause of this exception
   */
  public SensorNotAddedToGreenHouseException(Throwable cause) {
    super("Sensor could not be added to the greenhouse.", cause);
  }
}

