package p2p.handlers;

import blockchain.chain.Block;
import blockchain.exceptions.TransactionsSizeException;
import p2p.Node;
import p2p.PeerConnection;
import p2p.PeerMessage;
import p2p.util.Converter;

import java.io.IOException;

public class NewBlockBaseHandler extends BaseHandler {
    public NewBlockBaseHandler(Node node) {
        super(node);
    }

    @Override
    public void handleMessage(PeerConnection peerConnection, PeerMessage msg) {
        Block block;
        try {
            block = (Block) Converter.deserialize(msg.getMsgDataBytes());
            node.getBlockchain().addBlockToBlockchain(block);
        } catch (IOException | TransactionsSizeException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
