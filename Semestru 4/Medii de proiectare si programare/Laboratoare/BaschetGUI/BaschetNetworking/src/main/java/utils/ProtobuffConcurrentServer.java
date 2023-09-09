package utils;

import protobuffprotocol.ProtoWorker;
import rpcprotocol.RpcReflexionWorker;
import services.IService;

import java.net.Socket;

public class ProtobuffConcurrentServer extends AbstractConcurrentServer{

    private IService server;

    public ProtobuffConcurrentServer(int port, IService server) {
        super(port);
        this.server = server;
        System.out.println("ProtobufferConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        //RpcReflexionWorker worker=new RpcReflexionWorker(server, client);
        ProtoWorker worker = new ProtoWorker(server, client);

        return new Thread(worker);
//        return null;
    }

    @Override
    public void stop() throws Exception {
//        super.stop();
        System.out.println("Stopping services.");
    }
}
