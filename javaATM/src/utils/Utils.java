package utils;

import java.io.*;
import java.util.List;
import client.Client;
import client.ClientDAO;
import java.util.Scanner;

public class Utils {
    private static Scanner scanner = new Scanner(System.in);

    public static String getStringInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
        
    }

    public static int getIntInput(String prompt) {
    	System.out.print(prompt);
        int input = scanner.nextInt();
        clearBuffer(); 
        return input;
    }

    public static double getDoubleInput(String prompt) {
    	System.out.print(prompt);
        double input = scanner.nextDouble();
        clearBuffer(); 
        return input;
    }

    public static void clearBuffer() {
        scanner.nextLine(); 
    }

    public static void saveClients(List<Client> clients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("client.txt"))) {
            for (Client client : clients) {
                writer.write(client.getClientId() + "," + client.getName() + "," + client.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("데이터 저장 실패: " + e.getMessage());
        }
    }

    public static void loadClients(ClientDAO clientDAO) {
        try (BufferedReader reader = new BufferedReader(new FileReader("client.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Client client = new Client(data[0], data[1], data[2]);
                    clientDAO.addClient(client);
                }
            }
        } catch (IOException e) {
            System.out.println("데이터 불러오기 실패: " + e.getMessage());
        }
    }
}
