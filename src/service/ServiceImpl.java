package service;

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
public class ServiceImpl implements  IServer{

    @Override
    public void show(String name) {
        System.out.println("她的名字是："+name);
    }
}
