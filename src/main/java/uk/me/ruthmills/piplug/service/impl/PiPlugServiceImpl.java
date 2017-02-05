package uk.me.ruthmills.piplug.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import uk.me.ruthmills.piplug.service.PiPlugService;

@Service
public class PiPlugServiceImpl implements PiPlugService {
	
	private static final Pin[] gpioPins = { RaspiPin.GPIO_00,
							   				RaspiPin.GPIO_01,
							   				RaspiPin.GPIO_02,
							   				RaspiPin.GPIO_03,
							   				RaspiPin.GPIO_04,
							   				RaspiPin.GPIO_05,
							   				RaspiPin.GPIO_06,
							   				RaspiPin.GPIO_07 };

	private GpioController gpio;
	private List<GpioPinDigitalOutput> electricSockets = new ArrayList<>();
	private boolean[] electricSocketOn = new boolean[gpioPins.length];
	
	@PostConstruct
	public void initialise() {
		gpio = GpioFactory.getInstance();
		for (int i = 0; i < gpioPins.length; i++) {
			GpioPinDigitalOutput electricSocket = gpio.provisionDigitalOutputPin(gpioPins[i], "Channel " + i, PinState.HIGH);
			electricSocket.setShutdownOptions(true, PinState.HIGH);
			electricSockets.add(electricSocket);
			electricSocketOn[i] = false;
		}
	}

	@PreDestroy
	public void shutdown() {
		gpio.shutdown();
	}
	
	@Override
	public void off(int channel) {
		checkInRange(channel);
		electricSocketOn[channel] = false;
	}

	@Override
	public void on(int channel) {
		checkInRange(channel);
		electricSocketOn[channel] = true;
	}
	
	@Override
	public void updateStatus() {
		for (int i = 0; i < electricSockets.size(); i++) {
			if (electricSocketOn[i]) {
				electricSockets.get(i).low();
			} else {
				electricSockets.get(i).high();
			}
		}
	}
	
	private void checkInRange(int channel) {
		if (channel < 0 || channel >= electricSockets.size()) {
			throw new IndexOutOfBoundsException("Channel number is out of range: " + channel);
		}		
	}
}
