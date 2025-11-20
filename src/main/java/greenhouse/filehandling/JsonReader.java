package greenhouse.filehandling;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading greenhouse data from JSON files using Gson.
 * Only reads from the resources/greenhouses directory.
 * Created using AI.
 */
public class JsonReader {

  private final Gson gson;

  public JsonReader() {
    this.gson = new Gson();
  }

  /**
   * Reads a single greenhouse from a JSON file by ID from resources/greenhouses.
   *
   * @param greenHouseID The ID of the greenhouse to read
   * @return The GreenHouseDTO object
   * @throws IOException If the file cannot be found or read
   */
  public GreenHouseDTO readGreenHouseFile(int greenHouseID) throws IOException {
    String jsonContent = readJsonContent("/greenhouses/greenhouse" + greenHouseID + ".json");
    return gson.fromJson(jsonContent, GreenHouseDTO.class);
  }

  /**
   * Reads all greenhouse files from the resources/greenhouses directory.
   * Tries greenhouse1.json through greenhouse20.json and stops when no more files are found.
   *
   * @return A list of all GreenHouseDTO objects found
   * @throws IOException If there's an error reading the files
   */
  public List<GreenHouseDTO> readAllGreenHouses() throws IOException {
    List<GreenHouseDTO> greenHouses = new ArrayList<>();
    
    // Keep reading greenhouse files starting from 0 until no more are found
    int i = 0;
    while (true) {
      try {
        GreenHouseDTO greenhouse = readGreenHouseFile(i);
        greenHouses.add(greenhouse);
        i++;
      } catch (FileNotFoundException e) {
        // File doesn't exist, stop searching
        break;
      }
    }
    
    return greenHouses;
  }

  /**
   * Helper method to read JSON content from resources.
   *
   * @param resourcePath The resource path to read from
   * @return The JSON content as a string
   * @throws IOException If the resource cannot be found or read
   */
  private String readJsonContent(String resourcePath) throws IOException {
    try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new FileNotFoundException("Could not find file: " + resourcePath);
      }
      return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
  }
}
