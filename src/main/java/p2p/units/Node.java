package p2p.units;

import blockchain.chain.Blockchain;
import lombok.Getter;
import p2p.PeerConnection;
import p2p.PeerInfo;
import p2p.PeerMessage;
import p2p.RouterInterface;
import p2p.handlers.BaseHandler;
import p2p.handlers.NewBlockBaseHandler;
import p2p.socket.SimpleSocket;
import p2p.socket.SocketInterface;
import p2p.util.Const;
import p2p.util.LoggerUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Set;

public abstract class Node {
    private static final int SOCKET_TIMEOUT = 2000; // milliseconds
    private PeerInfo peerInfo;
    private int maxPeers;
    private Hashtable<String, PeerInfo> peers;
    private Hashtable<String, BaseHandler> handlers;
    protected RouterInterface router;
    @Getter
    private Blockchain blockchain;
    private boolean shutdown;

    public Node(int maxPeers, PeerInfo info) {
        if (info.getHost() == null)
            info.setHost(getHostname());
        if (info.getId() == null)
            info.setId(info.getHost() + ":" + info.getPort());

        blockchain = new Blockchain();

        this.peerInfo = info;
        this.maxPeers = maxPeers;

        this.peers = new Hashtable<>();
        this.handlers = new Hashtable<>();
        this.router = null;

        this.shutdown = false;
        addHandler(Const.NEW_BLOCK, new NewBlockBaseHandler(this));
    }

    public Node(int port) {
        this(0, new PeerInfo(port));
    }

    //TODO: impl synchronization between agents
    public void synchronizeBlockchain() {

    }

    /*
     * Attempt to determine the name or IP address of the machine
     * this node is running on.
     */
    private String getHostname() {
        String host = "";
        try {
            Socket s = new Socket("www.google.com", 80);
            host = s.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            LoggerUtil.getLogger().warning("Could not determine host: " + e);
        } catch (IOException e) {
            LoggerUtil.getLogger().warning(e.toString());
        }

        LoggerUtil.getLogger().config("Determined host: " + host);
        return host;
    }

    public ServerSocket makeServerSocket(int port) throws IOException {
        return makeServerSocket(port, 5);
    }

    public ServerSocket makeServerSocket(int port, int backlog)
            throws IOException {
        ServerSocket s = new ServerSocket(port, backlog, InetAddress.getByName("localhost"));
        s.setReuseAddress(true);
        return s;
    }

    public void mainLoop() {
        try {
            ServerSocket s = makeServerSocket(peerInfo.getPort());
            s.setSoTimeout(SOCKET_TIMEOUT);

            while (!shutdown) {
                LoggerUtil.getLogger().info("Listening...");
                try {
                    Socket clientsock = s.accept();
                    clientsock.setSoTimeout(0);

                    PeerHandler ph = new PeerHandler(clientsock);
                    ph.start();
                    LoggerUtil.getLogger().info("Successfully listened");
                } catch (SocketTimeoutException e) {
                    LoggerUtil.getLogger().fine("" + e);
                    continue;
                }
            }
            s.close();
        } catch (SocketException e) {
            LoggerUtil.getLogger().severe("Stopping main loop (SocketExc): " + e);
        } catch (IOException e) {
            LoggerUtil.getLogger().severe("Stopping main loop (IOExc): " + e);
        }
        shutdown = true;
    }

    public void addHandler(String msgtype, BaseHandler handler) {
        handlers.put(msgtype, handler);
    }

    public void addRouter(RouterInterface router) {
        this.router = router;
    }

    public boolean addPeer(PeerInfo pd) {
        return addPeer(pd.getId(), pd);
    }

    public boolean addPeer(String key, PeerInfo pd) {
        if ((maxPeers == 0 || peers.size() < maxPeers) &&
                !peers.containsKey(key)) {
            peers.put(key, pd);
            return true;
        }
        return false;
    }

    public PeerInfo getPeer(String key) {
        return peers.get(key);
    }

    public PeerInfo removePeer(String key) {
        return peers.remove(key);
    }

    public Set<String> getPeerKeys() {
        return peers.keySet();
    }

    public int getNumberOfPeers() {
        return peers.size();
    }

    public int getMaxPeers() {
        return maxPeers;
    }

    public boolean maxPeersReached() {
        return maxPeers > 0 && peers.size() == maxPeers;
    }

    public String getId() {
        return peerInfo.getId();
    }

    public String getHost() {
        return peerInfo.getHost();
    }

    public int getPort() {
        return peerInfo.getPort();
    }

    private class PeerHandler extends Thread {
        private final SocketInterface socket;

        public PeerHandler(Socket socket) throws IOException {
            this.socket = new SimpleSocket(socket);
        }

        public void run() {
            LoggerUtil.getLogger().fine("New PeerHandler: " + socket);

            PeerConnection peerConnection = new PeerConnection(null, socket);
            PeerMessage peerMessage = peerConnection.recvData();
            LoggerUtil.getLogger().info("Handling: " + peerMessage);
            handlers
                    .get(peerMessage.getMsgType())
                    .handleMessage(peerConnection, peerMessage);
            LoggerUtil.getLogger().info("Disconnecting incoming: " + peerConnection);
            peerConnection.close();
        }
    }
}
