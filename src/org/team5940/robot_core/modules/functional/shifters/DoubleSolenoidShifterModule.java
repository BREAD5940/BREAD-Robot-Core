package org.team5940.robot_core.modules.functional.shifters;

import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.SimpleOwnableModule;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
* Implementation of ShifterModule to shift gears on the robot.
* @author Alexander Loeffler
*/

public class DoubleSolenoidShifterModule extends SimpleOwnableModule implements ShifterModule{
	
	private DoubleSolenoid solenoid;
	private short currentGear = 0;
	
	/**
	 * Creates a new DoubleSolenoidShifterModule object.
	 * @param name The name of the shifter you are creating.
	 * @param logger The log to save to.
	 * @param chan1 The integer value of high gear.
	 * @param chan2 The integer value of low gear.
	 */
	public DoubleSolenoidShifterModule(String name, LoggerModule logger, int canPort, int chan1, int chan2)
			throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
		this.solenoid = new DoubleSolenoid(canPort, chan1, chan2);
	}

	/**
	 * Initializes the shifter to gear 0.
	 * 
	 * Super docs:
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
		setShift(0);
	}

	@Override
	public void shutDown() {
		
	}
	
	@Override
	public void setShift(int gear) throws IllegalArgumentException {
		if(gear == 0) {
			this.solenoid.set(DoubleSolenoid.Value.kForward);
			currentGear=0;
		}else if (gear == 1) {
			this.solenoid.set(DoubleSolenoid.Value.kReverse);
			currentGear=1;
		}else {
			throw new IllegalArgumentException("Gear out of bounds!");
		}
	}

	@Override
	public int getShift() {
		return currentGear;
	}

	@Override
	public int getNumberOfGears() {
		return 2;
	}

}
