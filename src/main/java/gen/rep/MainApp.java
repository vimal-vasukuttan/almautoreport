package gen.rep;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import gen.rep.model.Defect;
import gen.rep.services.AlmServices;
import gen.rep.services.CreateExcelService;
import gen.rep.services.MailingService;

@Configuration
@EnableScheduling
public class MainApp {
	
	@Autowired
	AlmServices almService;
	
	@Autowired
	CreateExcelService excelService;
	
	@Autowired
	MailingService mailingService;
	

	/**
	 * Application is configured to run every Monday morning to retrieve defects
	 */
	@Scheduled(cron = "	0 0 8 ? * MON *")
	public void execute() {
		almService.authenticate();
		List<Defect> defects = almService.getDefects();
		excelService.createSheet(defects);
		String defectdetails = getString(defects);
		//mailingService.sendMail(defectdetails);		
	}
	
	private String getString(List<Defect> defects) {
		StringBuilder builder = new StringBuilder();
		for(Defect defect : defects) {
			builder.append(defect.getId() + "\t");
			builder.append(defect.getOwner() + "\t");
			builder.append(defect.getStatus() + "\n");
		}
		return builder.toString();
	}
}
