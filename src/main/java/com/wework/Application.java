package com.wework;

import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.Date;
import com.wework.WebPage;

public class Application {

    // Worker threads dequeue
    // Synchronized write

    private volatile static Queue<WebPage> unProcessedQueue = new ConcurrentLinkedDeque<WebPage>();
    private static URL INPUT_URL =  null;
    private static String[] searchTerms = new String[]{"web", "security", "was", "anirudh", "mathad"};
    private static final Integer MAX_SESSIONS = 1; // re attempts
    private static final Integer MAX_THREADS = 50;
    private static Logger logger = Logger.getLogger(Application.class);

    public static void main(String[] args){
        System.out.println("Started fetching Web Pages ...");
        long beginTime = new Date().getTime();
        //Read file
        // Load to a queue
        Util util = new Util();
        try {
            INPUT_URL = new URL("https://s3.amazonaws.com/fieldlens-public/urls.txt");
        } catch (MalformedURLException mue){
            logger.error("Error opening remote file", mue);
        }

        if(INPUT_URL!=null){
            System.out.println("Fetching input file ...");
            util.readFile(INPUT_URL, unProcessedQueue);
            System.out.println("Input file read complete ...");
            for (int i = 0; i < MAX_THREADS; i++) {
                System.out.println("Creating thread: " + i);
                Worker worker = new Worker(unProcessedQueue, searchTerms, MAX_SESSIONS);
                worker.setName(String.format("Thread: %d", i));
                worker.start();
            }
        }

        while (Thread.activeCount() > 2){ //wait till all threads are complete to print metrics
        }
        logger.info(String.format("Total Time taken: %d ms", new Date().getTime()-beginTime) );
        System.out.println(String.format("Total Time taken: %d ms", new Date().getTime()-beginTime));
        System.out.println("Please check output.json for results");
    }

}
