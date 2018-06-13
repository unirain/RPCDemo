package center;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/********************************************************************************
 *
 * Title: 服务中心实现
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/6/13
 *
 *******************************************************************************/
public class ServiceCenterImpl implements ServiceCenter {
    private ExecutorService executors=Executors.newSingleThreadExecutor();
    private Map<String,Class> map=new HashMap<>(6);
    private boolean isRunning=false;
    private int port;

    public ServiceCenterImpl(int port) {
        this.port = port;
    }

    @Override
    public void stop() {
        isRunning=false;
        executors.shutdown();

    }

    @Override
    public void start() throws Exception {
        //使用socket接收
        ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("服务已开启**********");
        while (true){
            executors.execute(new ServiceTask(serverSocket.accept(),map));
        }

    }

    @Override
    public void register(Class serviceInterface, Class impl) {
        map.put(serviceInterface.getName(),impl);

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPort() {
        return 0;
    }

}
