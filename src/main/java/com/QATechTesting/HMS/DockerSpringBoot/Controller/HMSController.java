package com.QATechTesting.HMS.DockerSpringBoot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.QATechTesting.HMS.DockerSpringBoot.DAO.PatientRepo;
import com.QATechTesting.HMS.DockerSpringBoot.Model.HMS;

@Controller
public class HMSController {
	@Autowired          
	PatientRepo repo;  
	
	@RequestMapping("/")   
	public String home()      {
		return "Home.jsp";     
	}
	
	@RequestMapping("/addPatient")     
	public String addPatient(HMS patient)  {
		System.out.println("patientname----------"+patient);
		repo.save(patient);            
		return "CreatePatient.jsp";		
	}
	
	@RequestMapping("/findPatientByID")
	public ModelAndView findPatientByID(@RequestParam int patientid)  {
		ModelAndView MV= new ModelAndView("ShowPatinet.jsp");  
		HMS t= repo.findById(patientid).orElse(null);    
		MV.addObject("hms",t); 
		return MV;	
	}
	
}


