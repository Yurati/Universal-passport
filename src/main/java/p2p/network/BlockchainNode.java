package p2p.network;

import p2p.Node;
import p2p.PeerInfo;
import p2p.handlers.AddBaseHandler;
import p2p.handlers.NewBlockBaseHandler;
import p2p.handlers.QuitBaseHandler;
import p2p.handlers.ResponseBaseHandler;
import p2p.util.Router;

public class BlockchainNode extends Node {
    public static final String ADD_PEAR = "ADD";
    public static final String QUIT = "QUIT";
    public static final String NEW_BLOCK = "NEW";
    public static final String REPLY = "RPL";
    public static final String ERROR = "ERR";

    public BlockchainNode(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);

        addRouter(new Router(this));

        addHandler(ADD_PEAR, new AddBaseHandler(this));
        addHandler(QUIT, new QuitBaseHandler(this));
        addHandler(NEW_BLOCK, new NewBlockBaseHandler(this));
        addHandler(REPLY, new ResponseBaseHandler(this));
    }
}
