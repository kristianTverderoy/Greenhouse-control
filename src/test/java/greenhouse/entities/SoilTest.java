package greenhouse.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import greenhouse.logic.Soil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoilTest {
  private Soil soil;

  /**
   * Creates the Soil with default values before each test.
   */
  @BeforeEach
  public void init() {
    soil = new Soil(50,7, 20);
  }

  /**
   * Dries the soil once and checks that the moisture level of
   * the soil is within expected parameters.
   */
  @Test
  public void dryOnce() {
    soil.dry();
    assertTrue(soil.getSoilMoisture() < 50);
    assertTrue(soil.getSoilMoisture() >= 45);
  }

  /**
   * Checks that the moisture of the soil cannot be reduced to more than zero.
   */
  @Test
  public void soilMoistureNotLessThanZero() {
    soil.changeMoisture(-51);
    assertEquals(0, soil.getSoilMoisture());
  }

  /**
   * Checks that the soil moisture cannot be increased to more
   * that a hundred.
   */
  @Test
  public void soilMoistureNotMoreThanHundred() {
    soil.changeMoisture(51);
    assertEquals(100, soil.getSoilMoisture());
  }

  /**
   * Checks that the waterSoil method increases the moisture and
   * decreases the nitrogen level of the soil.
   */
  @Test
  public void waterOnce() {
    soil.waterSoil(10);
    assertEquals(60, soil.getSoilMoisture());
    assertTrue(soil.getNitrogen() < 20);
    assertTrue(soil.getNitrogen() >= 17);
  }

  /**
   * Checks that the phTick method reduces the ph of the soil
   * as expected.
   */
  @Test
  public void phTickOnce() {
    soil.phTick();
    assertEquals(64, Math.round(soil.getPhValue()*10));
  }

  /**
   * Checks that the ph of the soil cannot be increased over 14.
   */
  @Test
  public void phAddToOverFourteen() {
    soil.changePH(8);
    assertEquals(14, soil.getPhValue());
  }

  /**
   * Checks that the ph of the soil cannot be decreased under 0.
   */
  @Test
  public void phSubtractToUnderZero() {
    soil.changePH(-8);
    assertEquals(0, soil.getPhValue());
  }

  /**
   * Checks that the nitrogen of the soil cannot be decreased
   * to under 0.
   */
  @Test
  public void subtractNitrogenToUnderZero() {
    soil.changeNitrogen(-21);
    assertEquals(0, soil.getNitrogen());
  }


}
