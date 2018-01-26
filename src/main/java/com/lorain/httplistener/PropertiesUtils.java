package com.lorain.httplistener;

import java.io.*;
import java.util.Properties;

public abstract class PropertiesUtils
{
    public PropertiesUtils()
    {
    }
    
    private static Properties loadProperty()
    {
        Properties p = new Properties();
        loadProp("httpListener_config.properties", p);
        return p;
    }
    
    private static void loadProp(String conf, Properties p)
    {
        InputStream is = null;
        try
        {
            is = getInputStream(conf);
            if (null != is)
            {
                p.load(is);
            }
        }
        catch (IOException e)
        {
            MainFrame.area.append("Exception happened in loadProp() :" + e.getMessage());
        }
        finally
        {
            if (is != null)
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    MainFrame.area.append("Exception happened in loadProperty() :" + e.getMessage());
                }
        }
    }
    
    @Override
    protected Object clone()
        throws CloneNotSupportedException
    {
        return super.clone();
    }
    
    public static String getValue(String key)
    {
        String value = properties.getProperty(key);
        return value != null ? value : "";
    }
    
    private static InputStream getInputStream(String path)
        throws IOException
    {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (is == null)
            throw new FileNotFoundException(path + " cannot be opened because it does not exist");
        else
            return is;
    }
    
    private static Properties properties = null;
    
    static
    {
        properties = loadProperty();
    }
}
