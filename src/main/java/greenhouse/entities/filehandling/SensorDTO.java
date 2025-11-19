package greenhouse.entities.filehandling;

public class SensorDTO {
  private int id;
  private String type;
  private boolean isActive;
  private boolean isConnected;

  public SensorDTO() {
  }

  public SensorDTO(int id, String type) {
    this.id = id;
    this.type = type;
    this.isActive = isActive;
    this.isConnected = isConnected;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public boolean isConnected() {
    return isConnected;
  }

  public void setConnected(boolean connected) {
    isConnected = connected;
  }
}
