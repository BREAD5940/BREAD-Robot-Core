package org.team5940.robot_core.modules.functional;

import org.team5940.robot_core.modules.ownable.OwnableModule;

/**
 * Represents a drivetrain with two fixed sets of wheels. 
 * For anyone who uses this: Make sure it extends ShifterModule if it can.
 * 
 * @author Noah Sturgeon + Dean Brown + artwork by Ethan Shedd.
 *
 */
public interface TankDrivetrainModule extends OwnableModule {
	
/**
 * Updates a tank drivetrain for tank control, invidually moving each set of wheels.
 * @param Left the speed you want for the left side. -1 to 1, inclusive for both.
 * @param Right the speed you want for the right side. -1 to 1, inclusive for both.
 */
 	public void updateTank(double left, double right);
	
	/**
	 * Updates a tank drivetrain for arcade control, multiplexing straight and turning.
	 * 
	 * @param forward The speed of the vertical speed. -1 to 1, inclusive for both.
	 * @param right The speed of the horizontal speed. -1 to 1, inclusive for both.
	 */
	public void updateArcade(double forward, double right);
}
