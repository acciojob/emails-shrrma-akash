package com.driver;

import java.util.ArrayList;
import java.util.Date;

class mail{
    Date date;
    String sender;
    String message;

    public mail(Date date, String sender, String message){
        this.date = date;
        this.sender = sender;
        this.message = message;
    }
}

public class Gmail extends Email {

    int inboxCapacity;
    ArrayList<mail> inboxlist;
    ArrayList<mail> trashlist;

    //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)
    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity = inboxCapacity;
        this.inboxlist = new ArrayList<>();
        this.trashlist = new ArrayList<>();
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if(inboxlist.isEmpty() || inboxlist.size()<this.inboxCapacity){
            inboxlist.add(new mail(date,sender,message));
        }
        else{
            trashlist.add(inboxlist.remove(0));
            inboxlist.add(new mail(date,sender,message));
        }
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        for(int i =0;i<inboxlist.size();i++){
            if(inboxlist.get(i).message.equals(message))
                trashlist.add(inboxlist.remove(i));
        }
    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inboxlist.isEmpty()) return null;
        return inboxlist.get(inboxlist.size() - 1).message;
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inboxlist.isEmpty()) return null;
        return inboxlist.get(0).message;
    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int count = 0;
        for(int i =0;i<inboxlist.size();i++){
            Date currentDate = inboxlist.get(i).date;
            if(currentDate.compareTo(start)>=0 && currentDate.compareTo(end)<=0)
                count++;
        }
        return count;
    }

    public int getInboxSize(){
        // Return number of mails in inbox
        return inboxlist.size();
    }

    public int getTrashSize(){
        // Return number of mails in Trash
        return trashlist.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        while(!trashlist.isEmpty())
            trashlist.remove(0);
    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return this.inboxCapacity;
    }
}
