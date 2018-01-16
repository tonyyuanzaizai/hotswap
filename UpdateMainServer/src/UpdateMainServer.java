import com.tony.zaizai.utils.HotSwapAgentManager;

import java.io.IOException;

/**
 * Created by Administrator on 2018/1/15.
 */
public class UpdateMainServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Usage: java UpdateMainServer E:\\tonyyuanzaizai\\agent\\zaizai-agent.jar pid");
        System.out.println("UpdateMainServer."+args[0]);
        System.out.println("UpdateMainServer."+args[1]);
        HotSwapAgentManager.updateMainServer(args[0], args[1]);
    }
}
