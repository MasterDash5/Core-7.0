package xyz.dashnetwork.core.bungee.pain.listener.listeners;

import xyz.dashnetwork.core.bungee.pain.channel.Channel;
import xyz.dashnetwork.core.bungee.pain.listener.Listener;
import xyz.dashnetwork.core.utils.SignatureUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerListener extends Listener {

    private ServerSocket server;

    public ServerListener(short port) {
        try {
            server = new ServerSocket(port);
        } catch (IOException exception) {}
    }

    @Override
    public void stop() {
        try {
            server.close();
        } catch (IOException exception) {}

        super.stop();
    }

    @Override
    public void listen() {
        try {
            new Handler(server.accept());
        } catch (SocketException exception) {
            // Shutdown
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public class Handler implements Runnable {

        private Socket client;

        public Handler(Socket client) {
            this.client = client;

            new Thread(this, "Pain - ServerListener:Handler").start();
        }

        @Override
        public void run() {
            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                DataInputStream input = new DataInputStream(client.getInputStream());

                long date = input.readLong();
                String name = input.readUTF();
                byte[] signature = new byte[256];

                input.readFully(signature);

                // Verify
                if (System.currentTimeMillis() - date < 30000 && SignatureUtils.verify((date + name).getBytes(), signature)) {
                    Channel channel = Channel.getChannel(name);

                    if (channel != null)
                        channel.onChannel(input, output);
                }

                output.close();
                input.close();
                client.close();
            } catch (IOException exception) {}
        }

    }

}
