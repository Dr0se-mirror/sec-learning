package com.drose.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObj extends Remote {
    public String sayHello(String keywords) throws Exception;
}