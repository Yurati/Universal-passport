package p2p.handlers;

import p2p.Node;
import p2p.PeerConnection;
import p2p.PeerMessage;

public class NewBlockBaseHandler extends BaseHandler {
    public NewBlockBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {

    }
}
