package greenhouse.entities;

import java.util.HashMap;
import java.util.Map;

public class ServerManager {
    private static ServerManager instance = null;
    private final Map<String, tcpServer> servers = new HashMap<>(); //Holds all servers by name.


    private ServerManager(){

    }

    public void addServer(String name, tcpServer server) {
        servers.put(name, server);
    }

    public tcpServer getServer(String name) {
        return servers.get(name);
    }

    public static synchronized ServerManager getInstance(){
        if (instance == null){
            instance = new ServerManager();
        }
        return instance;
    }
}
