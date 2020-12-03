package p2p.util;

import p2p.PeerInfo;
import p2p.RouterInterface;
import p2p.units.Node;

public class Router implements RouterInterface {
    private Node peer;

    public Router(Node peer) {
        this.peer = peer;
    }

    public PeerInfo route(String peerid) {
        for (String key : peer.getPeerKeys())
            if (peer.getPeer(key).getId().equals(peerid))
                return peer.getPeer(peerid);
        return null;
    }
}
