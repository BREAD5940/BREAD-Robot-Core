package org.team5940.robot_core.modules.actuators.pistons;

import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.AbstractOwnableModule;
import org.team5940.robot_core.modules.ownable.ThreadUnauthorizedException;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
* Implementation of {@link PistonModule} that uses a DoubleSolenoid.
* @author Amit Harlev
*/

public class DoubleSolenoidPistonModule extends AbstractOwnableModule implements PistonModule {
	
	/**
	 * Stores this' DoubleSolenoid.
	 */
	private final DoubleSolenoid solenoid;
	
	/**
	 * Stores the last set state.
	 */
	private boolean state = false;
	
	/**
	 * Creates a new DoubleSolenoidPistonModule object. Does not force initialization contracted but assumes it.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param solenoid The solenoid to use for this. You can flip the port numbers to make extending and contracting work correctly.
	 * @throws IllegalArgumentException Thrown if any argument is null.
	 */
	public DoubleSolenoidPistonModule(String name, LoggerModule logger, DoubleSolenoid solenoid)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, DoubleSolenoidPistonModule.class, solenoid);
		this.solenoid = solenoid;
		this.logger.log(this, "Initialized DoubleSolenoidPistonModule", solenoid);
		this.logger.logInitialization(this, DoubleSolenoidPistonModule.class);
	}

	@Override
	public synchronized boolean getPistonState() {
		this.logger.logGot(this, "Piston State", this.state);
		return this.state;
	}

	@Override
	public synchronized void setPistonState(boolean state) throws ThreadUnauthorizedException {
		this.doAccessibilityCheck();
		this.logger.logSettingPrimitiveArgs(this, "Piston State", state);
		if(state) this.solenoid.set(Value.kForward);
		else this.solenoid.set(Value.kReverse);
		this.state = state;
	}

}
