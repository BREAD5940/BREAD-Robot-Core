package org.team5940.robot_core.modules.actuators.rotational;

import org.team5940.robot_core.modules.ownable.OwnableModule;

public interface RotationalVelocityActuatorModule extends OwnableModule {
	
	public void setRotationalVelocity(double velocity);
	
}
