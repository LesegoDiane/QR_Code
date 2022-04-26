package dev.simplesolution.generateqr.controller;

import dev.simplesolution.generateqr.service.QRCodeService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.simplesolution.generateqr.doa.TrackedEntity;
import dev.simplesolution.generateqr.doa.VaccinationEntity;
import dev.simplesolution.generateqr.rest.client.DataRestClient;
import dev.simplesolution.generateqr.rest.client.TrackedEntityResponse;
import dev.simplesolution.generateqr.rest.exceptions.RestClientException;


import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;
import org.json.simple.JSONObject;
import java.net.URLEncoder;

@Controller
public class QRCodeController {
	
	
	public String printPdf(JSONArray vaccineData, Map entityData ) throws IOException, JRException, RestClientException {
			
		//get data
  
		Map entity  = entityData;
			
		//System.out.println(entity.get("Identification no"));
    	//System.out.println(vaccineData.get("bbnyNYD1wgS"));//vaccine name
    	    	
		String outputFile = "C:\\Users\\b5171938\\Downloads\\spring-boot-generate-display-qr-code-1.0.0\\resources\\templates\\"+ entity.get("COVAX Unique System Identifier (EPI)")+".pdf";
		
		List <TrackedEntity> listItems  = new ArrayList<TrackedEntity>(entity.entrySet());
		 JSONObject jsonObject = new JSONObject();
		 entity.put("vaccines", vaccineData);
		 jsonObject.put("Data",entity);

		 System.out.println("JSON : " + jsonObject);
		 
		 try {
	         FileWriter file = new FileWriter("C:\\Users\\b5171938\\Documents\\output.json");
	         file.write(jsonObject.toJSONString());
	         file.close();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
		 
		 
		 
		
		System.out.println(listItems);
		
		JRBeanCollectionDataSource itemsJRBean =  new JRBeanCollectionDataSource(listItems);
		
		Map<String, Object> parameters = new HashMap <String, Object>();
		parameters.put("collectionBeamParam", itemsJRBean);
		
		InputStream inputS = new FileInputStream(new File("C:\\Users\\b5171938\\Downloads\\spring-boot-generate-display-qr-code-1.0.0\\resources\\fenyaCovid7.jrxml"));
		
		JasperDesign jasperDesigner = JRXmlLoader.load(inputS);
		
        JasperReport jasperReport  =  JasperCompileManager.compileReport(jasperDesigner);
				
        JasperPrint  JasperPrint = JasperFillManager.fillReport(jasperReport,parameters,new JREmptyDataSource());
        
        
        //output to pdf
        OutputStream OutputStream = new FileOutputStream(new File(outputFile));
        
        JasperExportManager.exportReportToPdfStream(JasperPrint,OutputStream);
        
        
        JasperViewer.viewReport(JasperPrint);
        
		return "index";
	}

	
	

    @Autowired
    private QRCodeService qrCodeService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/showQRCode")
    public String showQRCode(String qrContent, Model model) throws UnsupportedEncodingException {
   
        model.addAttribute("qrCodeContent", "/generateQRCode?qrContent=" + URLEncoder.encode(qrContent, "UTF-8"));
        return "show-qr-code";
    }

    @GetMapping("/generateQRCode")
    public void generateQRCode(@RequestParam(required = false) String qrContent, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        byte[] qrCode = qrCodeService.generateQRCode(qrContent, 500, 500);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(qrCode);
    }
    
    
    
    @RequestMapping("/getEntityInfo/{identity}/{mobile}")
    public String makeTemplate(@PathVariable String identity, @PathVariable String mobile ) throws JRException {
    	DataRestClient dataRestClient  = new DataRestClient();
    	try {
			Map trackedEntityMap  = dataRestClient.getTrackedEntity(identity,mobile);
			System.out.println(trackedEntityMap.get("uid"));
			JSONArray  vdata = dataRestClient.getVaccinationData(trackedEntityMap.get("uid").toString());
			printPdf(vdata, trackedEntityMap);
		} catch (RestClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    
        return "index";
    }
    
    @RequestMapping("/getVaccineInfo/{uid}")
    public String requetVacInfo(@PathVariable String uid) {
    	DataRestClient dataRestClient  = new DataRestClient();
    	try {
			JSONArray map  = dataRestClient.getVaccinationData(uid);
			System.out.println(map);
		} catch (RestClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
    
        return "index";
    }
    
    
}
