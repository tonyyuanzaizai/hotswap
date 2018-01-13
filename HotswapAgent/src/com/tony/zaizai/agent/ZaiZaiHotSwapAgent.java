package com.tony.zaizai.agent;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.*;

/**
 * Created by tonyyuan on 2018/01/13.
 */
public class ZaiZaiHotSwapAgent {
    private static final Logger logger = LoggerFactory.getLogger(ZaiZaiHotSwapAgent.class);
    private static final String PATCH_DIR = "E:/tonyyuanzaizai/agent/patches";//patches
    private static final String LOAD_PACKAGES = "com.tony.hotswap";


    public static void premain(String args, Instrumentation inst) {
        
    }

    /**
     * 参考
     * 1. http://xiaohuishu.net/2015/07/26/%E6%8E%A2%E7%B4%A2Java%E7%83%AD%E9%83%A8%E7%BD%B2/
     * 2. http://zeroturnaround.com/rebellabs/reloading-objects-classes-classloaders/
     * 3. http://linmingren.me/blog/2013/02/动态替换目标进程的java类/
     * 4. http://www.javabeat.net/introduction-to-java-agents/?cm_mc_uid=69969826849814482528628&cm_mc_sid_50200000=1448263170
     * 5. http://jboss-javassist.github.io/javassist/tutorial/tutorial.html
     * 6. http://download.forge.objectweb.org/asm/asm4-guide.pdf
     * @param args
     * @param inst
     * @throws Exception
     */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        logger.info("[hot swap] begin, agentmain method invoked with args: {} and inst: {}, RedefineClasses: {} and RetransformClasses: {}", args, inst, inst.isRedefineClassesSupported(), inst.isRetransformClassesSupported());
        Map<String, String> classNamePathMap = getFullClassNameFilePathMap();
        if (classNamePathMap.isEmpty()) {
            logger.info("[hot swap] no patch files, finish.");
            return;
        }
        logger.info("[hot swap] FullClassNameFilePathMap: {}", classNamePathMap);
        ZaiZaiClassTransform classTransformer = new ZaiZaiClassTransform(classNamePathMap);
//        inst.addTransformer(classTransformer, true);
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        List<Class> transformClasses = new ArrayList<>();
        for (Class loadedClass : allLoadedClasses) {
            String loadedClassName = loadedClass.getName();
            //logger.info("[hot swap] loadedClassName:", loadedClassName);
            if (classNamePathMap.containsKey(loadedClassName)) {
                transformClasses.add(loadedClass);
            }
        }
        try {
            inst.addTransformer(classTransformer, true);
            inst.retransformClasses(transformClasses.toArray(new Class[]{}));
        } catch (Throwable t) {
            logger.error("[hot swap] re transform classes error, classes: " + classNamePathMap.keySet().toString(), t);
        }
        inst.removeTransformer(classTransformer);
        logger.info("[hot swap] finish");
    }

    /**
     * this class loaded by Application Class Loader, can not load extension classes, need extension class loader extends URLClassLoader
     * public static void agentmain(String agentArguments, Instrumentation instrumentation) throws Exception {
     * logger.info("[hot swap] agentmain method invoked with args: {} and inst: {}", agentArguments, instrumentation);
     * logger.info("[hot swap] RedefineClasses flag {}, RetransformClasses flag {}", instrumentation.isRedefineClassesSupported(), instrumentation.isRetransformClassesSupported());
     * Collection<File> patchClassFiles = FileUtils.listFiles(new File(PATCH_DIR), new String[]{"class"}, true);
     * if (patchClassFiles.isEmpty()) {
     * logger.info("[hot swap] no patch files");
     * return;
     * }
     * for (File patchFile : patchClassFiles) {
     * String path = patchFile.getPath();
     * String fullClassName = getFullClassName(path);
     * if (!fullClassName.startsWith(LOAD_PACKAGES)) continue;
     * logger.info("[hot swap] ready redefine file {}, full class name {}", path, fullClassName);
     * byte[] classContents = Files.readAllBytes(Paths.get(path));
     * ClassDefinition classDefinition = new ClassDefinition(Class.forName(fullClassName), classContents);
     * instrumentation.redefineClasses(classDefinition);
     * logger.info("[hot swap] finish redefine class {}", fullClassName);
     * }
     * }
     **/

    private static Map<String, String> getFullClassNameFilePathMap() {
        Map<String, String> map = new HashMap<>();
        Collection<File> patchClassFiles = FileUtils.listFiles(new File(PATCH_DIR), new String[]{"class"}, true);
        for (File patchFile : patchClassFiles) {
            String filePath = patchFile.getPath();

            logger.info("[hot swap] find filePath: " + filePath);
            //

            if(new File(PATCH_DIR).isAbsolute()){
                //patchFile.getCanonicalPath();
                filePath = filePath.substring(new File(PATCH_DIR).getAbsolutePath().length()+1);
                logger.info("[hot swap] find new filePath: " + filePath);
            }
            else {
                filePath = filePath.substring(PATCH_DIR.length() + 1);
                logger.info("[hot swap] find new filePath: " + filePath);
            }

            String fullClassName = getFullClassName(filePath);
            logger.info("[hot swap] find fullClassName: " + fullClassName);
            if (fullClassName.startsWith(LOAD_PACKAGES)) {
                map.put(fullClassName, patchFile.getPath());
            }
        }
        return map;
    }

    private static String getFullClassName(String filePath) {
        int start = 0;
        int end = filePath.lastIndexOf('.');
        String fullClassPath = filePath.replace(File.separator, ".");
        fullClassPath = fullClassPath.substring(start, end);
        return fullClassPath;
    }
}
