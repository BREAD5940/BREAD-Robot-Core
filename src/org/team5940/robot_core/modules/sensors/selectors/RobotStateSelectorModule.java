package org.team5940.robot_core.modules.sensors.selectors;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * An implementation of {@link SelectorModule} that gives you the current state of a RobotBase:
 * -1 - Disabled
 * 0 - Autonomous
 * 1 - Operator Control
 * 2 - Test
 * @author David Boles
 *
 */
public class RobotStateSelectorModule extends AbstractModule implements SelectorModule {

	private final RobotBase robot;
	
	public RobotStateSelectorModule(String name, LoggerModule logger, RobotBase robot)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, RobotStateSelectorModule.class, robot);
		this.robot = robot;
		this.logger.logInitialization(this, RobotStateSelectorModule.class, robot);
	}

	@Override
	public int getNumberOfStates() {
		this.logger.logGot(this, "Number Of States", 3);
		return 3;
	}

	@Override
	public int getCurrentState() {
		int out = -1;
		if(this.robot.isTest()) out = 2;
		if(this.robot.isOperatorControl()) out = 1;
		if(this.robot.isAutonomous()) out = 0;
		if(this.robot.isDisabled()) out = -1;
		this.logger.logGot(this, "Current State", out);
		return out;
		
	}

}
