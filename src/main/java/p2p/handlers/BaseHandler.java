package p2p.handlers;


import p2p.PeerConnection;
import p2p.PeerMessage;
import p2p.units.Node;

public abstract class BaseHandler {
    protected Node node;

    public BaseHandler(Node node) {
        this.node = node;
    }

    abstract public void handleMessage(PeerConnection peerConnection, PeerMessage msg);
}
