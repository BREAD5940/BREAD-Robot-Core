package org.team5940.robot_core.modules.sensors.selectors;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An implementation of {@link SelectorModule} that allows you to select between multiple strings on SmartDashboard. -1 is only returned if there is no selection from SmartDashboard.
 * @author deb
 *
 */
public class SmartDashboardSelectorModule extends AbstractModule implements SelectorModule {

	/**
	 * The SendableChooser on the SmartDashboard that this uses to determine its state.
	 */
	private final SendableChooser<Integer> chooser;
	
	/**
	 * The number of options there are.
	 */
	private final int number;
	
	/**
	 * Initializes a new {@link SmartDashboardSelectorModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param options The options to select between. Selector states map to the indices of this array.
	 * @param defaultOption The index of the default option for the SmartDashboard radio buttons.
	 * @throws IllegalArgumentException Thrown if any argument is null, any option is null, or the defaultOption >= options.
	 */
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
		SmartDashboard.putData(name, this.chooser);
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
