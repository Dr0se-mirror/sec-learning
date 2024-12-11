package com.drose.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjImpl extends UnicastRemoteObject implements Remoteobj {
    public RemoteObjImpl() throws RemoteException{

    }
    @Override
    public String SayHello(String key) throws RemoteException{
        System.out.println(key);
        return key;
    }
}