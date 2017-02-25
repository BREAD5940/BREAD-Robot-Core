package org.team5940.robot_core.modules.robot;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.control.procedures.ProcedureModule;
import org.team5940.robot_core.modules.control.procedures.SelectableProcedureRunnerModule;
import org.team5940.robot_core.modules.control.threads.ThreadModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.selectors.RobotStateSelectorModule;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

import edu.wpi.first.wpilibj.SampleRobot;

/**
 * A simple base class that implements Module. Logger and modules defined by named modules added to this' dependencies in initializeModules().
 * @author David Boles
 *
 */
public abstract class SimpleRobotModule extends SampleRobot implements Module {
	
	/**
	 * Stores this' dependencies.
	 * @see Module#getModuleDependencies()
	 * @see Module#getExtendedModuleDependencies()
	 */
	protected final ModuleHashtable<Module> dependencies = new ModuleHashtable<>();
	
	/**
	 * Called on robot init to create all of the modules for this robot (this.dependencies.put(...)). Make sure to start any threads! To work with this base class you might want to initialize modules with names:
	 * {@link LoggerModule} robot_logger (returned by {@link SimpleRobotModule#getModuleLogger()})
	 * {@link ProcedureModule} disabled_procedure (executed when robot disabled)
	 * {@link ProcedureModule} auto_procedure (executed when robot in autonomous)
	 * {@link ProcedureModule} opcon_procedure (executed when robot in teleop)
	 * {@link ProcedureModule} test_procedure (executed when robot in test)
	 * 
	 * Modules named "robot_selector", "robot_procedure" and "robot_thread" will be created to run your procedures and added to the dependencies.
	 * @throws Exception Anything can be thrown by the implementation. If done it will be logged and the above modules will not be made.
	 */
	protected abstract void initializeModules() throws Exception;
	
	@Override
	protected void robotInit() {
		
		try {
			this.initializeModules();
			
			ProcedureModule disabled = null;
			try{disabled = (ProcedureModule) this.dependencies.get("disabled_procedure");}catch(Exception e){};
			if(disabled == null) disabled = ProcedureModule.INERT_PROCEDURE;
			
			ProcedureModule auto = null;
			try{auto = (ProcedureModule) this.dependencies.get("auto_procedure");}catch(Exception e){};
			if(auto == null) auto = ProcedureModule.INERT_PROCEDURE;
			
			ProcedureModule opcon = null;
			try{opcon = (ProcedureModule) this.dependencies.get("opcon_procedure");}catch(Exception e){};
			if(opcon == null) opcon = ProcedureModule.INERT_PROCEDURE;
			
			ProcedureModule test = null;
			try{test = (ProcedureModule) this.dependencies.get("test_procedure");}catch(Exception e){};
			if(test == null) test = ProcedureModule.INERT_PROCEDURE;

			SelectorModule selector = new RobotStateSelectorModule("robot_selector", LoggerModule.INERT_LOGGER, this);
			this.dependencies.put(selector);
			
			ProcedureModule procedure = new SelectableProcedureRunnerModule("robot_procedure", this.getModuleLogger(), selector, disabled, new ProcedureModule[]{auto, opcon, test}, true);
			this.dependencies.put(procedure);
			
			ThreadModule thread = new ThreadModule("robot_thread", this.getModuleLogger(), procedure);
			this.dependencies.put(thread);
			thread.start();
			
			this.getModuleLogger().log(this, "Initialized And Started");
		}catch(Exception e) {
			this.getModuleLogger().error(this, "Initialization Failed", e);
		}
	}
	
	@Override
	protected void disabled() {
		this.getModuleLogger().log(this, "Disabled");
	}
	
	@Override
	public void operatorControl() {
		this.getModuleLogger().log(this, "Operator Control");
	}
	
	@Override
	public void autonomous() {
		this.getModuleLogger().log(this, "Autonomous");
	}
	
	@Override
	public void test() {
		this.getModuleLogger().log(this, "Test");
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
		LoggerModule out = this.dependencies.getAssignableTo(LoggerModule.class).get("robot_logger");
		if(out == null) out = LoggerModule.INERT_LOGGER;
		return out;
	}
}
