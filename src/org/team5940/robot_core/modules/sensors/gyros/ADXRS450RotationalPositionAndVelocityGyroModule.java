package org.team5940.robot_core.modules.sensors.gyros;
 
 import org.team5940.robot_core.modules.AbstractModule;
 import org.team5940.robot_core.modules.Module;
 import org.team5940.robot_core.modules.ModuleHashtable;
 import org.team5940.robot_core.modules.logging.LoggerModule;
 import org.team5940.robot_core.modules.sensors.rotational.RotationalPositionSensorModule;
 import org.team5940.robot_core.modules.sensors.rotational.RotationalVelocitySensorModule;
 import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;
 
 import edu.wpi.first.wpilibj.ADXRS450_Gyro;
 
 public class ADXRS450RotationalPositionAndVelocityGyroModule extends AbstractModule implements RotationalPositionSensorModule, RotationalVelocitySensorModule {
 	
 	private final ADXRS450_Gyro gyro;
 	
 	public ADXRS450RotationalPositionAndVelocityGyroModule(String name, LoggerModule logger)
 			throws IllegalArgumentException {
 		super(name, new ModuleHashtable<Module>(), logger);
 		gyro = new ADXRS450_Gyro();
 	}
 	
 	@Override
 	public double getRotationalPosition() {
 		return Math.toRadians(gyro.getAngle());
 	}
 
 	@Override
 	public double getRotationalVelocity() {
 		return Math.toRadians(gyro.getRate());
 	}
 
 	@Override
 	public TestStatus runTest(TestCommunicationModule comms) throws IllegalArgumentException {
 		TestStatus status = RotationalVelocitySensorModule.super.runTest(comms);
 		if (status == TestStatus.PASSED) {
 			status = RotationalPositionSensorModule.super.runTest(comms);
 		}
 		return status;
 	}
 
 }