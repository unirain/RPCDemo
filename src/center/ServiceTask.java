package center;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/********************************************************************************
 *
 * Title: 线程任务
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/6/13
 *
 *******************************************************************************/
public class ServiceTask implements Runnable {
    private Socket socket;
    private Map<String, Class> map;

    public ServiceTask(Socket socket, Map map) {
        this.socket = socket;
        this.map = map;
    }

    @Override
    public void run() {
        //获取流
        //可自动读取对象的流
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        try {
            System.out.println("接受处理+0");
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("接受处理+1");
            String serviceName = input.readUTF();
            System.out.println("接受处理+2");
            String methodName = input.readUTF();
            System.out.println("接受处理+3");
            Class[] parameterTypes = (Class[]) input.readObject();
            Object[] arguments = (Object[]) input.readObject();
            Class service = map.get(serviceName);
            if (service == null) {
                throw new ClassNotFoundException("找不到类型");
            }
            Method method = service.getMethod(methodName, parameterTypes);
            Object result = method.invoke(service.newInstance(), arguments);
            output = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("接受处理+4");
            output.writeObject(result);
            System.out.println("接受处理+5");


        } catch (Exception e) {
            e.printStackTrace();

        }
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
