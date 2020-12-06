package p2p.handlers;

import blockchain.chain.Block;
import p2p.PeerConnection;
import p2p.PeerInfo;
import p2p.PeerMessage;
import p2p.units.Node;
import p2p.util.Const;
import p2p.util.Converter;

import java.io.IOException;
import java.util.logging.Logger;

public class AddBaseHandler extends BaseHandler {
    public AddBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {
        Logger.getLogger("AddBaseHandler").info("Adding new peer...");
        PeerInfo peerInfo;
        try {
            peerInfo = (PeerInfo) Converter.deserialize(msg.getMsgDataBytes());
            node.addPeer(peerInfo);
            node.synchronizeBlockchain();
            Logger.getLogger("AddBaseHandler").info("Peer added");
            peerConnection.close();
            sendBlockchain(new PeerConnection(peerInfo));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendBlockchain(PeerConnection peerConnection) {
        Logger.getLogger("AddBaseHandler").info("Sending blockchain...");
        node.getBlockchain().getBlockchain().stream()
                .forEach(block -> sendBlock(peerConnection, block));
        Logger.getLogger("AddBaseHandler").info("Blockchain sended");
    }

    private void sendBlock(PeerConnection peerConnection, Block block) {
        try {
            String msgtype = Const.NEW_BLOCK;
            byte[] msgdata = Converter.serialize(block);
            PeerMessage toSend = new PeerMessage(msgtype, msgdata);
            peerConnection.sendData(toSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
