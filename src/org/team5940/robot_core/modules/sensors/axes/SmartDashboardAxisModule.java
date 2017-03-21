package org.team5940.robot_core.modules.sensors.axes;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO docs
public class SmartDashboardAxisModule extends AbstractModule implements AxisModule {

	private final String key;
	
	public SmartDashboardAxisModule(String name, LoggerModule logger, String key)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, SmartDashboardAxisModule.class, key);
		this.key = key;
		SmartDashboard.putNumber(key, 0);
		this.logger.logInitialization(this, SmartDashboardAxisModule.class, key);
	}

	@Override
	public double getAxis() {
		double out = SmartDashboard.getNumber(this.key, 0);
		this.logger.logGot(this, "Axis", out);
		return out;
	}

}