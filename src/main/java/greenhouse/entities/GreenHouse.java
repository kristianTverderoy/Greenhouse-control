package greenhouse.entities;

import greenhouse.entities.sensors.SensorContract;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GreenHouse {

  Map<Integer, SensorContract> sensors;

  public GreenHouse(){
    this.sensors = new ConcurrentHashMap<>();
  }
}
