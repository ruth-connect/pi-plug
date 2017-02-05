package uk.me.ruthmills.piplug.service;

public interface PiPlugService {
	
	public void off(int channel);
	
	public void on(int channel);
	
	public void updateStatus();
}
