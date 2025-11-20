package greenhouse.logic;

/**
 * Subscribes to the Clock and does an action when the clock ticks.
 */
public abstract class ClockSubscriber {

  /**
   * Does an action when the Clock ticks.
   */
  abstract void tick();

  /**
   * Subscribes to the Clock instance.
   */
  public void subscribe() {
    Clock.getInstance().subscribe(this);
  }
}
