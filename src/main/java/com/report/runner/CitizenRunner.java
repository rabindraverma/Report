package com.report.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.report.binding.CitizenPlan;
import com.report.repo.CitizenPlanRepository;

@Component
public class CitizenRunner implements ApplicationRunner{
	@Autowired
	private CitizenPlanRepository repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		CitizenPlan c1=new CitizenPlan();
		c1.setCname("RajKrishna");
		c1.setCemail("raj@gmail.com");
		c1.setGender("Male");
		c1.setPhno(9898787878l);
		c1.setSsn(776767l);
		c1.setPlanName("SNAP");
		c1.setPlanStatus("Approved");
		
		CitizenPlan c2=new CitizenPlan();
		c2.setCname("Krishna");
		c2.setCemail("krishna@gmail.com.com");
		c2.setGender("Male");
		c2.setPhno(8988787878l);
		c2.setSsn(756767l);
		c2.setPlanName("SNAP");
		c2.setPlanStatus("Denied");
		
		CitizenPlan c3=new CitizenPlan();
		c3.setCname("Rajendra");
		c3.setCemail("Rajendra@gmail.com.com");
		c3.setGender("Male");
		c3.setPhno(89898878l);
		c3.setSsn(798767l);
		c3.setPlanName("CAP");
		c3.setPlanStatus("Pending");
		
		CitizenPlan c4=new CitizenPlan();
		c4.setCname("Pooja");
		c4.setCemail("pooja@gmail.com.com");
		c4.setGender("Female");
		c4.setPhno(98898878l);
		c4.setSsn(79867l);
		c4.setPlanName("CAP");
		c4.setPlanStatus("Approved");
		
		List<CitizenPlan> asList = Arrays.asList(c1,c2,c3,c4);
		
		repo.saveAll(asList);
		
	}

}
