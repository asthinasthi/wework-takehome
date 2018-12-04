package com.wework;

import java.io.*;
import java.util.Queue;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.FileHandler;
import com.wework.WebPage;
import org.apache.log4j.Logger;

public class Util {
    private static Logger logger = Logger.getLogger("Util");

    public void readFile(URL inputFileUrl, Queue<WebPage> unProcessedQueue){
        logger.info("Reading file ...");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            Scanner scanner = new Scanner(inputFileUrl.openStream());
            if(scanner.hasNext()){
                scanner.nextLine(); // skip header
                String line = null;
                while (scanner.hasNextLine()){
                    line = scanner.nextLine();
                    String[] splits = line.split(",");
                    WebPage webPage = new WebPage(Integer.parseInt(splits[0]), splits[1]);
                    unProcessedQueue.add(webPage);
                }
            }
        } catch (IOException ioe){
//            logger.log(Level.SEVERE, "Error accessing the file stream", ioe);
            logger.error(ioe);
        }
    }
}
