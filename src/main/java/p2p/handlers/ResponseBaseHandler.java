package p2p.handlers;

import p2p.PeerConnection;
import p2p.PeerMessage;
import p2p.units.Node;

public class ResponseBaseHandler extends BaseHandler {
    public ResponseBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {

    }
}
