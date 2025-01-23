package client;

import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private List<Client> clients;

    public ClientDAO() {
        clients = new ArrayList<>();
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public Client getClient(String clientId) {
        for (Client client : clients) {
            if (client.getClientId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    public boolean removeClient(String clientId) {
        return clients.removeIf(client -> client.getClientId().equals(clientId));
    }

    public List<Client> getClients() {
        return clients;
    }
}
