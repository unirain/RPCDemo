package consume;

import center.ServiceCenter;
import center.ServiceCenterImpl;
import service.IServer;
import service.ServiceImpl;

import java.net.InetSocketAddress;
import java.time.chrono.IsoChronology;

/********************************************************************************
 *
 * Title: 
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/6/13
 *
 *******************************************************************************/
public class CallTest {
    public static void main(String[] args) {
        new Thread(CallTest::dorun).start();
        IServer server=RPCClient.getRemoteProxyObj(IServer.class,new InetSocketAddress("localhost",1234));
        server.show("chenlm");

    }

    private static void dorun() {
        ServiceCenter serviceCenter = new ServiceCenterImpl(1234);
        serviceCenter.register(IServer.class, ServiceImpl.class);
        try {
            serviceCenter.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
