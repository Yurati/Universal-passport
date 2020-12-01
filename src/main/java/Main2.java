import blockchain.chain.Block;
import p2p.PeerInfo;
import p2p.network.BlockchainNode;

import java.util.LinkedList;

public class Main2 {
    public static void main(String[] args) {
        PeerInfo peerInfo = new PeerInfo(9091);
        BlockchainNode blockchainNode = new BlockchainNode(10, peerInfo);
        new Thread(new Runnable() {
            @Override
            public void run() {
                blockchainNode.mainLoop();
            }
        }).start();
        PeerInfo peerInfo2 = new PeerInfo(9090);
        blockchainNode.getBlockchain().mineBlock(new LinkedList<>());
        blockchainNode.connectAndSendBlock(peerInfo2, BlockchainNode.NEW_BLOCK, blockchainNode.getBlockchain().getBlockchain().get(blockchainNode.getBlockchain().getBlockchain().size() - 1), true);
    }
}
