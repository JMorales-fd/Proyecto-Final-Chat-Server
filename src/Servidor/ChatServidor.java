/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Cliente.InterfaceCliente;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author fran_
 */
public class ChatServidor extends UnicastRemoteObject implements InterfazServidor {
    private final ArrayList<InterfaceCliente> clientes;
    private final ArrayList<InterfaceCliente> clienteblocked;
    public ChatServidor()throws RemoteException{
        super();
        this.clientes = new ArrayList<>();
        clienteblocked = new ArrayList<>();
    }
    @Override
    public synchronized void DifundirMensaje(String msg, List<String> list) throws RemoteException {
        if(list.isEmpty()){
            int i =0;
            while(i<clientes.size()){
                clientes.get(i++).RecuperaMensaje(msg);
            }
        }else{
            for(InterfaceCliente cliente : clientes ){
                for (int i = 0; i < list.size(); i++) {
                    if(cliente.getName().equals(list.get(i))){
                        cliente.RecuperaMensaje(msg);
                    }
                }
            }
        }
    }

    @Override
    public synchronized void DifundirArchivo(ArrayList<Integer> inc, List<String> list, String nombreArchivo) throws RemoteException {
       if(list.isEmpty()){
            int i =0;
            while(i<clientes.size()){
                clientes.get(i++).RecuperarArchivo(nombreArchivo, inc);
            }
        }else{
            for(InterfaceCliente cliente : clientes ){
                for (int i = 0; i < list.size(); i++) {
                    if(cliente.getName().equals(list.get(i))){
                        cliente.RecuperarArchivo(nombreArchivo, inc);
                    }
                }
            }
        }
    }

    @Override
    public synchronized Vector<String> getNombreListaCliente(String name) throws RemoteException {
        Vector<String> list = new Vector<>();
        for(InterfaceCliente cliente: clientes){
            if(!cliente.getName().equals(name)){
                list.add(cliente.getName());
            }
        }
        return list;
    }

    @Override
    public synchronized void addCliente(InterfaceCliente cliente) throws RemoteException {
        this.clientes.add(cliente);
    }

    @Override
    public synchronized void bloquearCliente(List<String> clientess) throws RemoteException {
        for (int j = 0; j < this.clientes.size(); j++) {
            for (int i = 0; i <clientess.size(); i++) {
                try {
                    if (this.clientes.get(j).getName().equals(clientess.get(i))){
                        this.clientes.get(j).cerrarChat(clientess + "el administrador te bloqueo");
                        clienteblocked.add(this.clientes.get(j));
                    }
            } catch (RemoteException e) {
                System.out.println("Error " + e.getMessage());
            }
            }
        }
    }

    @Override
    public synchronized void eliminarTodosCliente(List<String> clientess) throws RemoteException {
        for (int j = 0; j < this.clientes.size(); j++) {
            for (int i = 0; i < clientess.size() ; i++) {
                try {
                    if(this.clientes.get(j).getName().equals(clientess.get(i))){
                        this.clientes.get(j).cerrarChat(clientess.get(i) + "has sido eliminado del chat");
                        this.clientes.remove(j);
                    }
                } catch (RemoteException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void eliminarCliente(String clientess) throws RemoteException {
        for (int j = 0; j < this.clientes.size(); j++) {
            try {
                if (this.clientes.get(j).getName().equals(clientess)) {
                    this.clientes.remove(j);
                }
            } catch (RemoteException e) {
                System.out.println("Error: " + e.getMessage());
            }   
        }
    }

    @Override
    public void activarCliente(List<String> clientess) throws RemoteException {
        for (int j = 0; j < this.clienteblocked.size(); j++) {
            try {
                if (this.clienteblocked.get(j).getName().equals(clientess)) {
                    this.clienteblocked.get(j).abrirChat();
                    this.clienteblocked.remove(j);
                }
            } catch (RemoteException e) {
            }
        }
    }

    @Override
    public boolean existeNombreClinete(String nombreUser) throws RemoteException {
       boolean existe = false;
        for (int i = 0; i <clientes.size(); i++) {
            if (clientes.get(i).getName().equals(nombreUser)) {
                existe = true;
            }
        }
        for (int j = 0; j < clienteblocked.size(); j++) {
            if (!clienteblocked.get(j).getName().equals(nombreUser)) {
                existe=true;
                
            }
        }
        return existe;
    }
    
}
