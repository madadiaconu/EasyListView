package com.madadiaconu.easylistview.exceptions;

/**
 * Created by Diaconu Madalina on 23.01.2016.
 */
public class InvalidModelClassException extends Exception{

    public InvalidModelClassException(){
        super("Fields missing or not corresponding to the xml file.");
    }

}
