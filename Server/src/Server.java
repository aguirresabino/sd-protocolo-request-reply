import java.io.IOException;
import java.util.logging.Logger;

public class Server {
    private static final Logger log = java.util.logging.Logger.getLogger(Server.class.getName());

    public static void main(String args[]) {
        try {
            log.info("[SERVER] Iniciando servidor UDP");
            ServerUDP serverUDP = new ServerUDP();
            for(;;) {
                serverUDP.receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
