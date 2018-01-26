package com.lorain.httplistener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JTextArea;

@WebServlet("/")
public class HttpListenerServlet extends HttpServlet
{
    private static JTextArea area = MainFrame.area;
    
    private static final long serialVersionUID = -4628761235513786630L;
    
    private static String customizeResponseHeaders = PropertiesUtils.getValue("customizeResponseHeaders").trim();
    
    private static String customizeResponseBody = PropertiesUtils.getValue("customizeResponseBody").trim();
    
    private static String customizeHttpStatusCode = PropertiesUtils.getValue("customizeHttpStatusCode").trim();
    
    private static int sleepTime = 0;
    
    private static boolean customizeResponseHeadersEnable = false;
    
    private static boolean customizeResponseBodyEnable = false;
    
    private static boolean customizeHttpStatusCodeEnable = false;
    
    static
    {
        if (!"".equals(customizeResponseBody))
            customizeResponseBodyEnable = true;
        
        if (!"".equals(customizeHttpStatusCode))
        {
            try
            {
                Integer.parseInt(customizeHttpStatusCode);
                customizeHttpStatusCodeEnable = true;
            }
            catch (NumberFormatException e)
            {
                area.append("WARN:Customize Http StatusCode is illegal, customizeHttpStatusCode disabled\n\n");
                customizeHttpStatusCodeEnable = false;
            }
        }
        
        try
        {
            if (!"".equals(customizeResponseHeaders))
            {
                String[] customizeHeaders = customizeResponseHeaders.split("##");
                for (String customizeHeader : customizeHeaders)
                    customizeHeader.split(":")[1].trim();
                
                customizeResponseHeadersEnable = true;
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            area.append("WARN:Customize Response Headers is illegal, customizeResponseHeaders disabled\n\n");
            customizeResponseHeadersEnable = false;
        }
        
        try
        {
            String sleepTimeStr = PropertiesUtils.getValue("sleepTime").trim();
            if (!"".equals(sleepTimeStr))
            {
                sleepTime = Integer.parseInt(sleepTimeStr);
            }
        }
        catch (NumberFormatException e)
        {
            area.append("WARN:Customize sleepTime is illegal, sleep disabled\n\n");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        
        MainFrame.appendSplitLine();
        sleep();
        
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        
        customizeResponseHeaders(resp);
        
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("Hello Get".getBytes());
        
        out.flush();
        out.close();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        appendEntity(req);
        MainFrame.appendSplitLine();
        sleep();
        
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        
        customizeResponseHeaders(resp);
        
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("HelloPost".getBytes());
        out.flush();
        out.close();
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        MainFrame.appendSplitLine();
        sleep();
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        customizeResponseHeaders(resp);
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("Hello Delete".getBytes());
        out.flush();
        out.close();
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        appendEntity(req);
        MainFrame.appendSplitLine();
        sleep();
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        customizeResponseHeaders(resp);
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("Hello Put".getBytes());
        out.flush();
        out.close();
    }
    
    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        MainFrame.appendSplitLine();
        sleep();
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        customizeResponseHeaders(resp);
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("Hello Head".getBytes());
        out.flush();
        out.close();
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        MainFrame.appendSplitLine();
        sleep();
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        customizeResponseHeaders(resp);
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("Hello Options".getBytes());
        out.flush();
        out.close();
    }
    
    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        appendRequestLine(req);
        appendHeaders(req);
        MainFrame.appendSplitLine();
        sleep();
        resp.setContentType("charset=utf-8");
        if (customizeHttpStatusCodeEnable)
            resp.setStatus(Integer.parseInt(customizeHttpStatusCode));
        customizeResponseHeaders(resp);
        ServletOutputStream out = resp.getOutputStream();
        if (customizeResponseBodyEnable)
            out.write(customizeResponseBody.getBytes());
        else
            out.write("Hello Trace".getBytes());
        out.flush();
        out.close();
    }
    
    private void appendRequestLine(HttpServletRequest req)
    {
        area.append(req.getMethod() + " ");
        area.append(req.getRequestURI());
        if (null != req.getQueryString())
            area.append("?" + req.getQueryString());
        
        area.append(" " + req.getProtocol());
        area.append("  From Address:" + getRemoteIp(req) + "\n\n");
    }
    
    private void appendHeaders(HttpServletRequest req)
    {
        Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements())
        {
            String header = headers.nextElement();
            area.append(header + ": " + req.getHeader(header) + "\n");
        }
        area.append("\n");
    }
    
    private void appendEntity(HttpServletRequest req)
        throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            area.append(line + "\n");
        }
        area.append("\n");
    }
    
    private void customizeResponseHeaders(HttpServletResponse resp)
    {
        if (customizeResponseHeadersEnable)
        {
            if (null != customizeResponseHeaders && !"".equals(customizeResponseHeaders))
            {
                String[] customizeHeaders = customizeResponseHeaders.split("##");
                for (String customizeHeader : customizeHeaders)
                    resp.addHeader(customizeHeader.split(":")[0], customizeHeader.split(":")[1]);
            }
        }
    }
    
    private String getRemoteIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (checkIp(ip))
        {
            ip = request.getHeader("CLIENT_IP");
        }
        if (checkIp(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (checkIp(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (checkIp(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (null == ip || 0 == ip.length())
        {
            return "";
        }
        String ipAddress = ip.split(",")[0].trim();
        return ipAddress;
    }
    
    private boolean checkIp(String ip)
    {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            return true;
        }
        return false;
    }
    
    private void sleep()
    {
        if (0 != sleepTime)
        {
            try
            {
                Thread.sleep(1000 * sleepTime);
            }
            catch (InterruptedException e)
            {
                area.append("sleep failed:" + e.getMessage() + "\n");
            }
        }
        return;
    }
}
