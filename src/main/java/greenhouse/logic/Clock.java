package greenhouse.logic;

import java.util.ArrayList;
import java.util.Arrays;
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
  private static final int NORMAL = 10;
  private static final int MODERATE = 5;
  private static final int FAST = 1;
  private ArrayList<Integer> speeds = new ArrayList<>(Arrays.asList(NORMAL, MODERATE, FAST));
  private int position;
  private int currentRate;

  private ScheduledExecutorService scheduler;
  private boolean on;
  private static Clock clock = null;
  private ArrayList<ClockSubscriber> subscribers;

  /**
   * Creates the instance of the clock.
   */
  private Clock() {
    subscribers = new ArrayList<>();
    on = false;
  }

  /**
   * Starts the clock with normal tick speed (10 min) as default.
   */
  public void start() {
    scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(tick(), 0, NORMAL, TimeUnit.SECONDS);
    position = 0;
    currentRate = NORMAL;
    on = true;
  }

  /**
   * Stops the clock.
   */
  public void stop() {
    scheduler.shutdown();
    on = false;
  }

  /**
   * Changes the rate at which the clock ticks.
   *
   * @param newRate The new tick rate of the clock.
   */
  public void changeRate(int newRate) {
    scheduler.scheduleAtFixedRate(tick(), 0, newRate, TimeUnit.MINUTES);
    currentRate = newRate;
  }

  /**
   * Speeds up the rate the clock ticks at between three predetermined
   * tick rates; 10 min, 5 min and 1 min.
   *
   * @param jump The jump increase between the predetermined tick rates.
   *             Can only be 1 or 2
   */
  public void speedUp(int jump) {
    if (jump < 1 || jump > 2) {
      throw new IllegalArgumentException("Rate of change must be 1 or 2");
    }
    if (!on) {
      throw new IllegalStateException("Clock is not on yet");
    }
    if (position + jump < speeds.size()) {
      position += jump;
    }
    else {
      position = speeds.size() - 1;
    }
    changeRate(speeds.get(position));
  }

  /**
   * Slows down the rate the clock ticks at between three predetermined
   * tick rates; 10 min, 5 min and 1 min.
   *
   * @param jump The jump decrease between the predetermined tick rates.
   *             Can only be 1 or 2.
   */
  public void slowDown(int jump) {
    if (jump < 1 || jump > 2) {
      throw new IllegalArgumentException("Rate of change must be 1 or 2");
    }
    if (!on) {
      throw new IllegalStateException("Clock is not on yet");
    }
    if (position - jump >= 0) {
      position -= jump;
    }
    else {
      position = 0;
    }
    changeRate(speeds.get(position));
  }

  /**
   * Returns the rate the clock ticks at.
   *
   * @return The rate the clock ticks at.
   */
  public int getCurrentRate() {
    return currentRate;
  }

  /**
   * Returns a runnable that tells every ClockSubscriber that the clock
   * has ticked.
   *
   * @return A runnable that tells every ClockSubscriber that the clock
   *  has ticked.
   */
  public Runnable tick() {
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
      clock.start();
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

  /**
   * Removes the subscriber with the given subscriber id.
   *
   * @param subscriber the ClockSubscriber to remove from the list of subscribers.
   */
  public void removeSubscriber(ClockSubscriber subscriber) {
    subscribers.remove(subscriber);
  }

  /**
   * Returns the id of the last subscriber in the clock's list
   * of subscribers.
   *
   * @return The id of the last subscriber in the clock's list of subscribers.
   */
  public int getLastSubscriber() {
    return subscribers.size() - 1;
  }
}
