package p2p.handlers;

import p2p.Node;
import p2p.PeerConnection;
import p2p.PeerMessage;

public class QuitBaseHandler extends BaseHandler {
    public QuitBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {

    }
}
