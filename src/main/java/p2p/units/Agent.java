package p2p.units;

import blockchain.chain.Block;
import blockchain.data.rights.AccessRight;
import blockchain.exceptions.InvalidRightsException;
import blockchain.exceptions.TransactionsSizeException;
import blockchain.transactions.Transaction;
import blockchain.utils.KeyUtils;
import lombok.Getter;
import p2p.PeerConnection;
import p2p.PeerInfo;
import p2p.PeerMessage;
import p2p.handlers.AddBaseHandler;
import p2p.handlers.QuitBaseHandler;
import p2p.handlers.ResponseBaseHandler;
import p2p.util.Const;
import p2p.util.Converter;
import p2p.util.LoggerUtil;
import p2p.util.Router;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public abstract class Agent extends Node {
    @Getter
    protected HashSet<AccessRight> accessRightHashSet;
    @Getter
    protected LinkedList<Transaction> transactionLinkedList;
    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public Agent(int maxPeers, PeerInfo peerInfo) {
        super(maxPeers, peerInfo);

        addRouter(new Router(this));
        addHandler(Const.QUIT, new QuitBaseHandler(this));
        addHandler(Const.ADD_PEAR, new AddBaseHandler(this));
        addHandler(Const.REPLY, new ResponseBaseHandler(this));

        accessRightHashSet = new HashSet<>();
        transactionLinkedList = new LinkedList<>();
        accessRightHashSet.add(AccessRight.READ);
        KeyPair keyPair = KeyUtils.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public void addTransactionsToBlockchain() throws TransactionsSizeException {
        getBlockchain().mineBlock(transactionLinkedList);
        transactionLinkedList.clear();
    }

    //TODO: impl synchronization between agents
    public void synchronizeBlockchain() {

    }

    public List<PeerMessage> connectAndSend(PeerInfo pd, String msgtype,
                                            String msgdata, boolean waitreply) throws InvalidRightsException {
        List<PeerMessage> msgreply = new ArrayList<>();
        try {
            PeerConnection peerConnection = new PeerConnection(pd);
            PeerMessage toSend = new PeerMessage(msgtype, msgdata);
            peerConnection.sendData(toSend);
            LoggerUtil.getLogger().fine("Sent " + toSend + "/" + peerConnection);

            if (waitreply) {
                PeerMessage onereply = peerConnection.recvData();
                while (onereply != null) {
                    msgreply.add(onereply);
                    LoggerUtil.getLogger().fine("Got reply " + onereply);
                    onereply = peerConnection.recvData();
                }
            }

            peerConnection.close();
        } catch (IOException e) {
            LoggerUtil.getLogger().warning("Error: " + e + "/"
                    + pd + "/" + msgtype);
        }
        return msgreply;
    }

    public List<PeerMessage> connectAndSendBlock(PeerInfo pd,
                                                 Block block, boolean waitreply) throws InvalidRightsException {
        String msgtype = Const.NEW_BLOCK;
        validateRights(msgtype);
        List<PeerMessage> msgreply = new ArrayList<>();
        try {
            PeerConnection peerConnection = new PeerConnection(pd);
            PeerMessage toSend = new PeerMessage(msgtype, Converter.serialize(block));
            peerConnection.sendData(toSend);
            LoggerUtil.getLogger().info("Sent " + toSend + "/" + peerConnection);

            if (waitreply) {
                PeerMessage onereply = peerConnection.recvData();
                while (onereply != null) {
                    msgreply.add(onereply);
                    LoggerUtil.getLogger().info("Got reply " + onereply);
                    onereply = peerConnection.recvData();
                }
            }

            peerConnection.close();
        } catch (IOException e) {
            LoggerUtil.getLogger().warning("Error: " + e + "/"
                    + pd + "/" + msgtype);
        }
        return msgreply;
    }

    public List<PeerMessage> sendToPeer(String peerid, String msgtype,
                                        String msgdata, boolean waitreply) throws InvalidRightsException {
        PeerInfo pd = null;
        if (router != null)
            pd = router.route(peerid);
        if (pd == null) {
            LoggerUtil.getLogger().severe(
                    String.format("Unable to route %s to %s", msgtype, peerid));
            return new ArrayList<>();
        }

        return connectAndSend(pd, msgtype, msgdata, waitreply);
    }

    private void validateRights(String msgType) throws InvalidRightsException {
        String errorMsg = "Agent doest not have sufficient rights!";
        if (hasRightToAdd(msgType)) {
            throw new InvalidRightsException(errorMsg);
        }
    }

    private boolean hasRightToAdd(String msgType) {
        return msgType.equals(Const.NEW_BLOCK) && !accessRightHashSet.contains(AccessRight.ADD);
    }
}
