package com.drose.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjImpl extends UnicastRemoteObject implements RemoteObj {

    public RemoteObjImpl() throws Exception {
    }

    public String sayHello(String keywords) throws Exception {
        String upKeywrods = keywords.toUpperCase();
        System.out.println("Server:" + upKeywrods);
        return upKeywrods;
    }
}