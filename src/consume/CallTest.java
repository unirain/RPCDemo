package consume;

import center.ServiceCenter;
import center.ServiceCenterImpl;
import service.IServer;
import service.ServiceImpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
//        new Thread(CallTest::dorun).start();
//        IServer server=RPCClient.getRemoteProxyObj(IServer.class,new InetSocketAddress("localhost",1234));
//        server.show("chenlm");
        tez();

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

    private static void tez() {
        List<String> list = new LinkedList<>();
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals("8")) {
                iterator.remove();
            }
        }


    }
}
