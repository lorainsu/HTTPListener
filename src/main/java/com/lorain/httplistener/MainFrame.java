package com.lorain.httplistener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.security.UnrecoverableKeyException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = 3950018723712880463L;
    
    public static JTextArea area = new JTextArea(10, 20);
    
    private static int localPort = Integer.parseInt(PropertiesUtils.getValue("localPort").trim());
    
    private static int localSslPort = Integer.parseInt(PropertiesUtils.getValue("localSslPort").trim());
    
    private static String scheme = PropertiesUtils.getValue("scheme");
    
    public static void main(String[] args)
        throws Exception
    {
        new MainFrame().setVisible(true);
        
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(new ServletHolder(new HttpListenerServlet()), "/");
        
        HandlerList handlers = new HandlerList();
        handlers.addHandler(servletContextHandler);
        
        Server server;
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
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.toString()), new HttpConnectionFactory(https_config));
            https_connector.setPort(localSslPort);
            
            server.addConnector(https_connector);
            
        }
        else
        {
            server = new Server(localPort);
        }
        server.setHandler(handlers);
        
        try
        {
            server.start();
            if ("https".equals(scheme))
                area.append("Server listening at >> https://127.0.0.1:" + localSslPort + "\n");
            else
                area.append("Server listening at >> http://127.0.0.1:" + localPort + "\n");
            MainFrame.appendSplitLine();
        }
        catch (BindException e)
        {
            if ("https".equals(scheme))
                area.append("Server start failed : local port " + localSslPort + " is in use.");
            else
                area.append("Server start failed : local port " + localPort + " is in use.");
        }
        catch (FileNotFoundException e)
        {
            area.append("Server start failed : can not find the keystore file.");
        }
        catch (UnrecoverableKeyException e)
        {
            area.append("Server start failed : keyPairPasswd is wrong.");
        }
        catch (IOException e)
        {
            area.append("Server start failed : keyStorePasswd is wrong.");
        }
        
        server.join();
    }
    
    public MainFrame()
    {
        setDefaultCloseOperation(3);
        setSize(700, 500);
        add(new JScrollPane(area));
        area.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void removeUpdate(DocumentEvent documentevent)
            {
            }
            
            @Override
            public void changedUpdate(DocumentEvent documentevent)
            {
            }
            
            @Override
            public void insertUpdate(DocumentEvent documentevent)
            {
                area.setCaretPosition(area.getDocument().getLength());
            }
        });
    }
    
    public static void appendSplitLine()
    {
        area.append(
            "----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }
    
}
