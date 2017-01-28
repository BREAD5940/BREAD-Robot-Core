package org.team5940.robot_core.modules.functional.controls;

import java.util.Hashtable;

import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.testable.TestRunnerModule;
import org.team5940.robot_core.modules.testable.TestableModule;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Implementation of ControlsModule for any number of GenericHIDs, buttons, and axes.
 * @author David Boles
 *
 */
public class GenericControlsModule extends SimpleModule implements ControlsModule, TestableModule {
	

	
	/**
	 * Stores the {@link GenericHID}s for the axes.
	 */
	private Hashtable<String, GenericHID> axisControlDevices = new Hashtable<>();
	
	/**
	 * Stores the raw axis numbers for the axes.
	 */
	private Hashtable<String, Integer> axisControlAxes = new Hashtable<>();
	
	/**
	 * Stores the inversion for the axes.
	 */
	private Hashtable<String, Boolean> axesIsInverted=new Hashtable<>();
	
	/**
	 * Stores the {@link GenericHID}s for the buttons.
	 */
	private Hashtable<String, GenericHID> buttonControlDevices = new Hashtable<>();
	
	/**
	 * Stores the raw button numbers for the buttons.
	 */
	private Hashtable<String, Integer> buttonControlButtons = new Hashtable<>();
	
	/**
	 * Stores the inversion for the buttons.
	 */
	private Hashtable<String, Boolean> buttonIsInverted=new Hashtable<>();
	
	public GenericControlsModule(String name, LoggerModule logger) throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
		this.logger.log(this, "Created GenericControlsModule");
	}
	
	@Override
	public synchronized void initialize() {
		this.logger.log(this, "Initializing");
	}

	@Override
	public synchronized void shutDown() {
		this.logger.log(this, "Shutting Down");
	}

	@Override
	public synchronized double getAxis(String axis) throws IllegalArgumentException {
		if(this.axisAvailable(axis)) {
			double out = this.axisControlDevices.get(axis).getRawAxis(this.axisControlAxes.get(axis));
			if(this.axesIsInverted.get(axis)) out = -1*(this.axisControlDevices.get(axis).getRawAxis(this.axisControlAxes.get(axis)));
			this.logger.vLog(this, "Axis Accessed", axis + ": " + out);
		}
		this.logger.vError(this, "Unavailable Axis Accessed", axis);
		throw new IllegalArgumentException("Axis not found!");
	}

	@Override
	public synchronized boolean axisAvailable(String axis) {
		boolean out = axisControlDevices.contains(axis);
		this.logger.vLog(this, "Axis Available", axis + ": " + out);
		return out;
	}

	/**
	 * Adds an axis to this or replaces the existing one. Returns this for chaining.
	 * @param axisName The name of the axis you are adding. Identifier used in {@link GenericControlsModule#getAxis(String)}
	 * @param device The GenericHID device to read from.
	 * @param rawAxis The raw axis number to be read from device.
	 * @param isInverted A boolean value for if the axis is inverted.
	 * @return this. Allows chaining of adds directly after initialization. (e.g. new GenericControlsModule(...).addAxis(...).addAxis(...).addButton(...)...;)
	 * @throws IllegalArgumentException If any inputs are null.
	 */
	public synchronized GenericControlsModule addAxis(String axisName, GenericHID device, int rawAxis, boolean isInverted) throws IllegalArgumentException {
		if(axisName == null || device == null) {
			this.logger.vError(this, "Adding Axis With Null", new Object[]{axisName, device, isInverted});
			throw new IllegalArgumentException("Argument null!");
		}
		this.logger.vLog(this, "Adding Axis", new Object[]{axisName, device, isInverted});
		this.axisControlDevices.put(axisName, device);
		this.axisControlAxes.put(axisName, rawAxis);
		this.axesIsInverted.put(axisName, isInverted);
		return this;
	}
	
	@Override
	public synchronized boolean getButton(String button) throws IllegalArgumentException {
		if(this.buttonAvailable(button)) {
			boolean out = this.buttonControlDevices.get(button).getRawButton(this.buttonControlButtons.get(button));;
			if(this.buttonIsInverted.get(button)) out = !out;
			this.logger.vLog(this, "Button Accessed", button + ": " + out);
			return out;
		}
		this.logger.vError(this, "Unavailable Button Accessed", button);
		throw new IllegalArgumentException("Button not found!");
	}

	@Override
	public synchronized boolean buttonAvailable(String button) {
		boolean out = buttonControlDevices.contains(button);
		this.logger.vLog(this, "Button Available", button + ": " + out);
		return out;
	}
	
	/** 
	 * Adds a button to this or replaces an existing one. Returns this for chaining.
	 * @param buttonName The name of the button you are adding. Identifier used in {@link GenericControlsModule#getButton(String)}
	 * @param device The GenericHID device to read from.
	 * @param rawButton The raw axis number to be read from device.
	 * @param isInverted Boolean for is inverted buttons.
	 * @return this. Allows chaining of adds directly after initialization. (e.g. new GenericControlsModule(...).addAxis(...).addAxis(...).addButton(...)...;)
	 */
	public synchronized GenericControlsModule addButton(String buttonName, GenericHID device, int rawButton, boolean isInverted) {
		if(buttonName == null || device == null) {
			this.logger.vError(this, "Adding Button With Null", new Object[]{buttonName, device, isInverted});
			throw new IllegalArgumentException("Argument null!");
		}
		this.logger.vLog(this, "Adding Button", new Object[] {device, rawButton, isInverted});
		this.buttonControlDevices.put(buttonName, device);
		this.buttonIsInverted.put(buttonName, isInverted);
		this.buttonControlButtons.put(buttonName, rawButton);
		return this;
	}

	@Override
	public TestResult runTest(TestRunnerModule testRunner) throws IllegalArgumentException {
		if(testRunner == null) {
			this.logger.vError(this, "Test Run With Null testRunner");
			throw new IllegalArgumentException("testRunner null");
		}
		boolean testsGood = true;
		
		try {
			for(String axis : this.axisControlAxes.keySet()) {
				while(!testRunner.getNewReturn()) testRunner.promptText("Is " + axis + " doing what it should (y/n): " + this.getAxis(axis));
				if(testRunner.getReturnedText().equals("n")) {
					this.logger.vLog(this, "Axis Test Failed", axis);
					testsGood = false;
				}else {
					this.logger.vLog(this, "Axis Test Passed", axis);
				}
			}
			
			for(String button : this.buttonControlButtons.keySet()) {
				while(!testRunner.getNewReturn()) testRunner.promptText("Is " + button + " doing what it should (y/n): " + this.getButton(button));
				if(testRunner.getReturnedText().equals("n")) {
					this.logger.vLog(this, "Button Test Failed", button);
					testsGood = false;
				}else {
					this.logger.vLog(this, "Button Test Passed", button);
				}
			}
		}catch (Exception e) {
			return TestResult.ERROR;
		}
		
		if(testsGood) return TestResult.SUCCESSFUL;
		else return TestResult.FAILED;
	}

}
