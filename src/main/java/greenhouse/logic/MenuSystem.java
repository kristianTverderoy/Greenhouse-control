package greenhouse.logic;

import greenhouse.util.CommandProcessor;
import greenhouse.entities.appliances.ApplianceNotAddedToGreenHouseException;
import greenhouse.entities.sensors.SensorNotAddedToGreenHouseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

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
   * @param server      the TCP server instance
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
    writer.write(server.encryptMessage("Available commands: greenhouses, subscribe, help, exit"));
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
    writer.write(server.encryptMessage("\nMenu:"));
    writer.newLine();
    writer.write(server.encryptMessage("Commands: greenhouses | saveserverstate | help | exit"));
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
        writer.write(server.encryptMessage("\nGreenhouses:"));
        writer.newLine();
        writer.write(server.encryptMessage("Available greenhouses:\n" + getGreenhouseList()));
        writer.newLine();
        writer.write(server.encryptMessage("type 'help' for commands."));
        writer.newLine();
        writer.flush();
        hasShownMenu = true;
      }

      String input = server.decryptMessage(reader.readLine());

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
          writer.write(server.encryptMessage("Type the greenhouse ID to view details. E.g., '0' for Greenhouse 0."));
          writer.newLine();
          writer.write(server.encryptMessage("'newgreenhouse' - Create a new greenhouse."));
          writer.newLine();
          writer.write(server.encryptMessage("'back' - Return to the previous menu."));
          writer.newLine();
          writer.write(server.encryptMessage("'listGreenhouses' - Show the list of greenhouses."));
          writer.newLine();
          writer.flush();
        }

        case "newgreenhouse" -> {
          server.createNewGreenhouse();
          writer.write(server.encryptMessage("New greenhouse created."));
          writer.newLine();
          writer.flush();
        }

        case "listgreenhouses" -> {
          writer.write(server.encryptMessage(getGreenhouseList()));
          writer.newLine();
          writer.flush();
        }

        default -> {
          try {
            int id = Integer.parseInt(input.trim());
            handleGreenhouseDetails(id, reader, writer);
            hasShownMenu = false;
          } catch (NumberFormatException e) {
            writer.write(server.encryptMessage("Invalid command. Try again. Type 'help' for commands."));
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
   * @param id     the ID of the greenhouse to display details for
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
      writer.write(server.encryptMessage("Greenhouse not found."));
      writer.newLine();
      writer.flush();
      return;
    }

    boolean hasShownMenu = false;

    while (server.isOn()) {
      if (!hasShownMenu) {
        writer.write(server.encryptMessage("\nGreenhouse " + id));
        writer.newLine();
        writer.write(server.encryptMessage("Commands: 'help' | 'addsensor' | 'sensorreading' | "
                + "'addappliance' | 'appliance' | 'monitor' | 'newtemptarget' | 'newhumiditytarget' | 'back'"));
        writer.newLine();
        writer.flush();
        hasShownMenu = true;
      }
      String input = server.decryptMessage(reader.readLine());
      if (input == null) {
        input = "back";
      } else {
        input = input.toLowerCase().trim();
      }
      if (input.startsWith("addsensor")) {
        try {
          server.addSensorsToGreenhouse(input + " -" + id);
          writer.write(server.encryptMessage("Sensor(s) added successfully."));
          writer.newLine();
          writer.flush();
        } catch (SensorNotAddedToGreenHouseException | IOException e) {
          writer.write(server.encryptMessage("Could not add sensor. Try 'man -addsensor' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to add sensor to."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("removeappliance")) {
        try {
          server.removeApplianceFromGreenhouse(input + " -" + id);
          writer.write(server.encryptMessage("Appliance removed successfully."));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not remove appliance. Try 'man -removeappliance' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to remove appliance from."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid appliance ID provided for removal. Try 'man -removeappliance' for help."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("removesensor")) {
        try {
          server.removeSensorFromGreenhouse(input + " -" + id);
          writer.write(server.encryptMessage("Sensor removed successfully."));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not remove sensor. Try 'man -removesensor' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to remove sensor from."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid sensor ID provided for removal. Try 'man -removesensor' for help."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("sensorreading")) {
        try {
          writer.write(server.encryptMessage(server.handleSensorReadingRequest(input + " -" + id)));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not process sensor reading request. Try 'man -sensorreading' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to read sensor from."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid sensor ID provided for reading. Try 'man -sensorreading' for help."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("addappliance")) {
        try {
          server.addAppliancesToGreenhouse(input + " -" + id);
          writer.write(server.encryptMessage("Appliance(s) added successfully."));
          writer.newLine();
          writer.flush();
        } catch (ApplianceNotAddedToGreenHouseException | IOException e) {
          writer.write(server.encryptMessage("Could not add appliance. Try 'man -addappliance' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to add appliance to."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("appliancereading")) {
        try {
          writer.write(server.encryptMessage(server.handleApplianceReadingRequest(input + " -" + id)));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not process appliance reading request. Try 'man -appliancereading' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to read appliance from."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid appliance ID provided for reading. Try 'man -appliancereading' for help."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("toggleappliance")) {
        try {
          server.toggleAppliance(input + " -" + id);
          writer.write(server.encryptMessage("Appliance toggled successfully."));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not process appliance toggle request. Try 'man -toggleappliance' for help."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to toggle appliance in."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid appliance ID provided for toggling. Try 'man -toggleappliance' for help."));
          writer.newLine();
          writer.flush();
        }
} else if (input.startsWith("newtemptarget")) {
        try {
          server.updateGreenhouseTempTarget(input + " -" + id);
          writer.write(server.encryptMessage("Temperature target updated successfully."));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not process new temperature target request."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to set temperature target in."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid temperature target provided. Try man -newtemptarget' for help."));
          writer.newLine();
          writer.flush();
        }
      } else if (input.startsWith("newhumiditytarget")) {
        try {
          server.updateGreenhouseHumidityTarget(input + " -" + id);
          writer.write(server.encryptMessage("Humidity target updated successfully."));
          writer.newLine();
          writer.flush();
        } catch (IOException e) {
          writer.write(server.encryptMessage("Could not process new humidity target request."));
          writer.newLine();
          writer.flush();
        } catch (NoExistingGreenHouseException e) {
          writer.write(server.encryptMessage("Could not find greenhouse to set humidity target in."));
          writer.newLine();
          writer.flush();
        } catch (IllegalArgumentException e) {
          writer.write(server.encryptMessage("Invalid humidity target provided. Try man -newhumiditytarget' for help."));
          writer.newLine();
          writer.flush();
        }      }else if (input.startsWith("man")) {
        String manualResponse = CommandProcessor.handleManualRequest(input);
        writer.write(server.encryptMessage(manualResponse));
        writer.newLine();
        writer.flush();
      }

      switch (input) {
        case "help" -> {
          writer.write(server.encryptMessage("Commands:"));
          writer.newLine();
          writer.write(server.encryptMessage("'addsensor' For further info, use 'man -addsensor'."));
          writer.newLine();
          writer.write(server.encryptMessage("'addappliance' For further info, use 'man -addappliance'."));
          writer.newLine();
          writer.write(server.encryptMessage("'removesensor' For further info use 'man -removesensor'."));
          writer.newLine();
          writer.write(server.encryptMessage("'removeappliance' For further info use 'man -removeappliance'."));
          writer.newLine();
          writer.write(server.encryptMessage("'sensorreading' For further info, use 'man -sensorreading'."));
          writer.newLine();
          writer.write(server.encryptMessage("'appliancereading' For further info, use 'man -appliancereading'."));
          writer.newLine();
          writer.write(server.encryptMessage("'toggleappliance' For further info, use 'man -toggleappliance'."));
          writer.newLine();
          writer.write(server.encryptMessage("'newtemptarget' For further info, use 'man -newtemptarget'."));
          writer.newLine();
          writer.write(server.encryptMessage("'newhumiditytarget' For further info, use 'man -newhumiditytarget'."));
          writer.newLine();
          writer.write(server.encryptMessage("'monitor' - Start monitoring all sensors data in real-time."));
          writer.newLine();
          writer.write(server.encryptMessage("'help' - Show help message."));
          writer.newLine();
          writer.write(server.encryptMessage("'back' - Return to the previous menu."));
          writer.newLine();
          writer.flush();
        }
        case "back" -> {
          return;
        }

        case "monitor" -> handleGreenhouseSensorMonitoring(gh, writer, reader);

      }
    }
  }

  /**
   * Handles the sensor monitoring menu for a specific greenhouse.
   * Allows users to get a continuous flow of sensor data.
   *
   * @param gh     the greenhouse to display details from
   * @param reader the buffered reader to read user input from
   * @param writer the buffered writer to send output to the client
   * @throws IOException if an I/O error occurs during communication
   */
  private void handleGreenhouseSensorMonitoring(GreenHouse gh, BufferedWriter writer, BufferedReader reader) throws IOException {
    writer.write(server.encryptMessage("Sensor monitoring started for Greenhouse " + gh.getID()));
    writer.newLine();
    writer.write(server.encryptMessage("Type 'stop' to stop monitoring."));
    writer.newLine();
    writer.flush();

    server.subscribeClientToGreenhouseUpdates(gh, writer);

    try {
      while (server.isOn()) {
        String input = server.decryptMessage(reader.readLine());
        if (input == null) {
          break;
        }
        if (input.toLowerCase().trim().equals("stop") || input.toLowerCase().trim().equals("back")) {
          writer.write(server.encryptMessage("Sensor monitoring stopped."));
          writer.newLine();
          writer.flush();
          break;
        }
      }
    } finally {
      server.unsubscribeClientFromGreenhouseUpdates(writer);
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
