package com.drose.client;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
        Remoteobj remoteobj = (Remoteobj) registry.lookup("qwq");
        String res =remoteobj.SayHello("test");
        System.out.println(res);
        }
}

