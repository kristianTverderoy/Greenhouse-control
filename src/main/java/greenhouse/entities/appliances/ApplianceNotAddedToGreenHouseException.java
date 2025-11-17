package greenhouse.entities.appliances;

/**
 * Exception thrown when an appliance cannot be added to a greenhouse.
 * This can occur due to various reasons such as duplicate appliance IDs,
 * invalid appliance configuration, or greenhouse capacity issues.
 */
public class ApplianceNotAddedToGreenHouseException extends Exception {

  /**
   * Constructs a new exception with no detail message.
   */
  public ApplianceNotAddedToGreenHouseException() {
    super("Appliance could not be added to the greenhouse.");
  }

  /**
   * Constructs a new exception with the specified detail message.
   *
   * @param message the detail message explaining why the appliance wasn't added
   */
  public ApplianceNotAddedToGreenHouseException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause of this exception
   */
  public ApplianceNotAddedToGreenHouseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified cause.
   *
   * @param cause the cause of this exception
   */
  public ApplianceNotAddedToGreenHouseException(Throwable cause) {
    super("Appliance could not be added to the greenhouse.", cause);
  }
}

