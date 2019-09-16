package server;


import java.net.*;
import java.io.*;

/**
 *
 * @author Douglas Allan Braga
 */
public class Server {
    public static void main(String[] args){
        
        int port = 6789;
        String result;
        String letra;
        DatagramPacket request;
        DatagramPacket reply;
        String validarLetra;
        byte[] buffer = new byte[100];
        
        try (DatagramSocket serverSocket = iniciarDatagramSocket(port)) {

            //request = pedido
            request = new DatagramPacket(buffer,buffer.length);  
            //receive = receber
            serverSocket.receive(request);

            result = new String(request.getData());
            result = result.toUpperCase();
            System.out.println(result);

            //reply = resposta
            reply = new DatagramPacket(request.getData(),request.getLength(),
                                       request.getAddress(), request.getPort());
            //send = enviar
            serverSocket.send(reply);
            
            while(true){
                buffer = new byte[1];
                //pacote a ser recebido
                request = new DatagramPacket(buffer, buffer.length);
                //recebe o pacote do cliente(fica esperando) 
                serverSocket.receive(request);

                //pega a msg do cliente
                letra = new String(request.getData()).toUpperCase();

                System.out.println("Letra que chegou no servidor: " + letra);

                if( result.contains(letra)){
                    validarLetra = "1";
                    System.out.println("Letra Correta");
                }else{
                    validarLetra = "0";
                    System.out.println("Letra Errada");
                }

                buffer = validarLetra.getBytes();

                reply = new DatagramPacket(buffer, buffer.length, request.getAddress(), request.getPort());

                serverSocket.send(reply);
            }      
            
        } catch (SocketException ex) {
            System.out.println("Socket: " + ex);
        } catch (IOException ex){
            System.out.println("Input Output: " + ex);
        }   
    }
    
    private static DatagramSocket iniciarDatagramSocket(int port) throws SocketException{
        return new DatagramSocket(port);
    }
    
    
}
