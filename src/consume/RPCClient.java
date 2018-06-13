package consume;

import com.sun.corba.se.impl.io.InputStreamHook;
import sun.text.normalizer.UBiDiProps;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/********************************************************************************
 *
 * Title: 客户端
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/6/13
 *
 *******************************************************************************/
public class RPCClient<T> {
    public static <T> T getRemoteProxyObj(Class serviceInterface, InetSocketAddress addr) {

        return  (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface},new proxyHandler(addr,serviceInterface));

    }

    /**
     * 代理实现
     */
    private static  class proxyHandler implements InvocationHandler {
        private InetSocketAddress address;
        private Class serviceInterface;

        public proxyHandler(InetSocketAddress address, Class serviceInterface) {
            this.address = address;
            this.serviceInterface = serviceInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Socket socket = null;
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {
                //总结 ，，每写入一次流就会被读取一次
                socket=new Socket();
                System.out.println("开始连接服务中心");
                System.out.println("***请求处理+0");
                socket.connect(address);
                System.out.println("***请求处理+1");
                //发送需要调用的类信息
                output=new ObjectOutputStream(socket.getOutputStream());
                System.out.println("***请求处理+2");
                output.writeUTF(serviceInterface.getName());
                output.writeUTF(method.getName());
                output.writeObject(method.getParameterTypes());
                System.out.println("***请求处理+3");
                output.writeObject(args);
                System.out.println("***请求处理+4");
                input=new ObjectInputStream(socket.getInputStream());
                System.out.println("***请求处理+5");
                Object o=input.readObject();
                System.out.println("**请求处理+6");
                return o;

            } finally {
                if (socket != null) socket.close();
                if (output != null) output.close();
                if (input != null) input.close();
            }

        }
    }

}
