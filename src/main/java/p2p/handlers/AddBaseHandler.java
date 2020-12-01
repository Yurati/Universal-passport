package p2p.handlers;

import p2p.PeerConnection;
import p2p.PeerInfo;
import p2p.PeerMessage;
import p2p.units.Node;
import p2p.util.Converter;

import java.io.IOException;
import java.util.logging.Logger;

public class AddBaseHandler extends BaseHandler {
    public AddBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {
        Logger.getLogger("AddBaseHandler").info("Adding new block...");
        PeerInfo peerInfo;
        try {
            peerInfo = (PeerInfo) Converter.deserialize(msg.getMsgDataBytes());
            node.addPeer(peerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
