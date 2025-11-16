package greenhouse.entities;

/**
 * Represents all the sensors that is alerted when the Air values
 * changes.
 */
public interface AirSubscriber {

  /**
   * Subscribes the AirSubscriber to the given Air.
   *
   * @param air The air the subscriber subscribes to.
   */
  public void subscribe(Air air);

  /**
   * Alerts the subscriber to a change in the air so that the
   * subscriber can do something about it.
   *
   * @param air The air that just has been changed.
   */
  public void update(Air air);
}
