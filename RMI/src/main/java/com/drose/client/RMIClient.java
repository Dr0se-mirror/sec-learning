package com.drose.client;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        //        方法一：通过registry的lookup
//        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
//        RemoteObj remoteObj = (RemoteObj)registry.lookup("remoteObj");
//        System.out.println("Client:" + remoteObj.sayHello("hello"));
//        方法二：通过Naming的lookup
//        RemoteObj RemoteObj = (RemoteObj)Naming.lookup("rmi://127.0.0.1:1099/remoteObj");
//        System.out.println("Client:" + RemoteObj.sayHello("hello"));
        String[] clazz = Naming.list("rmi://127.0.0.1:1099");
        for (String s:clazz) {
            System.out.println(s);
        }
    }
}
