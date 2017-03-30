package org.team5940.robot_core.modules;

import org.team5940.robot_core.modules.control.procedures.ProcedureModule;
import org.team5940.robot_core.modules.control.procedures.ContinuousSelectableProcedureModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.selectors.RobotStateSelectorModule;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.hal.HAL;

/**
 * A simple base class that implements {@link Module} and runs a single procedure.
 * @author David Boles
 * @see RobotModule#initializeRobotModules()
 *
 */
public abstract class RobotModule extends RobotBase implements Module {
	
	/**
	 * Stores this' dependencies.
	 * @see Module#getModuleDependencies()
	 * @see Module#getExtendedModuleDependencies()
	 */
	private final ModuleHashtable<Module> dependencies = new ModuleHashtable<>();
	
	/**
	 * Stores this' logger.
	 * @see RobotModule#getModuleLogger()
	 * @see RobotModule#setRobotLogger(LoggerModule)
	 */
	private LoggerModule logger = LoggerModule.INERT_LOGGER;
	
	/**
	 * Stores the procedure to run after module initialization.
	 * @see RobotModule#setRobotProcedure(ProcedureModule)
	 * @see RobotModule#createRobotProcedure(LoggerModule, ProcedureModule, ProcedureModule, ProcedureModule, ProcedureModule)
	 */
	private ProcedureModule robotProcedure = ProcedureModule.INERT_PROCEDURE;
	
	/**
	 * Stores whether this has finished initializing this' modules.
	 * @see RobotModule#initializeRobotModules()
	 * @see RobotModule#setRobotProcedure(ProcedureModule)
	 * @see RobotModule#createRobotProcedure(LoggerModule, ProcedureModule, ProcedureModule, ProcedureModule, ProcedureModule)
	 */
	private boolean robotModulesInitialized = false;
	
	@Override
	public void startCompetition() {
		try {
			this.initializeRobotModules();
			this.robotModulesInitialized = true;
			this.dependencies.put(this.robotProcedure);
			this.dependencies.put(this.logger);
			HAL.observeUserProgramStarting();
			this.logger.log(this, "Module Initialization Complete, Running Procedure", this.robotProcedure);
			this.robotProcedure.run(true);
		}catch(Exception e) {
			this.getModuleLogger().error(this, "Initialization Failed", e);
		}
	}

	/**
	 * Called on competition start to create all of the modules for this robot. Couple of notes:
	 * You can set the robot's logger directly with {@link RobotModule#setRobotLogger(LoggerModule)}.
	 * You can set the procedure to run after this exits with {@link RobotModule#setRobotProcedure(ProcedureModule)} or {@link RobotModule#createRobotProcedure(LoggerModule, ProcedureModule, ProcedureModule, ProcedureModule, ProcedureModule)}.
	 * Make sure to start any threads!
	 * @throws Exception Thrown if any fatal exception occurs. Will prevent robot procedure from executing.
	 */
	protected abstract void initializeRobotModules() throws Exception;
	
	/**
	 * Sets the procedure that the robot will run. It will get added to this' dependencies once module initialization is complete.
	 * @param procedure The procedure to run when initialization is complete.
	 * @throws IllegalArgumentException Thrown if procedure is null.
	 * @throws IllegalStateException Thrown if the robot has already finished initializing.
	 * @see RobotModule#createRobotProcedure(LoggerModule, ProcedureModule, ProcedureModule, ProcedureModule, ProcedureModule)
	 */
	public synchronized void setRobotProcedure(ProcedureModule procedure) throws IllegalArgumentException, IllegalStateException {
		if(this.robotModulesInitialized)
			this.logger.failSettingIllegal(this, "Robot Procedure", "Initialization Complete", procedure);
		this.logger.checkAndLogSettingArg(this, "Robot Procedure", procedure);
		this.robotProcedure = procedure;
	}
	
	/**
	 * Sets the robot's procedure to a new {@link ContinuousSelectableProcedureModule} named "robot_procedure" with logger, your procedures, forced dependency acquisition, and a new {@link RobotStateSelectorModule} named "robot_selector" with logger.
	 * @param logger The logger you want the new created procedure and selector to use.
	 * @param disabled The procedure to run when the robot is disabled.
	 * @param auto The procedure to run when the robot is in auto.
	 * @param opCon The procedure to run when the robot is in operator control.
	 * @param test The procedure to run when the robot is in testing mode.
	 * @throws IllegalArgumentException Thrown if any argument is null. You can use {@link LoggerModule#INERT_LOGGER} and {@link ProcedureModule#INERT_PROCEDURE} if you don't want it to log or run anything for a specific mode.
	 * @throws IllegalStateException Thrown if this robot has already finished initializing modules.
	 * @see RobotModule#setRobotProcedure(ProcedureModule)
	 */
	public synchronized void createRobotProcedure(LoggerModule logger, ProcedureModule disabled, ProcedureModule auto, ProcedureModule opCon, ProcedureModule test) throws IllegalArgumentException, IllegalStateException {
		ProcedureModule[] procedures = new ProcedureModule[]{auto, opCon, test};
		if(logger == null || disabled == null || auto == null || opCon == null ||test == null) {
			this.logger.error(this, "Creating Robot Procedure With Null", new Object[]{logger, disabled, procedures});
		}
		
		if(!this.robotModulesInitialized) {
			this.logger.log(this, "Creating Robot Procedure", new Object[]{logger, disabled,  procedures});
			SelectorModule selector = new RobotStateSelectorModule("robot_selector", logger, this);
			ProcedureModule procedure = new ContinuousSelectableProcedureModule("robot_procedure", logger, selector, disabled, procedures, true);
			this.setRobotProcedure(procedure);
		}else {
			this.logger.error(this, "Creating Robot Procedure When Initialization Complete", new Object[]{this.dependencies.get("robot_procedure"), logger, disabled,  procedures});
			throw new IllegalStateException("Robot initialzation already complete!");
		}
	}
	
	/**
	 * Sets the logger this should use. To be called in {@link RobotModule#initializeRobotModules()}.
	 * @param logger The logger this should now use.
	 * @throws IllegalArgumentException Thrown if logger is null.
	 * @throws IllegalStateException Thrown if this robot has already finished initializing modules.
	 * @see RobotModule#getModuleLogger()
	 */
	protected synchronized void setRobotLogger(LoggerModule logger) throws IllegalArgumentException, IllegalStateException {
		if(this.robotModulesInitialized)
			this.logger.failSettingIllegal(this, "Robot Procedure", "Initialization Complete", logger);
		this.logger.checkAndLogSettingArg(this, "Robot Logger", logger);
		this.logger = logger;
	}
	
	//MODULE STUFF
	@Override
	public String getModuleName() {
		this.getModuleLogger().logGot(this, "Name", "robot");
		return "robot";
	}

	@Override
	public ModuleHashtable<Module> getModuleDependencies() {
		ModuleHashtable<Module> out = new ModuleHashtable<Module>();
		out.putAll(this.dependencies);
		this.getModuleLogger().logGot(this, "Dependencies", out);
		return out;
	}

	@Override
	public LoggerModule getModuleLogger() {
		return this.logger;
	}
}
