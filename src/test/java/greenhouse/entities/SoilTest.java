package greenhouse.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  @Test
  public void dryOnce() {
    soil.dry();
    assertTrue(soil.getSoilMoisture() < 50);
    assertTrue(soil.getSoilMoisture() >= 45);
  }

  @Test
  public void soilMoistureNotLessThanZero() {
    soil.changeMoisture(-51);
    assertEquals(0, soil.getSoilMoisture());
  }

  @Test
  public void soilMoistureNotMoreThanHundred() {
    soil.changeMoisture(51);
    assertEquals(100, soil.getSoilMoisture());
  }

  @Test
  public void waterOnce() {
    soil.waterSoil(10);
    assertEquals(60, soil.getSoilMoisture());
    assertTrue(soil.getNitrogen() < 20);
    assertTrue(soil.getNitrogen() >= 17);
  }

  @Test
  public void phTickOnce() {
    soil.phTick();
    assertEquals(64, Math.round(soil.getPhValue()*10));
  }

  @Test
  public void phAddToOverFourteen() {
    soil.changePH(8);
    assertEquals(14, soil.getPhValue());
  }

  @Test
  public void phSubtractToUnderZero() {
    soil.changePH(-8);
    assertEquals(0, soil.getPhValue());
  }

  @Test
  public void subtractNitrogenToUnderZero() {
    soil.changeNitrogen(-21);
    assertEquals(0, soil.getNitrogen());
  }


}
