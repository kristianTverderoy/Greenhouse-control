package greenhouse.entities;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents a clock that ticks at a constant interval
 * measured in minutes. Tells a list of subscribers when
 * the clock has ticked. There exists only one instance of
 * the Clock.
 *
 */
public class Clock {
  private static int NORMAL = 10;
  private static int MODERATE = 5;
  private static int FAST = 1;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private static Clock clock = null;

  private ArrayList<ClockSubscriber> subscribers;

  /**
   * Creates the instance of the clock.
   */
  private Clock() {
    subscribers = new ArrayList<>();
  }

  /**
   * Starts the clock with normal tick speed as default.
   */
  public void start() {
    scheduler.scheduleAtFixedRate(tick(), 0, NORMAL, TimeUnit.MINUTES);
  }

  /**
   * Stops the clock.
   */
  public void stop() {
    scheduler.shutdown();
  }

  /**
   * Returns a runnable that tells every ClockSubscriber that the clock
   * has ticked.
   *
   * @return A runnable that tells every ClockSubscriber that the clock
   *  has ticked.
   */
  private Runnable tick() {
    return () -> subscribers.forEach(ClockSubscriber::tick);
  }

  /**
   * Returns the instance of the clock. If the clock hasn't been
   * initialized yet, it will initialize the clock.
   *
   * @return The clock instance.
   */
  public static synchronized Clock getInstance() {
    if (clock == null) {
      clock = new Clock();
    }
    return clock;
  }

  /**
   * Adds a ClockSubscriber to the list of subscribers.
   *
   * @param subscriber The ClockSubscriber being added to the list of
   *                   subscribers.
   */
  public void subscribe(ClockSubscriber subscriber) {
    subscribers.add(subscriber);
  }
}
