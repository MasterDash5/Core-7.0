package dashnetwork.core.bungee.pain;

import dashnetwork.core.bungee.pain.channel.Channel;
import dashnetwork.core.bungee.pain.channel.channels.ChannelStatus;

import javax.net.ServerSocketFactory;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;

// Packets Addressed for Interserver Networking
public class Pain implements Runnable {

    private ServerSocket server;
    private Thread thread;
    private boolean running;

    public Pain() {
        try {
            server = ServerSocketFactory.getDefault().createServerSocket(25454);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        thread = new Thread(this, "Pain Server");
        running = false;

        new ChannelStatus();
    }

    public void start() {
        if (!running) {
            thread.start();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            thread.interrupt();
            running = false;
        }
    }

    public StackTraceElement[] getStacktrace() {
        return thread.getStackTrace();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = server.accept();

                new Thread(() -> {
                    try {
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        DataInputStream input = new DataInputStream(socket.getInputStream());

                        long date = input.readLong();
                        String name = input.readUTF();
                        byte[] signature = new byte[256];

                        input.readFully(signature);

                        // Verify
                        if (System.currentTimeMillis() - date < 15000 && verify((date + name).getBytes(), signature)) {
                            Channel channel = Channel.getChannel(name);

                            if (channel != null)
                                channel.onChannel(input, output);
                        }

                        output.close();
                        input.close();
                    } catch (IOException exception) {}
                }).start();
            } catch (IOException exception) {}
        }
    }

    private boolean verify(byte[] data, byte[] signed) {
        try {
            ClassLoader loader = Pain.class.getClassLoader();
            File file = new File(loader.getResource("keys/public.key").toURI());

            ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            BigInteger modulus = (BigInteger) stream.readObject();
            BigInteger exponent = (BigInteger) stream.readObject();

            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey key = factory.generatePublic(new RSAPublicKeySpec(modulus, exponent));

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(key);
            signature.update(data);

            return signature.verify(signed);
        } catch (Exception exception) {
            return false;
        }
    }

}
