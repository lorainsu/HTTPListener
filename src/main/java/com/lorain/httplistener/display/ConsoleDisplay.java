package com.lorain.httplistener.display;

import com.lorain.httplistener.DisplayInterface;

public class ConsoleDisplay implements DisplayInterface
{
    
    @Override
    public void write(String str)
    {
        System.out.print(str);
        
    }
    
    @Override
    public void splitLine()
    {
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------------\n");
    }
    
}
