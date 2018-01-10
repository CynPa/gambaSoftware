package com.cynpa.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FiletoByte {
	
	
	public static void main(String[] args) throws ParseException {
		Date fechaInsert=new Date(); 
		 String fechaDummy=null;
		 fechaInsert = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse("2017/12/02 10:20:23");
       // fechaDummy=formatoFecha.format("2017/12/02");
        //fechaInsert=formatoFecha.parse(fechaDummy);
        System.out.println("sdf");
		
	}

}
