import com.tony.hotswap.HotTestClass;
import com.tony.zaizai.utils.HotSwapAgentManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2018/1/12.
 */
public class MainServer {
    private static final Logger logger = LoggerFactory.getLogger(MainServer.class);

    //搭建服务器端
    public static void main(String[] args) throws IOException {
        MainServer socketService = new MainServer();
        //1、a)创建一个服务器端Socket，即SocketService
        socketService.oneServer();
    }
    public  void oneServer(){
        try{
            getPID();

            while(true){
                try{
                    Thread.sleep(10000);
                }
                catch(Throwable ee){
                    System.out.println("Error."+ee);
                }
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                loadHotClass();
            } //继续循环
        }catch(Exception e) {//出错，打印出错信息
            System.out.println("Error."+e);
        }
    }

    // 更新hot class
    private static void loadHotClass() {
        System.out.println("new HotTestClass getValue--1: " + (new HotTestClass()).getRawValue());
        System.out.println("new HotTestClass getStaticValue--1: " + HotTestClass.getStaticRawValue());
        System.out.println("new HotTestClass staticRawField--1: " + HotTestClass.staticRawField);
        System.out.println("new HotTestClass publicRawField--1: " + (new HotTestClass()).publicRawField);
    }

    private static String getPID() {
        String pidAtHost = ManagementFactory.getRuntimeMXBean().getName();
        String pid = StringUtils.split(pidAtHost, '@')[0];
        logger.info("pid is {}", pid);
        return pid;
    }
}
