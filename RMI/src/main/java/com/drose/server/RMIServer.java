package com.drose.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        //实例化对象
        Remoteobj remoteobj = new RemoteObjImpl();
        //创建注册中心
        Registry registry = LocateRegistry.createRegistry(1099);
        //绑定对象到注册中心
        registry.bind("qwq",remoteobj);
    }
}