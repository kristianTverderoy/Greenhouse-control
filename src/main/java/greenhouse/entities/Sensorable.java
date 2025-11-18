package greenhouse.entities;

/**
 * A class that updates the sensors subscribed to it.
 */
public interface Sensorable {

  /**
   * Updates the sensors subscribed to the sensorable class.
   */
  void updateState();
}
