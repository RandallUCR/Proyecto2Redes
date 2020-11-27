/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorhttp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ronald
 */
public class EchoGetHandler implements HttpHandler {

    parseQuery p = new parseQuery();

    @Override

    public void handle(HttpExchange he) throws IOException {
        // parse request
        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = he.getRequestURI();
        System.out.println("ll" + requestedUri);
        //String query = requestedUri.getRawQuery();
        //p.parseQuery(query, parameters);

        String response = "";
        
        switch(requestedUri.toString()) {
        case "/":
        	try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\index.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        case "/index":
        	try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\index.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        	break;
        case "/mac":
        	try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\mac.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        	break; 
        case "/pizza":
        	try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\pizza.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        	break; 
        case "/burguer":
        	try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\burguer.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        	break; 
        case "/kfc":
        	try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\kfc.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        	break; 
        default:
        	response = "ERROR 44";
        	break;
        }
        
        /*if (requestedUri.toString().equals("/index")) {
            System.out.println("ENTREEE");
            response = "ERROR 44";
        } else {
            try {
                response = new String(Files.readAllBytes(Paths.get("D:\\Main\\Desktop\\xd.html")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/

        // send response
        for (String key : parameters.keySet()) {
            response += key + " = " + parameters.get(key) + "n";
            //System.out.println(response);
        }
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.toString().getBytes());

        os.close();

    }

}
