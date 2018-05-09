package com.superappps.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.superappps.demo.AllRequestsServlet;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;


import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        // String url = "smb://not-existent/aasd/23232/2323/";
        String url = "smb:///aasd/23232/2323/";

        SmbFile smbFile = null;

        try {
            smbFile = new SmbFile(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String host = smbFile.getServer();
        if (host == null) {
            throw new IllegalArgumentException(url
                    + " host is not valid");
        }
        System.out.println(host);

        // String hostAddress;
        // InetAddress inetAddress = null;

        try {
            /*InetAddress inetAddress =*/ InetAddress.getByName(host);  // .getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

//        String res = inetAddress == null ? "host not found" : inetAddress.getHostAddress();
//        System.out.println(res);

        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");

        Server server = new Server(8888);
        server.setHandler(context);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
