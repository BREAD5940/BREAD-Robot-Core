package org.team5940.robot_core.modules.functional;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.SimpleOwnableModule;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
* Implementation of ShifterModule to shift gears on the robot.
* @author Alexander Loeffler
*/

public class DoubleSolenoidShifterModule extends SimpleOwnableModule implements ShifterModule{
	
	private DoubleSolenoid shifty_shifter;
	private boolean Fowardishigh;
	private short currentshift=0;
	/**
 * Creates a new DoubleSolenoidShifterModule Object.
 * @param name The name of the shifter you are creating.
 * @param logger The log to save to.
 * @param highGear The integer value of high gear.
 * @param lowGear The integer value of low gear.
 * @param fishigh Boolean value for if Foward is High gear.
 */
	public DoubleSolenoidShifterModule(String name, LoggerModule logger,int highGear, int lowGear, boolean fishigh)
			throws IllegalArgumentException {
		super(name, new ModuleHashTable<>(), logger);
		this.shifty_shifter= new DoubleSolenoid(highGear,lowGear);
		this.Fowardishigh=fishigh;
		gear0();
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void shutDown() {
		setShift(0);
	}
	
	@Override
	public void setShift(int gear) throws IllegalArgumentException {
		switch(gear){
		case 0: gear0();
				break;
		case 1:
			gear1();
			break;
		}
	}

	@Override
	public int getShift() {
		return currentshift;
	}

	@Override
	public int getNumberOfGears() {
		return 2;
	}
	
	private void gear0(){
		if(Fowardishigh){
			this.shifty_shifter.set(kFoward);
			
		}
		else{
			this.shifty_shifter.set(kReverse);
		}
		currentshift=0;
	}
	private void gear1(){
		if(Fowardishigh){
			this.shifty_shifter.set(kReverse);
			
		}
		else{
			this.shifty_shifter.set(kFoward);
		}
		currentshift=1;
	}

}
