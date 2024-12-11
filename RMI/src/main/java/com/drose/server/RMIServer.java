package com.drose.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        RemoteObjImpl remoteObj = new RemoteObjImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
//         写法一：通过Naming绑定
//        Naming.bind("remoteObj",remoteObj);
//         写法二：通过registry绑定
        registry.bind("remoteObj",remoteObj);
    }
}