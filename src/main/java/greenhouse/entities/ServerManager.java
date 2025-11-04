package greenhouse.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {
    private static ServerManager instance = null;
    private final Map<String, TCPServer> servers = new ConcurrentHashMap<>(); //Holds all servers by name.


    private ServerManager(){

    }

    public void addServer(String name, TCPServer server) {
        servers.put(name, server);
    }

    public TCPServer getServer(String name) {
        return servers.get(name);
    }

    public static synchronized ServerManager getInstance(){
        if (instance == null){
            instance = new ServerManager();
        }
        return instance;
    }
}
