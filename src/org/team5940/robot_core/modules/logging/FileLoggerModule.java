package org.team5940.robot_core.modules.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.team5940.robot_core.modules.ModuleHashtable;

public class FileLoggerModule extends AbstractLoggerModule {

	File loggerFile;

	/**
	 * Initializes a new FileLoggerModule.
	 * 
	 * @param name
	 *            This' name.
	 * @param logger
	 *            This' logger.
	 * @param verbose
	 *            Whether to initially be verbose or not.
	 * @param enabled
	 *            Whether to initially be enabled or not.
	 * @param standardLoggerFile
	 *            The file generic logs are saved to.
	 * @param errorLoggerFile
	 *            The file error logs are saved to.
	 * @throws IllegalArgumentException
	 *             Thrown if any argument is null
	 */
	public FileLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, File loggerFile) throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger, verbose, enabled);
		this.logger.checkInitializationArgs(this, FileLoggerModule.class, new Object[] { verbose, enabled, loggerFile });
//		loggerFile.delete();
		try {
			loggerFile.createNewFile();
		}catch(IOException e) {
			//this.logger.failInitializationIllegal(this, FileLoggerModule.class, "Unable To Create Given Files", new Object[] { verbose, enabled, loggerFile });
			this.logger.error(this, "Unable To Create File", loggerFile);
		}
		this.loggerFile = loggerFile;
		this.logger.logInitialization(this, FileLoggerModule.class, new Object[] { verbose, enabled, loggerFile });
	}


	/**
	 * Initializes a new SystemLoggerModule that's enabled and not verbose with
	 * an inert logger.
	 */
	public FileLoggerModule(String name, File loggerFile)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, false, true, loggerFile);
	}


	@Override
	protected void log(String log) {
		log += "\n";
		try {
			FileOutputStream outputStream = new FileOutputStream(this.loggerFile, true);
			outputStream.write(log.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void error(String error) {
		error += "\n";
		try {
			FileOutputStream outputStream = new FileOutputStream(this.loggerFile, true);
			outputStream.write(error.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
