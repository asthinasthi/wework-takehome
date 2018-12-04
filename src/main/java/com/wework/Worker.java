package com.wework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Queue;
import java.util.logging.FileHandler;

import org.apache.log4j.Logger;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import sun.rmi.runtime.Log;

public class Worker extends Thread {

    private WebPage webPage = null;
    private Queue<WebPage> unProcessedQueue = null;
    private String[] searchTerms;
    private Integer MAX_SESSIONS = 0;

    public Worker(Queue<WebPage> unProcessedQueue, String[] searchTerms, Integer maxSessions){
        this.unProcessedQueue = unProcessedQueue;
        this.searchTerms = searchTerms;
        this.MAX_SESSIONS = maxSessions;
    }

    public void run() {
        while (!unProcessedQueue.isEmpty()){
            this.webPage = unProcessedQueue.poll();
            this.webPage.incrementSession();
            this.webPage.setExecutedBy(Thread.currentThread().getName());
            findSearchTerms();
            try {
                Files.write(Paths.get("output.json"), this.webPage.toString().getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ioe){
                Logger.getLogger(Thread.currentThread().getName()).error("Error writing to output file", ioe);
            }
        }
    }

    private void findSearchTerms(){
        //open a webpage
        Document doc = null;
        try {
            String formattedUrl = "https://www." + webPage.getUrl().replaceAll("\"", "");
            doc = Jsoup.connect(formattedUrl).get();
            //search term
            for(String searchTerm: this.searchTerms){
                this.webPage.getSearchTermMap().put(searchTerm, new Boolean(doc.text().contains(searchTerm)).toString());
            }
        } catch (IOException ioe){
            Logger.getLogger(Thread.currentThread().getName()).error("Error accessing the Web Page", ioe);
            this.webPage.setMsg("Error accessing the Web Page");
            if(this.webPage.getSessions() < MAX_SESSIONS){
                unProcessedQueue.add(this.webPage); // add back for reattempts
            }
        }
    }

}
