package com.tony.zaizai.utils;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by lifangkai on 15/12/7.
 */
public class HotSwapAgentManager {
    private static final Logger logger = LoggerFactory.getLogger(HotSwapAgentManager.class);
    private static String AGENT_JAR = "E:\\tonyyuanzaizai\\agent\\zaizai-agent.jar";
    private static String JAVA_PID = null;

    public static void updateMainServer(String agentJarPath, String pid) {
        AGENT_JAR = agentJarPath;
        JAVA_PID =  pid;
        reloadAgent();
    }
    public static void reloadAgent() {
        if(JAVA_PID == null){
            JAVA_PID= getPID();
        }

        VirtualMachine vm = null;
        try {
            vm = VirtualMachine.attach(JAVA_PID);
        } catch (AttachNotSupportedException e) {
            logger.error("attach " + JAVA_PID, e);
        } catch (IOException e) {
            logger.error("attach " + JAVA_PID, e);
        }
        if (vm == null) {
            return;
        }
        try {
            vm.loadAgent(AGENT_JAR);
            vm.detach();
        } catch (AgentLoadException e) {
            logger.error("load agent " + JAVA_PID, e);
        } catch (AgentInitializationException e) {
            logger.error("load agent " + JAVA_PID, e);
        } catch (IOException e) {
            logger.error("load agent " + JAVA_PID, e);
        }
    }

    private static String getPID() {
        String pidAtHost = ManagementFactory.getRuntimeMXBean().getName();
        String pid = StringUtils.split(pidAtHost, '@')[0];
        logger.info("pid is {}", pid);
        return pid;
    }
}
