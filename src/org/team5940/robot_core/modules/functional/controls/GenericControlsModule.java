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
	
	public GenericControlsModule(String name, LoggerModule logger) throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
	}
	
	/**
	 * Stores the {@link GenericHID}s for the axes.
	 */
	private Hashtable<String, GenericHID> axisControlDevices = new Hashtable<>();
	
	/**
	 * Stores the raw axis numbers for the axes.
	 */
	private Hashtable<String, Integer> axisControlAxes = new Hashtable<>();
	
	/**
	 * Stores the {@link GenericHID}s for the buttons.
	 */
	private Hashtable<String, GenericHID> buttonControlDevices = new Hashtable<>();
	
	/**
	 * Stores the raw button numbers for the buttons.
	 */
	private Hashtable<String, Integer> buttonControlButtons = new Hashtable<>();
	
	private Hashtable<String, Boolean> buttonIsInverted=new Hashtable<>();
	
	private Hashtable<String, Boolean> axesIsInverted=new Hashtable<>();
	
	@Override
	public synchronized void initialize() {
		//Nothing to do here...
	}

	@Override
	public synchronized void shutDown() {
		//Nothing to do here...
	}

	@Override
	public synchronized double getAxis(String axis) throws IllegalArgumentException {
		if(this.axisAvailable(axis)) {
			if(!this.axesIsInverted.get(axis)) return this.axisControlDevices.get(axis).getRawAxis(this.axisControlAxes.get(axis));
			if(this.axesIsInverted.get(axis))return -1*(this.axisControlDevices.get(axis).getRawAxis(this.axisControlAxes.get(axis)));
		}throw new IllegalArgumentException("Axis not found!");
	}

	@Override
	public synchronized boolean axisAvailable(String axis) {
		return axisControlDevices.contains(axis);
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
		if(axisName == null || device == null) throw new IllegalArgumentException("Argument null!");
		this.logger.log(this, "Adding Axis", new Object[] {device, rawAxis});
		this.axisControlDevices.put(axisName, device);
		this.axisControlAxes.put(axisName, rawAxis);
		this.axesIsInverted.put(axisName, isInverted);
		return this;
	}
	
	@Override
	public synchronized boolean getButton(String button) throws IllegalArgumentException {
		if(this.buttonAvailable(button)) {
			if(!this.buttonIsInverted.get(button)) return this.buttonControlDevices.get(button).getRawButton(this.buttonControlButtons.get(button));
			if(this.buttonIsInverted.get(button)) return !this.buttonControlDevices.get(button).getRawButton(this.buttonControlButtons.get(button));
		}throw new IllegalArgumentException("Button not found!");
	}

	@Override
	public synchronized boolean buttonAvailable(String button) {
		return buttonControlDevices.contains(button);
	}
	
	/** 
	 * Adds a button to this or replaces an existing one. Returns this for chaining.
	 * @param buttonName The name of the button you are adding. Identifier used in {@link GenericControlsModule#getButton(String)}
	 * @param device The GenericHID device to read from.
	 * @param rawButton The raw axis number to be read from device.
	 * @param isInvert Boolean for is inverted buttons.
	 * @return this. Allows chaining of adds directly after initialization. (e.g. new GenericControlsModule(...).addAxis(...).addAxis(...).addButton(...)...;)
	 */
	public synchronized GenericControlsModule addButton(String buttonName, GenericHID device, int rawButton, boolean isInvert) {
		if(buttonName == null || device == null) throw new IllegalArgumentException("Argument null!");
		this.logger.log(this, "Adding Button", new Object[] {device, rawButton});
		this.buttonControlDevices.put(buttonName, device);
		this.buttonIsInverted.put(buttonName, isInvert);
		this.buttonControlButtons.put(buttonName, rawButton);
		return this;
	}

	@Override
	public TestResult runTest(TestRunnerModule testRunner) throws IllegalArgumentException {
		if(testRunner == null) throw new IllegalArgumentException("testRunner null");
		boolean testsGood = true;
		
		try {
			for(String axis : this.axisControlAxes.keySet()) {
				while(!testRunner.getNewReturn()) testRunner.promptText("Is " + axis + " doing what it should (y/n): " + this.getAxis(axis));
				if(testRunner.getReturnedText().equals("n")) testsGood = false;
			}
			
			for(String button : this.buttonControlButtons.keySet()) {
				while(!testRunner.getNewReturn()) testRunner.promptText("Is " + button + " doing what it should (y/n): " + this.getButton(button));
				if(testRunner.getReturnedText().equals("n")) testsGood = false;
			}
		}catch (Exception e) {
			return TestResult.ERROR;
		}
		
		if(testsGood) return TestResult.SUCCESSFUL;
		else return TestResult.FAILED;
	}

}
