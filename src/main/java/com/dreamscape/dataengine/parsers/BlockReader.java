/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.exceptions.PageDoesNotExistException;

/**
 *
 * @author Jared
 */
public class BlockReader {
    private int position;
    private String text;
    
    public BlockReader(String block){
        this.text = block;
        this.position = 0;
    }
    
    public String getRemainingBlock(){ //TODO: Something buggy with this...
        if(position < text.length())
            return text.substring(position);
        else
            return null;
    }

    static String trimExcessContent(String content, String startContent, String endContent, boolean throwExceptionIfStartContentMissing, boolean throwExceptionIfEndContentMissing) throws StringIndexOutOfBoundsException{
        
        //System.out.println("Content: " + content);
        //System.out.println("Start Content: " + startContent);
        //System.out.println("End Content: " + endContent);
        try{
            int start = content.indexOf(startContent);
            if (start < 0)
            {
                StringIndexOutOfBoundsException exception = new StringIndexOutOfBoundsException();
                throw exception;
            }
            int end = content.indexOf(endContent);

            System.out.println("Start: " + start + " End: " + end);
            if(end > 0 && endContent.length() > 0)
                return content.substring(start, end);
            
            if(throwExceptionIfEndContentMissing)
                throw new PageDoesNotExistException();
            
            return content.substring(start);
        }
        catch(StringIndexOutOfBoundsException e){
            System.out.println("The following content could not be located in the Text supplied: " + startContent);
            System.out.println("Unable to trim content using this token, please configure with a different token.");
            if(throwExceptionIfStartContentMissing)
            {
                System.out.println("Assuming there is no valid content to parse. Returning blank content.");
                throw new PageDoesNotExistException();
            }
            else
                System.out.println("Returning content untrimmed. Configure with another token for better performance.");
                return content;
        }
    }
    static String removeWhiteSpace(String content){
        return content.replaceAll("[\n\r\t]", "");
    }
    
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void incrementPosition(){
        ++position;
    }
    
    public void clean(){
        this.text = "";
        this.position = 0;
    }
}
