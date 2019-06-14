package com.mgmsec.HackMyTeeth.HackMyTeeth.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.eventusermodel.XLSX2CSV;
@Controller
public class ExcelUploadController {
	private static final Logger logger = LoggerFactory
            .getLogger(ExcelUploadController.class);

	
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadPage(HttpServletRequest request, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload");
        return modelAndView;
    }
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView uploadFileHandler(@RequestParam("name") String name,
            @RequestParam("excel") MultipartFile file) {
		ModelAndView modelAndView = new ModelAndView();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                
                
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                try {

                    
                    FileInputStream fis = new FileInputStream(new File(dir.getAbsolutePath()
                            + File.separator + name));
                    Workbook workbook = WorkbookFactory.create(fis);
                    XLSX2CSV xlsx2csv = new XLSX2CSV(OPCPackage.open(fis), System.out, 0); 
                    xlsx2csv.process(); 
                    fis.close(); 

                   

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }

                
                modelAndView.addObject("message", "You successfully uploaded file="+ name);
    			modelAndView.setViewName("upload");
    			return modelAndView;
            } catch (Exception e) {
                modelAndView.addObject("message", "You failed to upload " + name + " => " + e.getMessage());
    			modelAndView.setViewName("upload");
    		    e.printStackTrace();
    			return modelAndView;
            }
        } else {
            modelAndView.addObject("message", "You failed to upload " + name
                    + " because the file was empty.");
			modelAndView.setViewName("upload");
			return modelAndView;
        }
    }

}
