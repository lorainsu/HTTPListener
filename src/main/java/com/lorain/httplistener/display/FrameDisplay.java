package com.lorain.httplistener.display;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.lorain.httplistener.DisplayInterface;

public class FrameDisplay extends JFrame implements DisplayInterface
{
    private static final long serialVersionUID = 3950018723712880463L;
    
    private JTextArea area = new JTextArea(10, 20);
    
    public FrameDisplay()
    {
        setDefaultCloseOperation(3);
        setSize(700, 500);
        add(new JScrollPane(area));
        setVisible(true);
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
    
    public JTextArea getTextArea()
    {
        return area;
    }
    
    public void splitLine()
    {
        area.append(
            "----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }
    
    @Override
    public void write(String str)
    {
        area.append(str);
    }
}
