/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhttp;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 *
 * @author Ronald
 */
public class ServidorHttp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at " + port);
        server.createContext("/", new EchoGetHandler());
        server.createContext("/index", new EchoGetHandler());
        server.setExecutor(null);
        server.start();
    }
    
}
