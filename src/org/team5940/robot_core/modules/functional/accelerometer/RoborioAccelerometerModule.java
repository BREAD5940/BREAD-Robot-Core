package org.team5940.robot_core.modules.functional.accelerometer;

import org.team5940.robot_core.modules.ModuleHashTable;
import org.team5940.robot_core.modules.SimpleModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.testable.TestRunnerModule;
import org.team5940.robot_core.modules.testable.TestableModule;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.ADXL345_I2C.Axes;

/**
 * Implementation of AccelerometerModule for the built in roborio accelerometer.
 * 
 * @author Michael Bentley
 *
 */
public class RoborioAccelerometerModule extends SimpleModule implements AccelerometerModule, TestableModule {

	/**
	 * Stores the roborio accelerometer.
	 */
	BuiltInAccelerometer accelerometer = new BuiltInAccelerometer();

	/**
	 * The direction of the roborio.
	 */
	RoborioAxisSwitch axisSwap;

	/**
	 * the inversion of the x axis.
	 */
	int xAxisInversion;

	/**
	 * the inversion of the y axis.
	 */
	int yAxisInversion;

	/**
	 * the inversion of the z axis.
	 */
	int zAxisInversion;

	/**
	 * Swaps the axis order of the roborio accelerometer. Standard orientation
	 * would be XYZ. If the swap were to be YZX and you were to retrieve the y
	 * axis you would get what the x axis reads.
	 */
	public enum RoborioAxisSwitch {

		/**
		 * Swaps x to y and y to z and z to x.
		 */
		YZX,

		/**
		 * Swaps y and x.
		 */
		YXZ,

		/**
		 * Swaps nothing.
		 */
		XYZ,

		/**
		 * Swaps z and y.
		 */
		XZY,

		/**
		 * Swaps x and z.
		 */
		ZYX,

		/**
		 * Swaps y to x and x to z and z to y.
		 */
		ZXY

	}

	/**
	 * Sets the axis that are swapped.
	 * @param axisSwap the axis to swap to.
	 * @throws IllegalArgumentException If axisSwap is null.
	 */
	public void setAxisSwap(RoborioAxisSwitch axisSwap) throws IllegalArgumentException {
		if(axisSwap == null) {
			this.logger.vError(this, "Axis Swap With Null");
		}
		this.axisSwap = axisSwap;
		this.logger.vLog(this, "Created RoborioAccelerometerModule", axisSwap);
	}

	/**
	 * Gets the axis swap.
	 * 
	 * @return the current axis swap.
	 */
	public RoborioAxisSwitch getAxisSwap() {
		this.logger.vLog(this, "Getting Axis Swap", this.axisSwap);
		return this.axisSwap;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param name
	 *            the name of the module.
	 * @param logger
	 *            the logger for the module.
	 * @param axisSwap
	 *            the axis that are swapped which happens before inversion. 
	 * @param xAxisInverted
	 *            the inversion of the x axis.
	 * @param yAxisInverted
	 *            the inversion of the y axis.
	 * @param zAxisInverted
	 *            the inversion of the z axis.
	 */
	public RoborioAccelerometerModule(String name, LoggerModule logger, RoborioAxisSwitch axisSwap,
			boolean xAxisInverted, boolean yAxisInverted, boolean zAxisInverted) {
		super(name, new ModuleHashTable<>(), logger);
		this.logger.log(this, "Creating RoborioAccelerometerModule", new Object[]{axisSwap, xAxisInverted, yAxisInverted, zAxisInverted});
		this.axisSwap = axisSwap;
		if (xAxisInverted) {
			this.xAxisInversion = -1;
		} else {
			this.xAxisInversion = 1;
		}
		if (yAxisInverted) {
			this.yAxisInversion = -1;
		} else {
			this.yAxisInversion = 1;
		}
		if (zAxisInverted) {
			this.zAxisInversion = -1;
		} else {
			this.zAxisInversion = 1;
		}
	}

	@Override
	public void initialize() {
		this.logger.log(this, "Initializing");
	}

	@Override
	public void shutDown() {
		this.logger.log(this, "Shutting Down");
	}

	@Override
	public double getX() {
		double out;
		switch (axisSwap) {
		case XYZ:
		case XZY:
			out = this.accelerometer.getX() * this.xAxisInversion;
			this.logger.vLog(this, "Getting X", out);
			return out;
		case ZYX:
		case ZXY:
			out = this.accelerometer.getZ() * this.zAxisInversion;
			this.logger.vLog(this, "Getting X", out);
			return out;
		default:
			out = this.accelerometer.getY() * this.yAxisInversion;
			this.logger.vLog(this, "Getting X", out);
			return out;
		}
	}

	@Override
	public double getY() {
		double out;
		switch (axisSwap) {
		case XYZ:
		case ZYX:
			out = this.accelerometer.getY() * this.yAxisInversion;
			this.logger.vLog(this, "Getting Y", out);
			return out;
		case YZX:
		case XZY:
			out = this.accelerometer.getZ() * this.zAxisInversion;
			this.logger.vLog(this, "Getting Y", out);
			return out;
		default:
			out = this.accelerometer.getX() * this.xAxisInversion;
			this.logger.vLog(this, "Getting Y", out);
			return out;
		}
	}

	@Override
	public double getZ() {
		double out;
		switch (axisSwap) {
		case XYZ:
		case YXZ:
			out = this.accelerometer.getZ() * this.zAxisInversion;
			this.logger.vLog(this, "Getting Z", out);
			return out;
		case YZX:
		case ZYX:
			out = this.accelerometer.getX() * this.xAxisInversion;
			this.logger.vLog(this, "Getting Z", out);
			return out;
		default:
			out = this.accelerometer.getY() * this.yAxisInversion;
			this.logger.vLog(this, "Getting Z", out);
			return out;
		}
	}

	@Override
	public TestResult runTest(TestRunnerModule testRunner) throws IllegalArgumentException {
		if (testRunner == null)
			throw new IllegalArgumentException("testRunner null");
		boolean testSuccess = true;
		try {
			while (!testRunner.getNewReturn())
				testRunner.promptText("Is this showing the proper x axis acceleration (y/n): " + this.getX());
			if (testRunner.getReturnedText().equals("n"))
				testSuccess = false;
			while (!testRunner.getNewReturn())
				testRunner.promptText("Is this showing the proper y axis acceleration (y/n): " + this.getY());
			if (testRunner.getReturnedText().equals("n"))
				testSuccess = false;
			while (!testRunner.getNewReturn())
				testRunner.promptText("Is this showing the proper z axis acceleration (y/n): " + this.getZ());
			if (testRunner.getReturnedText().equals("n"))
				testSuccess = false;

		} catch (Exception e) {
			return TestResult.ERROR;
		}
		if (testSuccess)
			return TestResult.SUCCESSFUL;
		return TestResult.FAILED;
	}

}
