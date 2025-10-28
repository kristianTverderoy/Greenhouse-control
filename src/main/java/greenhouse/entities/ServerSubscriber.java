package greenhouse.entities;

/**
 * An interface defining a set of methods all that any object that would like to subscribe
 * to changes in objects of the ServerSubscriber class, must implement.
 *
 * <p>An object of the TempSensor class will then be able to treat objects of any class as a
 * ServerSubscriber as long as the class of the object has implemented this interface.
 */
public interface ServerSubscriber {
  /**
   * Called whenever the observed object need to signal its observers that a change has occurred.
   */
  void update();
}
