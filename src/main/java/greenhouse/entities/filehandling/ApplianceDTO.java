package greenhouse.entities.filehandling;

public class ApplianceDTO {
  private int id;
  private String type;
  private boolean isOn;

  public ApplianceDTO() {
  }

  public ApplianceDTO(int id, String type, boolean isOn) {
    this.id = id;
    this.type = type;
    this.isOn = isOn;
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

  public boolean isOn() {
    return isOn;
  }

  public void setOn(boolean on) {
    isOn = on;
  }
}
