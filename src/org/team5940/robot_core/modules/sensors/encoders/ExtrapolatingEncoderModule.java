package org.team5940.robot_core.modules.sensors.encoders;

import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.actuators.shifters.ShifterModule;
import org.team5940.robot_core.modules.logging.LoggerModule;

//TODO docs
public class ExtrapolatingEncoderModule extends AbstractModule implements EncoderModule {

	private final EncoderModule encoder;
	private final ShifterModule shifter;
	private final double[] scalers;
	
	public ExtrapolatingEncoderModule(String name, LoggerModule logger, EncoderModule encoder, ShifterModule shifter, double[] scalers)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>().chainPut(encoder).chainPut(shifter), logger);
		this.logger.checkInitializationArgs(this, ExtrapolatingEncoderModule.class, new Object[]{encoder, shifter, scalers});
		if(shifter.getNumberOfGears() != scalers.length)
			this.logger.failInitializationIllegal(this, ExtrapolatingEncoderModule.class, "Unequal Quatities", new Object[]{encoder, shifter, scalers});
		this.encoder = encoder;
		this.shifter = shifter;
		this.scalers = scalers;
		this.logger.logInitialization(this, ExtrapolatingEncoderModule.class, new Object[]{encoder, shifter, scalers});
	}
	
	public ExtrapolatingEncoderModule(String name, LoggerModule logger, EncoderModule encoder, double scaler)
			throws IllegalArgumentException {
		this(name, logger, encoder, ShifterModule.INERT_SHIFTER, new double[]{scaler});
	}

	@Override
	public double getRotationalVelocity() {
		int gear = this.shifter.getShifterGear();
		double scaler = this.scalers[gear];
		double encV = this.encoder.getRotationalVelocity();
		double out = encV * scaler;
		this.logger.logGot(this, "Rotational Velocity", new Object[]{out, scaler, encV});
		return out;
	}

	@Override
	public double getRotationalPosition() {
		int gear = this.shifter.getShifterGear();
		double scaler = this.scalers[gear];
		double encP = this.encoder.getRotationalVelocity();
		double out = encP * scaler;
		this.logger.logGot(this, "Rotational Position", new Object[]{out, scaler, encP});
		return out;
	}

}
