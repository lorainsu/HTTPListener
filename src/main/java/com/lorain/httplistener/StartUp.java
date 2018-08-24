package com.lorain.httplistener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.security.UnrecoverableKeyException;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import com.lorain.httplistener.display.ConsoleDisplay;
import com.lorain.httplistener.display.FrameDisplay;

public class StartUp
{
    public static DisplayInterface display;
    
    private static Server server;
    
    private static int localPort;
    
    private static int localSslPort;
    
    private static String scheme;
    
    public static void main(String[] args)
        throws Exception
    {
        if (args.length != 0 && "-c".equals(args[0]))
            display = new ConsoleDisplay();
        else
            display = new FrameDisplay();
        
        initData();
        try
        {
            server.start();
            if ("https".equals(scheme))
                display.write("Server listening at >> https://127.0.0.1:" + localSslPort + "\n");
            else
                display.write("Server listening at >> http://127.0.0.1:" + localPort + "\n");
            display.splitLine();
        }
        catch (BindException e)
        {
            if ("https".equals(scheme))
                display.write("Server start failed : local port " + localSslPort + " is in use.");
            else
                display.write("Server start failed : local port " + localPort + " is in use.");
        }
        catch (FileNotFoundException e)
        {
            display.write("Server start failed : can not find the keystore file.");
        }
        catch (UnrecoverableKeyException e)
        {
            display.write("Server start failed : keyPairPasswd is wrong.");
        }
        catch (IOException e)
        {
            display.write("Server start failed : keyStorePasswd is wrong.");
        }
        
        server.join();
    }
    
    public static void initData()
    {
        localPort = Integer.parseInt(PropertiesUtils.getValue("localPort").trim());
        
        localSslPort = Integer.parseInt(PropertiesUtils.getValue("localSslPort").trim());
        
        scheme = PropertiesUtils.getValue("scheme");
        
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(new ServletHolder(new HttpListenerServlet()), "/");
        
        HandlerList handlers = new HandlerList();
        handlers.addHandler(servletContextHandler);
        
        if ("https".equals(scheme))
        {
            server = new Server();
            
            HttpConfiguration https_config = new HttpConfiguration();
            https_config.setSecureScheme(scheme);
            
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath("./keystore");
            sslContextFactory.setKeyStorePassword(PropertiesUtils.getValue("keyStorePasswd"));
            sslContextFactory.setKeyManagerPassword(PropertiesUtils.getValue("keyPairPasswd"));
            
            ServerConnector https_connector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.toString()),
                new HttpConnectionFactory(https_config));
            https_connector.setPort(localSslPort);
            
            server.addConnector(https_connector);
            
        }
        else
        {
            server = new Server(localPort);
        }
        server.setHandler(handlers);
    }
}
