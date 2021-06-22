/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author fran_
 */
public class main {
    public static void main(String[] args) {
    try {
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost:1099/remote",new ChatServidor());
            System.out.println("Servidor a la escucha ...");
        } catch (MalformedURLException | RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
}
