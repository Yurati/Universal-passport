package p2p;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeerInfo {
    private String id;
    private String host;
    private int port;

    public PeerInfo(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public PeerInfo(String host, int port) {
        this(host + ":" + port, host, port);
    }

    public PeerInfo(int port) {
        this(null, port);
    }
}
