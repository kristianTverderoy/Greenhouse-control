package greenhouse.entities;

import greenhouse.util.CommandProcessor;
import greenhouse.entities.appliances.ApplianceNotAddedToGreenHouseException;
import greenhouse.entities.sensors.SensorNotAddedToGreenHouseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

//TODO: Klasse skal i util pakke, eller ui pakke?

/**
 * Manages the menu system for the greenhouse TCP server.
 * Handles user interaction through different menu levels including
 * start menu, greenhouse listing, and greenhouse details.
 */
public class MenuSystem {
  private final List<GreenHouse> greenHouses;
  private final TCPServer server;

  /**
   * Creates a new MenuSystem instance.
   *
   * @param greenHouses the list of greenhouses to manage
   * @param server the TCP server instance
   */
  public MenuSystem(List<GreenHouse> greenHouses, TCPServer server) {
    this.greenHouses = greenHouses;
    this.server = server;
  }

  /**
   * Sends a help message with available commands to the client.
   *
   * @param writer the buffered writer to send the message through
   * @throws IOException if an I/O error occurs while writing
   */
  public void helpMessage(BufferedWriter writer) throws IOException {
    writer.write("Available commands: greenhouses, subscribe, help, exit");
    writer.newLine();
    writer.flush();
  }

  /**
   * Displays the start menu with available commands.
   *
   * @param writer the buffered writer to send the menu through
   * @throws IOException if an I/O error occurs while writing
   */
  public void showStartMenu(BufferedWriter writer) throws IOException {
    writer.write("\nMenu:");
    writer.newLine();
    writer.write("Commands: greenhouses | newgreenhouse | help | exit");
    writer.newLine();
    writer.flush();
  }

  /**
   * Handles the greenhouses menu where users can list, create, and select greenhouses.
   * Continuously reads user input until the user types 'back' or the server stops.
   *
   * @param reader the buffered reader to read user input from
   * @param writer the buffered writer to send output to the client
   * @throws IOException if an I/O error occurs during communication
   */
  public void handleGreenhousesMenu(BufferedReader reader, BufferedWriter writer) throws IOException {
    boolean hasShownMenu = false;
    while (server.isOn()) {
      if (!hasShownMenu) {
        writer.write("\nGreenhouses:");
        writer.newLine();
        writer.write("Available greenhouses:\n" + getGreenhouseList());
        writer.newLine();
        writer.write("type 'help' for commands.");
        writer.newLine();
        writer.flush();
        hasShownMenu = true;
      }

      String input = reader.readLine();
      if (input != null) {
        input = input.toLowerCase().trim();
      } else {
        input = "back";
      }

      switch (input) {
        case "back" -> {
          hasShownMenu = false;
          return;
        }

        case "help" -> {
          writer.write("Type the greenhouse ID to view details. E.g., '0' for Greenhouse 0.");
          writer.newLine();
          writer.write("'newgreenhouse' - Create a new greenhouse.");
          writer.newLine();
          writer.write("'back' - Return to the previous menu.");
          writer.newLine();
          writer.write("'listGreenhouses' - Show the list of greenhouses.");
          writer.newLine();
          writer.flush();
        }

        case "newgreenhouse" -> {
          server.createNewGreenhouse();
          writer.write("New greenhouse created.");
          writer.newLine();
          writer.flush();
        }

        case "listgreenhouses" -> {
          writer.write(getGreenhouseList());
          writer.newLine();
          writer.flush();
        }

        default -> {
          try {
            int id = Integer.parseInt(input.trim());
            handleGreenhouseDetails(id, reader, writer);
            hasShownMenu = false;
          } catch (NumberFormatException e) {
            writer.write("Invalid command. Try again. Type 'help' for commands.");
            writer.newLine();
            writer.flush();
          }
        }
      }
    }
  }

  /**
   * Handles the details menu for a specific greenhouse.
   * Allows users to view sensors, add sensors, and read sensor data.
   *
   * @param id the ID of the greenhouse to display details for
   * @param reader the buffered reader to read user input from
   * @param writer the buffered writer to send output to the client
   * @throws IOException if an I/O error occurs during communication
   */
  private void handleGreenhouseDetails(int id, BufferedReader reader, BufferedWriter writer) throws IOException {
    GreenHouse gh = greenHouses.stream()
            .filter(g -> g.getID() == id)
            .findFirst()
            .orElse(null);

    if (gh == null) {
      writer.write("Greenhouse not found.");
      writer.newLine();
      writer.flush();
      return;
    }

    boolean hasShownMenu = false;

    while (server.isOn()) {
      if (!hasShownMenu) {
        writer.write("\nGreenhouse " + id);
        writer.newLine();
        writer.write("Sensors: " + gh.getAllSensorsInformation());
        writer.newLine();
        writer.write("Commands: 'help' | 'sensors' | 'back' | 'addsensor' | 'sensorreading' | "
                + "'addappliance' | 'appliancereading");
        writer.newLine();
        writer.flush();
        hasShownMenu = true;
      }
      String input = reader.readLine();
      if (input == null) {
        input = "back";
      } else {
        input = input.toLowerCase().trim();
      }
      if (input.startsWith("addsensor")) {
        try {
          server.addSensorsToGreenhouse(input + " -" + id);
        } catch (SensorNotAddedToGreenHouseException | IOException e) {
          writer.write("Could not add sensor. Try 'man -addsensor' for help.");
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write("Could not find greenhouse to add sensor to.");
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("sensorreading")) {
        try {
          writer.write(server.handleSensorReadingRequest(input + " -" + id));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write("Could not process sensor reading request. Try 'man -sensorreading' for help.");
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write("Could not find greenhouse to read sensor from.");
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write("Invalid sensor ID provided for reading. Try 'man -sensorreading' for help.");
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("addappliance")) {
        try {
          server.addAppliancesToGreenhouse(input + " -" + id);
        } catch (ApplianceNotAddedToGreenHouseException | IOException e) {
          writer.write("Could not add appliance. Try 'man -addappliance' for help.");
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write("Could not find greenhouse to add appliance to.");
          writer.newLine();
          writer.flush();
        }

      } else if (input.startsWith("man")) {
        String manualResponse = CommandProcessor.handleManualRequest(input);
        writer.write(manualResponse);
        writer.newLine();
        writer.flush();
      }

      switch (input) {
        case "help" -> {
          writer.write("Commands:");
          writer.newLine();
          writer.write("'sensors' - List all sensors in this greenhouse.");
          writer.newLine();
          writer.write("'back' - Return to the previous menu.");
          writer.newLine();
          writer.flush();
        }
        case "back" -> {
          return;
        }
        case "sensors" -> {
          writer.write(gh.getAllSensorsInformation());
          writer.newLine();
          writer.flush();
        }
        case "addsensor" -> {

        }
      }
    }
  }

  /**
   * Returns the name of all the green houses as one string
   *
   * @return name of all the green houses as one string
   */
  private String getGreenhouseList() {
    if (greenHouses.isEmpty()) {
      return "No greenhouses available. Use 'newgreenhouse' to create one.";
    }
    StringBuilder sb = new StringBuilder();
    for (GreenHouse gh : greenHouses) {
      sb.append("Greenhouse ").append(gh.getID()).append("\n");
    }
    return sb.toString();
  }


}
