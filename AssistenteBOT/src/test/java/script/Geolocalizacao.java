package script;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Geolocalizacao {
    private static final String ACCESS_KEY = "b147911bac20143b2e1a694f589e06d0\n"; // Substitua pela chave da API do ipstack

    // Método para obter o IP local
    public static String getLocalIP() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress(); // Retorna o IP local
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "IP local não encontrado";
        }
    }

    // Método para obter o IP público
    public static String getPublicIP() {
        try {
            // Fazendo requisição HTTP para o ipify e obtendo o IP público
            URL url = new URL("https://api.ipify.org");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString(); // Retorna o IP público
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao obter IP público";
        }
    }

    // Método para consultar a geolocalização usando o ipstack
    public static String getGeolocation(String ip) {
        try {
            // URL para consultar a API do ipstack
            String urlString = "http://api.ipstack.com/" + ip + "?access_key=" + ACCESS_KEY;
            URL url = new URL(urlString);

            // Fazendo a requisição HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lendo a resposta da API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Retorna a resposta da API
            return response.toString(); // A resposta estará em JSON
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao obter geolocalização";
        }
    }

    public static void main(String[] args) {
        // Obtendo o IP local
        String localIP = getLocalIP();
        System.out.println("IP Local: " + localIP);

        // Obtendo o IP público
        String publicIP = getPublicIP();
        System.out.println("IP Público: " + publicIP);

        // Consultando a geolocalização do IP público
        String result = getGeolocation(publicIP);
        System.out.println("Geolocalização do IP Público: " + result); // Exibe a resposta em JSON

        // Caso você queira consultar a geolocalização do IP local, pode usar o método getGeolocation passando o IP local
         String resultLocal = getGeolocation(localIP);
        System.out.println("Geolocalização do IP Local: " + resultLocal);
    }
}
