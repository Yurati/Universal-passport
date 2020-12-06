package p2p;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PeerInfo implements Serializable {
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
