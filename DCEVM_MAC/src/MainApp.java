// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainApp.java

import at.ssw.hotswap.installer.Installation;
import at.ssw.hotswap.installer.Installer;
import at.ssw.hotswap.installer.InstallerException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package at.ssw.hotswap.installer:
//            MainWindow, InstallerException, Installer

public class MainApp
{

    public MainApp()
    {
    }
    private static final ArrayList<Installation> installations = new ArrayList<Installation>();
    public static void main(String args[])
    {
        try {
            if(installations.size() == 0){
                Installer installer = Installer.create();
                Installation installation;
                for (Iterator iterator = installer.listAllInstallations().iterator(); iterator.hasNext(); ) {
                    installation = (Installation) iterator.next();
                    installations.add(installation);
                    System.out.println("Installation:" + installation.getPath() + "|" + installation.getVersion());

                    System.out.println("Install |isJDK:" + installation.isJDK() +"|isDCEInstalled:"+ installation.isDCEInstalled()
                            +"|64bit:"+ installation.is64Bit());
                }
            }
            System.out.println("Usage: java -jar dcevm-0.2-mac_new.jar install 0");
            System.out.println("Usage: java -jar dcevm-0.2-mac_new.jar uninstall 0");
        }
        catch(Exception ex)
        {
            showInstallerException(ex);
        }

        if(installations.size() < 1) {
            System.out.println("Cannot find jdk!!!!");
        }

        if(args.length != 2){
            System.out.println("Installation false");
            return;
        }

        try
        {
            if("install".equals(args[0]) || "uninstall".equals(args[0])){
                // do nothing
            }
            else {
                return;
            }

            int idx = Integer.parseInt(args[1]);
            Installation installation = installations.get(idx);

            if("install".equals(args[0])){
                installation.installDCE();
            }
            else {
                installation.uninstallDCE();
            }

            System.out.println("InstallFinish |isJDK:" + installation.isJDK() +"|isDCEInstalled:"+ installation.isDCEInstalled()
                    +"|64bit:"+ installation.is64Bit());
        }
        catch(Throwable ex)
        {
            showInstallerException(ex);
        }
    }

    static void showInstallerException(Throwable ex)
    {
        Throwable e = ex;
        String error = e.getMessage();
        for(e = e.getCause(); e != null; e = e.getCause())
            error = (new StringBuilder()).append(error).append("\n").append(e.getMessage()).toString();

        System.out.println("error");
        System.out.println(error);
        System.out.println(ex.getMessage());
        System.out.println("");
    }
}
