package org.team5940.robot_core.modules.sensors.selectors;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO all docs
public class SmartDashboardSelectorModule extends AbstractModule implements SelectorModule {

	private final SendableChooser<Integer> chooser;
	private final int number;
	
	public SmartDashboardSelectorModule(String name, LoggerModule logger, String[] options, int defaultOption)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		logger.checkInitializationArgs(this, SmartDashboardSelectorModule.class, new Object[]{options, defaultOption});
		for(String option : options)
			if(option == null)
				this.logger.failInitializationIllegal(this, SmartDashboardSelectorModule.class, "Null Option", new Object[]{options, defaultOption});
		if(defaultOption >= options.length || defaultOption < 0)
			this.logger.failInitializationIllegal(this, SmartDashboardSelectorModule.class, "Invalid Default", new Object[]{options, defaultOption});
		this.number = options.length;
		this.chooser = new SendableChooser<>();
		chooser.addDefault(options[defaultOption], defaultOption);
		for(int i = 0; i < options.length; i++) {
			if(i != defaultOption)
				chooser.addObject(options[i], i);
		}
		SmartDashboard.putData("Auto Selector", this.chooser);
		logger.logInitialization(this, SmartDashboardSelectorModule.class, new Object[]{options, defaultOption});
	}

	@Override
	public int getNumberOfStates() {
		this.logger.logGot(this, "Number Of States", this.number);
		return this.number;
	}

	@Override
	public int getCurrentState() {
		Integer state = this.chooser.getSelected();
		if(state == null) state = -1;
		this.logger.logGot(this, "Current State", state);
		return state;
	}

}
