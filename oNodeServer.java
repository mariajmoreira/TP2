import java.io.*;
import java.net.*;
import java.util.*;
 
/**
 * This program demonstrates how to implement a UDP server program.
 *
 *
 * @author www.codejava.net
 * 
 *  //java oNodeServer.java quotes.txt 8080
 */
public class oNodeServer {
    private DatagramSocket socket;
    private List<String> listoNodes = new ArrayList<String>();
    private Random random;
 
    public oNodeServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
        random = new Random();
    }
 
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Syntax: oNodeServer <file> <port>");
            return;
        }
 
        String quoteFile = args[0];
        int port = Integer.parseInt(args[1]);
 
        try {
            oNodeServer server = new oNodeServer(port);
            server.loadoNodesFromFile(quoteFile);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
 
    private void service() throws IOException {
        while (true) {
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);
 
            String quote = getRandomoNode();
            byte[] buffer = quote.getBytes();
 
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
 
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
        }
    }
 
    private void loadoNodesFromFile(String quoteFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(quoteFile));
        String aoNode;
 
        while ((aoNode = reader.readLine()) != null) {
            listoNodes.add(aoNode);
        }
 
        reader.close();
    }
 
    private String getRandomoNode() {
        int randomIndex = random.nextInt(listoNodes.size());
        String randomoNode = listoNodes.get(randomIndex);
        return randomoNode;
    }
}
