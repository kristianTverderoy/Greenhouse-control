package greenhouse.entities;

/**
 * If a green house hasn't been created, this exception is thrown.
 */
public class NoExistingGreenHouseException extends Exception {
  public NoExistingGreenHouseException(String message) {
    super(message);
  }

  public NoExistingGreenHouseException(){
    super();
  }
}
