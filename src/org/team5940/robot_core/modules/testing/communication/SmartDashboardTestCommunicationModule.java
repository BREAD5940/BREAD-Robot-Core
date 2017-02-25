package org.team5940.robot_core.modules.testing.communication;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A {@link TestCommunicationModule} that uses SmartDashboard.
 * @author David Boles
 *
 */
public class SmartDashboardTestCommunicationModule extends AbstractModule implements TestCommunicationModule {

	/**
	 * Stores the SmartDashboard key for displaying text.
	 */
	private String displayKey;
	
	/**
	 * Stores the SmartDashboard key for receiving text.
	 */
	private String returnKey;
	
	/**
	 * 
	 * @param name This name.
	 * @param dependencies This' dependencies.
	 * @param logger This' logger.
	 * @param displayKey The SmartDashboard key for displaying text.
	 * @param returnKey The SmartDashboard key for receiving text.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public SmartDashboardTestCommunicationModule(String name, LoggerModule logger, String displayKey, String returnKey)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		
		this.logger.checkInitializationArgs(this, SmartDashboardTestCommunicationModule.class, new Object[]{displayKey, returnKey});
		
		this.displayKey = displayKey;
		this.returnKey = returnKey;
		
		this.logger.logInitialization(this, SmartDashboardTestCommunicationModule.class, new Object[]{displayKey, returnKey});
	}

	@Override
	public synchronized void displayText(String text) {
		this.logger.vLog(this, "Displaying Text", text);
		SmartDashboard.putString(this.displayKey, text);
	}

	@Override
	public synchronized boolean promptBoolean(String prompt) throws InterruptedException {
		String returned = this.promptText(prompt + " (y/n)");
		while(!returned.equalsIgnoreCase("y") && !returned.equalsIgnoreCase("n")) {
			this.logger.vError(this, "Returned Not Boolean", returned);
			returned = this.promptText(prompt + " (y/n !!!)");
		}
		
		if(returned.equalsIgnoreCase("y")) {
			this.logger.vLog(this, "Prompted For Boolean", new Object[]{prompt, true});
			return true;
		}else {
			this.logger.vLog(this, "Prompted For Boolean", new Object[]{prompt, false});
			return false;
		}
	}

	@Override
	public synchronized String promptText(String prompt) throws InterruptedException {
		SmartDashboard.putString(this.displayKey, prompt);
		SmartDashboard.putString(this.returnKey, "");
		
		while(SmartDashboard.getString(this.returnKey, "").equals(""))
			Thread.sleep(50);
		
		String out = SmartDashboard.getString(this.returnKey, "");
		this.logger.vLog(this, "Prompted For Text", new Object[]{prompt, out});
		return out;
	}

}
