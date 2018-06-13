package center;

/********************************************************************************
 *
 * Title:  服务中心
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/6/13
 *
 *******************************************************************************/
public interface ServiceCenter {
    /**
     * 停止
     */
    void stop();

    /**
     * 开启服务
     * @throws Exception
     */
    void start()throws Exception;

    /**
     * 注册服务
     * @param serviceInterface
     * @param impl
     */
    void register(Class serviceInterface,Class impl);

    /**
     *  是否在运行
     * @return
     */
    boolean isRunning();

    /**
     * 获取端口
     * @return
     */
    int getPort();

}
