package uk.me.ruthmills.piplug.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import uk.me.ruthmills.piplug.service.PiPlugService;

@Controller
@RequestMapping("/piplug")
public class PiPlugController {
	
	@Autowired
	private PiPlugService piPlugService;

	@RequestMapping(value = "{channel}/off", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void off(@PathVariable String channel) throws IOException {
		piPlugService.off(Integer.parseInt(channel));
	}

	@RequestMapping(value = "{channel}/on", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void on(@PathVariable String channel) throws IOException {
		piPlugService.on(Integer.parseInt(channel));
	}
}
