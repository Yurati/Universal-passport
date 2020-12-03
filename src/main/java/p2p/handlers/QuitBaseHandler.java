package p2p.handlers;

import p2p.PeerConnection;
import p2p.PeerInfo;
import p2p.PeerMessage;
import p2p.units.Node;
import p2p.util.Converter;

import java.io.IOException;

public class QuitBaseHandler extends BaseHandler {
    public QuitBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {
        PeerInfo peerInfo;
        try {
            peerInfo = (PeerInfo) Converter.deserialize(msg.getMsgDataBytes());
            node.removePeer(peerInfo.getId());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
