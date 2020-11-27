package p2p.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleSocket implements SocketInterface {

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SimpleSocket(String host, int port)
            throws IOException {
        this(new Socket(host, port));
    }

    public SimpleSocket(Socket socket)
            throws IOException {
        this.socket = socket;
        inputStream = this.socket.getInputStream();
        outputStream = this.socket.getOutputStream();
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    public int read() throws IOException {
        return inputStream.read();
    }

    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }

    public void write(byte[] b) throws IOException {
        outputStream.write(b);
        outputStream.flush();
    }

}
