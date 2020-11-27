package p2p.handlers;


import p2p.Node;
import p2p.PeerConnection;
import p2p.PeerMessage;

public abstract class BaseHandler {
    private Node node;

    public BaseHandler(Node node) {
        this.node = node;
    }

    abstract public void handleMessage(PeerConnection peerConnection, PeerMessage msg);
}
