package org.team5940.robot_core.modules.testing.runners;

import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.control.procedures.AbstractProcedureModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.ownable.OwnableModule;
import org.team5940.robot_core.modules.testing.TestableModule;
import org.team5940.robot_core.modules.testing.TestableModule.TestStatus;
import org.team5940.robot_core.modules.testing.communication.TestCommunicationModule;

/**
 * A {@link AbstractProcedureModule} that runs tests on a {@link TestableModule}.
 * Can also list the modules and filter them based on what they
 * extend/implement.
 * 
 * @author Michael Bentley
 */
public class MultipleTestRunnerProcedureModule extends AbstractProcedureModule {

	/**
	 * The modules to test.
	 */
	private final ModuleHashtable<TestableModule> testableModules;

	/**
	 * The {@link TestCommunicationModule} that communicates with the tester.
	 */
	private final TestCommunicationModule communicationModule;

	/**
	 * The thread that runs the testing.
	 */
	private TestThread testThread;

	/**
	 * Initializes {@link MultipleTestRunnerProcedureModule}
	 * 
	 * @param name
	 *            This' name.
	 * @param dependencies
	 *            This' dependencies.
	 * @param logger
	 *            This' logger.
	 * @param testableModules
	 *            The modules that can be tested.
	 * @param communicationModule
	 *            The {@link TestCommunicationModule} to communicate with the
	 *            user.
	 * @throws IllegalArgumentException
	 *             Thrown if argument is null.
	 */
	public MultipleTestRunnerProcedureModule(String name, LoggerModule logger,
			ModuleHashtable<TestableModule> testableModules, TestCommunicationModule communicationModule)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(testableModules.values()).chainPut(communicationModule), logger);
		this.logger.checkInitializationArgs(this, MultipleTestRunnerProcedureModule.class,
				new Object[] { testableModules, communicationModule });
		this.testableModules = testableModules;
		this.communicationModule = communicationModule;
		this.logger.logInitialization(this, MultipleTestRunnerProcedureModule.class,
				new Object[] { testableModules, communicationModule });
	}

	@Override
	protected synchronized void doProcedureStart() throws Exception {
		testThread = new TestThread(this.communicationModule, this.testableModules);
		testThread.start();
	}

	@Override
	protected synchronized boolean doProcedureUpdate() throws Exception {
		return testThread.isInterrupted();
	}

	@Override
	protected synchronized void doProcedureClean() {
		testThread.interrupt();
		testThread = null;
	}

	private class TestThread extends Thread {
		
		/**
		 * The {@link TestCommunicationModule} that communicates with the tester.
		 */
		private final TestCommunicationModule communicationModule;
		
		/**
		 * The modules to test.
		 */
		private final ModuleHashtable<TestableModule> testableModules;

		/**
		 * Creates a new Thread that tests the modules.
		 * 
		 * @param communicationModule
		 *            The {@link TestCommunicationModule} that communicates with
		 *            the user.
		 * @param testableModules
		 *            A {@link ModuleHashtable} of TestableModules that it can
		 *            test.
		 */
		public TestThread(TestCommunicationModule communicationModule,
				ModuleHashtable<TestableModule> testableModules) {
			this.communicationModule = communicationModule;
			this.testableModules = testableModules;
		}

		@Override
		public void run() {
			String testModuleName;
			try {
				TestableModule testModule;
				String userActionString;
				while (!this.isInterrupted()) {
					userActionString = this.communicationModule.promptText(
							"Would you like to search, list, or test, the TestableModules? (Enter no to leave)");
					if (userActionString.toLowerCase().equals("test")) {
						boolean test = true;
						do {
							testModuleName = this.communicationModule
									.promptText("Which module would you like to test?");
							testModule = this.testableModules.get(testModuleName);
							if (testModule == null) {
								testModule = this.testableModules.get(testModuleName.toLowerCase());
							}
							if (testModule == null)
								test = this.communicationModule.promptBoolean("Would you like to continue testing?");
						} while (testModule == null && test);
						
						
						if(test) {
							ModuleHashtable<OwnableModule> extendedDependencies = testModule.getExtendedModuleDependencies().getAssignableTo(OwnableModule.class);
							
							if (testModule instanceof OwnableModule)
								((OwnableModule) testModule).acquireOwnershipForCurrent(true);
							for(OwnableModule dependency : extendedDependencies.values())
								dependency.acquireOwnershipForCurrent(true);
							
							TestStatus testResult = testModule.runTest(this.communicationModule);
							
							if (testModule instanceof OwnableModule)
								((OwnableModule) testModule).relinquishOwnershipForCurrent();
							for(OwnableModule dependency : extendedDependencies.values())
								dependency.relinquishOwnershipForCurrent();
							
							this.communicationModule.promptText("Test: " + testResult.toString() + " (ok to continue)");
						}
					} else if (userActionString.toLowerCase().equals("list")) {
						String moduleNames = "Modules: ";
						for (TestableModule module : this.testableModules.values()) {
							moduleNames += module.getModuleName() + ", ";
						}
						this.communicationModule.promptText(moduleNames + "(ok to continue)");
					} else if (userActionString.toLowerCase().equals("search")) {
						String moduleClass = this.communicationModule.promptText("What class do you want to list?");
						String extendedModules = "";
						for (TestableModule modules : this.testableModules.values()) {
							if (isAssignableToString(moduleClass, modules.getClass())) {
								extendedModules += modules.getModuleName() + ", ";
							}
						}
						if (extendedModules.equals("")) {
							extendedModules = "Nothing";
						}
						this.communicationModule.promptText(
								extendedModules + " extends/implements " + moduleClass + "(ok to continue)");
					} else if (userActionString.toLowerCase().equals("no")) {
						this.interrupt();
					}
				}
				this.communicationModule.displayText("Tests Complete");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private boolean isAssignableToString(String className, Class<?> module) {
			if (module.getSimpleName().equals(className))
				return true;

			// TODO this might be able to be done faster.
			for (Class<?> implementedInterface : module.getInterfaces())
				if (implementedInterface.getSimpleName().equals(className))
					return true;

			Class<?> superClass = module.getSuperclass();
			if (superClass != null)
				return this.isAssignableToString(className, superClass);

			return false;
		}
	}
}
