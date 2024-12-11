package com.drose.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Remoteobj extends Remote {
    public String SayHello(String key) throws RemoteException;
}