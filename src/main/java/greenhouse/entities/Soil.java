package greenhouse.entities;

import java.util.ArrayList;

/**
 * <p>
 *   Represents a soil sample with a ph value,
 *   moisture level and nitrogen level.
 */
public class Soil extends ClockSubscriber implements Sensorable {
  private double soilMoisture; //The percentage of moisture of the soil
  private float phValue; //The ph value of the soil.
  private double nitrogen; //The amount of nitrogen in the soil, measured in ppm
  private ArrayList<SoilSubscriber> subscribers;

  /**
   * Creates an instance of Soil.
   *
   * @param soilMoisture The initial value of the soil humidity.
   * @param phValue The initial value of the ph of the soil.
   * @param nitrogen The initial amount of nitrogen in the soil.
   */
  public Soil(double soilMoisture, float phValue, double nitrogen) {
    this.phValue = phValue;
    this.soilMoisture = soilMoisture;
    this.nitrogen = nitrogen;
    subscribers = new ArrayList<>();
    subscribe();
  }

  /**
   * Adds the given humidity to the soil humidity.
   *
   * @param moistureChange The value of the change in the soil humidity.
   */
  public void changeMoisture(double moistureChange) {
    if (soilMoisture + moistureChange < 0) {
      soilMoisture = 0;
    }
    else if (soilMoisture + moistureChange > 100) {
      soilMoisture = 100;
    }
    else {
      soilMoisture += moistureChange;
    }
    update();
  }

  /**
   * Adds the given nitrogen to the nitrogen in the soil.
   *
   * @param nitrogenChange The amount of nitrogen being added to the soil.
   */
  public void changeNitrogen(double nitrogenChange) {
    if (nitrogen + nitrogenChange < 0) {
      nitrogen = 0;
    }
    else {
      nitrogen += nitrogenChange;
    }
    update();
  }

  /**
   * Adds the given ph value to the ph in the soil.
   *
   * @param phChange The value being added to the ph value of the soil.
   */
  public void changePH(float phChange) {
    if (phValue + phChange < 0) {
      phValue = 0;
    }
    else if (phValue + phChange > 14) {
      phValue = 14;
    }
    else {
      phValue += phChange;
    }
    update();
  }

  /**
   * Waters the soil. Increases the humidity of the soil, and decreases
   * the fertilization.
   *
   * @param amountWatered The amount of humidity being added to the soil.
   */
  public void waterSoil(double amountWatered) {
    changeMoisture(amountWatered);
    changeNitrogen(-amountWatered * 0.2 - Math.random());
  }

  /**
   * Reduces the moisture in the soil by between 4% and 5%.
   * Used every time the clock ticks.
   */
  public void dry() {
    changeMoisture(-5 + Math.random());
  }

  /**
   * Adds the given amount of nitrogen to the soil.
   * Reduces the ph of the soil based on the given nitrogen.
   * Used by a fertilizer appliance.
   *
   * @param nitrogenAmount The amount of nitrogen being added to the soil
   *                       in ppm.
   */
  public void fertilize(double nitrogenAmount) {
    changeNitrogen(nitrogenAmount);
    changePH( (float) ((-nitrogenAmount%7)*0.1));
  }

  /**
   * Reduces the nitrogen in the soil by 3 ppm.
   * Used every time the clock ticks.
   */
  public void useFertilizer() {
    changeNitrogen(-3);
  }

  /**
   * Reduces the ph based on how much nitrogen
   * there is in the soil.
   * Used every time the clock ticks.
   */
  public void phTick() {
    changePH( (float) ((-nitrogen%14)*0.1));
  }

  /**
   * Increases the ph by 0.5.
   * Used by a liming appliance.
   */
  public void lime() {
    changePH((float) (0.5));
  }

  /**
   * Returns the humidity of the soil.
   *
   * @return The humidity of the soil.
   */
  public double getSoilMoisture() {
    return soilMoisture;
  }

  /**
   * Returns the ph of the soil.
   *
   * @return The ph of the soil.
   */
  public float getPhValue() {
    return phValue;
  }

  /**
   * Returns the amount of nitrogen in the soil.
   *
   * @return The amount of nitrogen in the soil in ppm.
   */
  public double getNitrogen() {
    return nitrogen;
  }

  /**
   * Changes the value of soilMoisture, nitrogen and phValue, and alerts the
   * soilSubscribers to the change.
   */
  @Override
  public void updateState() {
    phTick();
    dry();
    useFertilizer();
    update();
  }

  /**
   * Updates the soilMoisture, nitrogen and phValue every time the clock ticks.
   */
  @Override
  void tick() {
    updateState();
  }

  /**
   * Adds a subscriber to the list of subscribers of this soil.
   *
   * @param subscriber The subscriber being added to the list of
   *                   subscribers.
   */
  public void addSubscriber(SoilSubscriber subscriber) {
    subscribers.add(subscriber);
  }

  /**
   * Alerts the soilSubscribers that the soil has changed.
   */
  public void update() {
    subscribers.forEach(subscriber -> subscriber.update(this));
  }
}
