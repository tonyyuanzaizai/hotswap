// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   MainApp.java


import com.github.dcevm.installer.ConfigurationInfo;
import com.github.dcevm.installer.Installation;
import com.github.dcevm.installer.Installer;

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
            if(installations.size() == 0) {
                Installer installer = new Installer(ConfigurationInfo.current());
                Installation installation;
                for (Iterator iterator = installer.listInstallations().iterator(); iterator.hasNext(); ){
                    installation = (Installation) iterator.next();
                    installations.add(installation);
                    System.out.println("Installation:" + installation.getPath() + "|" + installation.getVersion());

                    System.out.println("Install |isJDK:" + installation.isJDK() +"|isDCEInstalled:"+ installation.isDCEInstalled()
                                    +"|isDCEInstalledAltjvm:"+ installation.isDCEInstalledAltjvm()
                            +"|64bit:"+ installation.is64Bit());

                }

            }
            System.out.println("Usage: java -jar DCEVM-8u144-installer_new.jar install 0 altjvm true");
            System.out.println("Usage: java -jar DCEVM-8u144-installer_new.jar install 0 altjvm false");
            System.out.println("Usage: java -jar DCEVM-8u144-installer_new.jar uninstall 0 altjvm true");
        }
        catch(Exception ex)
        {
            showInstallerException(ex);
        }

        if(args.length != 4){
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
            boolean install = Boolean.getBoolean(args[3]);
            Installation installation = installations.get(idx);

            if("install".equals(args[0])){
                installation.installDCE(install);
            }
            else {
                installation.uninstallDCE();
            }

            installation.update();
            System.out.println("InstallFinish |isJDK:" + installation.isJDK() +"|isDCEInstalled:"+ installation.isDCEInstalled()
                    +"|isDCEInstalledAltjvm:"+ installation.isDCEInstalledAltjvm()
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
