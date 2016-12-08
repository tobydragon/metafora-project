package de.uds.MonitorInterventionMetafora.shared.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//from: http://stackoverflow.com/questions/4288643/how-do-i-peek-the-next-element-on-a-java-scanner

public class PeekableScanner
{
    private Scanner scan;
    private String next;

    public PeekableScanner(File source) throws FileNotFoundException{
    	scan = new Scanner( source );
        next = (scan.hasNextLine() ? scan.nextLine() : null);
    }
    
    public PeekableScanner( String source )
    {
        scan = new Scanner( source );
        next = (scan.hasNextLine() ? scan.nextLine() : null);
    }

    public boolean hasNextLine()
    {
        return (next != null);
    }

    public String nextLine()
    {
        String current = next;
        next = (scan.hasNextLine() ? scan.nextLine() : null);
        return current;
    }

    public String peek()
    {
        return next;
    }
    
    public void close(){
    	scan.close();
    }
}