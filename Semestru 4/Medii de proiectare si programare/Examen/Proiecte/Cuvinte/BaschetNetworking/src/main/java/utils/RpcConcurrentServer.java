package utils;

import rpcprotocol.RpcReflexionWorker;
import services.IService;

import javax.swing.table.TableRowSorter;
import java.net.Socket;

public class RpcConcurrentServer extends AbstractConcurrentServer{

    private IService server;

    public RpcConcurrentServer(int port, IService server) {
        super(port);
        this.server = server;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        RpcReflexionWorker worker=new RpcReflexionWorker(server, client);

        Thread thread = new Thread(worker);
        return thread;
        // return null;
    }

    @Override
    public void stop() throws Exception {
//        super.stop();
        System.out.println("Stopping services.");
    }
}
