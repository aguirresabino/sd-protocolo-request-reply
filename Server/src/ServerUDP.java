import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class ServerUDP implements Runnable {

    private final Logger log = Logger.getLogger(ServerUDP.class.getName());

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private byte[] receiveData;
    private byte[] sendData;
    private InetAddress inetAddressClient;
    private int portClient;

    {
        // Definindo o tamanho da mensagem que pode ser recebida.
        receiveData = new byte[1024];
        sendData = new byte[1024];
    }

    public ServerUDP() throws SocketException {
        this.serverSocket = new DatagramSocket(9876);
        this.receivePacket = new DatagramPacket(receiveData, receiveData.length);
    }

    public ServerUDP(InetAddress inetAddressClient, int portClient, byte[] receiveData, DatagramSocket serverSocket) throws SocketException {
        this.serverSocket = serverSocket;
        this.inetAddressClient = inetAddressClient;
        this.portClient = portClient;
        this.receiveData = receiveData;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(DatagramSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public DatagramPacket getReceivePacket() {
        return receivePacket;
    }

    public void setReceivePacket(DatagramPacket receivePacket) {
        this.receivePacket = receivePacket;
    }

    public byte[] getReceiveData() {
        return receiveData;
    }

    public void setReceiveData(byte[] receiveData) {
        this.receiveData = receiveData;
    }

    public byte[] getSendData() {
        return sendData;
    }

    public void setSendData(byte[] sendData) {
        this.sendData = sendData;
    }

    public void receive() throws IOException {
        this.serverSocket.receive(this.receivePacket);
        log.info("[SERVER] Recebendo conexão.");
        String msg = new String(this.receivePacket.getData());
        log.info("[SERVER] Mensagem recebida: " + msg);
        new Thread(new ServerUDP(this.receivePacket.getAddress(), this.receivePacket.getPort(), this.receivePacket.getData(), this.serverSocket)).start();
    }

    @Override
    public void run() {
        log.info("Iniciando nova Thread");
        String sentence = new String(this.receiveData);
        InetAddress IPAddress = this.inetAddressClient;
        int port = this.portClient;
        String capitalizedSentence = sentence.toUpperCase();
        sendData = capitalizedSentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
