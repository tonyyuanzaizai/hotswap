package com.tony.hotswap;

import org.slf4j.Logger;

import java.lang.reflect.Field;

public class HotTestClass{
    public String getRawValue(){
        return privateGetRawV();
    }

    private String privateGetRawV(){
//        try{
//            Class<?> clazz =  Class.forName("ZaiZaiHotSwapAgent");
//            Field fieldPkg = clazz.getDeclaredField("LOAD_PACKAGES");
//            System.out.println("Hello World!--1" + fieldPkg);
//        }
//        catch(Throwable ex){
//            System.out.println("Hello World!" + ex);
//        }


//        return "privateGetRawV-string==8";
        return hotprivateGetRawV();
    }
//
    private String hotprivateGetRawV(){
        hotprivateGetRawV2();
        staticRawField = "staticRawField==8-6";
        return "hot privateGetRawV-string==8-6";
    }


    private String hotprivateGetRawV2(){
        staticRawField = "staticRawField==8-6";
        System.out.println("hotprivateGetRawV2");
        return "hot privateGetRawV-string==8-6";
    }

    public static String getStaticRawValue(){

        return "static-raw-string==8-6";
    }

    public static String staticRawField = "staticRawField==8-6";
    public String publicRawField = "publicRawField==8-6";
}