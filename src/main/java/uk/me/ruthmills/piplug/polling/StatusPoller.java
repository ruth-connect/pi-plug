package uk.me.ruthmills.piplug.polling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.me.ruthmills.piplug.service.PiPlugService;

@Component
public class StatusPoller {
	
	@Autowired
	private PiPlugService piPlugService;

	@Scheduled(cron = "*/1 * * * * *")
	public void updateStatus() {
		piPlugService.updateStatus();
	}
}
