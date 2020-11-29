
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.lang.*;
import java.net.URL;
import com.sun.net.httpserver.HttpsServer;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import com.sun.net.httpserver.*;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.net.InetAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsExchange;

public class SimpleHTTPSServer {

    public static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            HttpsExchange httpsExchange = (HttpsExchange) t;
            Map<String, Object> parameters = new HashMap<String, Object>();
            URI requestedUri = t.getRequestURI();
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
            
            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        try {
            // setup the socket address
            InetSocketAddress address = new InetSocketAddress(9000);

            // initialise the HTTPS server
            HttpsServer httpsServer = HttpsServer.create(address, 0);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // initialise the keystore
            char[] password = "password".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream("C:\\Users\\randa\\.p2\\pool\\plugins\\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_14.0.2.v20200815-0932\\jre\\bin\\testkey.jks");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // Set the SSL parameters
                        SSLParameters sslParameters = context.getSupportedSSLParameters();
                        params.setSSLParameters(sslParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
            httpsServer.createContext("/", new MyHandler());
            httpsServer.createContext("/index", new MyHandler());
            httpsServer.createContext("/mac", new MyHandler());
            httpsServer.createContext("/pizza", new MyHandler());
            httpsServer.createContext("/burguer", new MyHandler());
            httpsServer.createContext("/kfc", new MyHandler());
            httpsServer.setExecutor(null); // creates a default executor
            httpsServer.start();

        } catch (Exception exception) {
            System.out.println("Failed to create HTTPS server on port " + 9000 + " of localhost");
            exception.printStackTrace();

        }
    }

}
