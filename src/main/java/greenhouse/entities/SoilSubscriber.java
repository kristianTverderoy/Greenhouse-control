package greenhouse.entities;

/**
 * Represents all the sensors that is alerted when the Soil values
 * changes.
 */
public interface SoilSubscriber {

  /**
   * Subscribes the SoilSubscriber to the given Soil.
   *
   * @param soil The soil the subscriber subscribes to.
   */
  public void subscribe(Soil soil);

  /**
   * Alerts the subscriber to a change in the soil so that the
   * subscriber can do something about it.
   *
   * @param soil The soil that just has been changed.
   */
  public void update(Soil soil);
}
