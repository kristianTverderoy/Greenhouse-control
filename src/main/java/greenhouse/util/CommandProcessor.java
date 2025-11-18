package greenhouse.util;

/**
 * Processes commands related to the Greenhouse system.
 */
public class CommandProcessor {

  /**
   * Responsible for extracting and returning manual information for a specific command.
   *
   * @param messageFromClient The full message from client.
   * @return The manual information for the selected command.
   */
  public static String handleManualRequest(String messageFromClient){
    try {
      String[] parts = messageFromClient.split("-");
      parts[1] = parts[1].trim().toLowerCase();

      return switch (parts[1]) {
        case "sensorreading" -> "To specify a sensor to receive information from. Use the command:"
                + "sensorreading -<id>\n"
                + "Where <id> is select sensor id to read from.\n"
                + "To read all sensors from the selected greenhouse, type sensorreading -a\n"
                + "Example: 'sensorreading -2' will read the sensor with id 2 in the greenhouse you're in the menu of."
                + "Likewise, 'sensorreading -a' will read all sensors in the greenhouse you're in the menu of.";

        case "addsensor" -> "To add a sensor to a greenhouse, use the command: "
                + "addsensor -<type>.\n"
                + "Where <type> is the type of sensor (e.g., temperature, humidity), \n"
                + "Example: 'addsensor -temperaturesensor' will add a temperature sensor to the greenhouse"
                +" you're in the menu of.\n"
                + "To add multiple sensors at the same time, separate each type with a space.\n"
                + "Example: 'addsensor -temperaturesensor humiditysensor phsensorsensor'";

        case "addappliances" -> "To add an appliance to a greenhouse, use the command: "
                + "addappliance -<type>.\n"
                + "Where <type> is the type of appliance (e.g., aircondition, lamp), \n"
                + "Example: 'addappliance -lamp' will add a lamp to the greenhouse"
                +" you're in the menu of.\n"
                + "To add multiple appliances at the same time, separate each type with a space.\n"
                + "Example: 'addappliances -lamp humidifier fertilizer'";


        default -> "Did not find that item in 'man'.";
      };
    } catch (ArrayIndexOutOfBoundsException e) {
      return """
              Error: Please include '-' before command.
              Example use: man -sensorreading
              """;
    }
  }

  /**
   * Provides a full manual for the Greenhouse Server commands.
   * @return A string containing the complete manual.
   */
  public static String getManual() {
    return """
      === Greenhouse Server Manual ===

      General Commands:
        manual/help       - Show this manual
        greenhouses       - View and manage greenhouses
        subscribe         - Subscribe to sensor updates
        newgreenhouse     - Create a new greenhouse
        exit              - Disconnect from server

      Greenhouse Management:
        newgreenhouse     - Create a new greenhouse
        listgreenhouses   - List all greenhouses
        <greenhouse_id>   - View details of a specific greenhouse
       
       
      Sensor Management:
       addsensor <type> <id> <greenhouse_id> - Add a sensor to a greenhouse
       sensorreadings -'number' - View sensor readings for this sensor.
       sensorreadings -'a' - View all sensor readings in this greenhouse.
       
        
      Navigation:
        back              - Return to previous menu
      """;
  }
}

