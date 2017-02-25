package org.team5940.robot_core.modules.actuators.shifters;

import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
* Implementation of ShifterModule using a DoubleSolenoid.
* @author Alexander Loeffler
* 
*/
public class DoubleSolenoidShifterModule extends AbstractOwnableModule implements ShifterModule {
	
	/**
	 * Stores this' solenoid.
	 */
	private final DoubleSolenoid solenoid;
	
	/**
	 * Stores the last gear this was set to.
	 */
	private int gear = 0;
	
	/**
	 * Creates a new DoubleSolenoidShifterModule object. Does not force initialization low but assumes it.
	 * @param name The name of the shifter you are creating.
	 * @param logger This' logger.
	 * @param solenoid The solenoid to use for this. You can flip the port numbers to make the gears work correctly.
	 */
	public DoubleSolenoidShifterModule(String name, LoggerModule logger, DoubleSolenoid solenoid)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, DoubleSolenoidShifterModule.class, solenoid);
		this.solenoid = solenoid;
		this.logger.logInitialization(this, DoubleSolenoidShifterModule.class, solenoid);
	}
	
	@Override
	public synchronized void setShifterGear(int gear) throws IllegalArgumentException, ThreadUnauthorizedException {
		this.doAccessibilityCheck();
		if(gear == 1) {
			this.logger.logSettingPrimitiveArgs(this, "Shifter Gear", gear);
			this.solenoid.set(DoubleSolenoid.Value.kForward);
			this.gear = gear;
		}else if (gear == 0) {
			this.logger.logSettingPrimitiveArgs(this, "Shifter Gear", gear);
			this.solenoid.set(DoubleSolenoid.Value.kReverse);
			this.gear = gear;
		}else {
			this.logger.failSettingIllegal(this, "Shifter Gear", "Out Of Bounds", gear);
		}
	}

	@Override
	public synchronized int getShifterGear() {
		this.logger.logGot(this, "Shifter Gear", this.gear);
		return this.gear;
	}

	@Override
	public synchronized int getNumberOfGears() {
		this.logger.logGot(this, "Number Of Gears", 2);
		return 2;
	}

}
