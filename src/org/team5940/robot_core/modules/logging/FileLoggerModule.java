package org.team5940.robot_core.modules.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.team5940.robot_core.modules.ModuleHashtable;

public class FileLoggerModule extends AbstractLoggerModule {

	File standardLoggerFile;
	File errorLoggerFile;

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
	public FileLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, File standardLoggerFile,
			File errorLoggerFile) throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger, verbose, enabled);
		errorLoggerFile.delete();
		standardLoggerFile.delete();
		try {
			errorLoggerFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			standardLoggerFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.standardLoggerFile = standardLoggerFile;
		this.errorLoggerFile = errorLoggerFile;
		this.logger.logInitialization(this, FileLoggerModule.class, new Object[] { verbose, enabled });
	}

	/**
	 * Initializes a new FileLoggerModule with an inert logger.
	 */
	public FileLoggerModule(String name, boolean verbose, boolean enabled, File standardLoggerFile,
			File errorLoggerFile) throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, verbose, enabled, standardLoggerFile, errorLoggerFile);
	}

	/**
	 * Initializes a new SystemLoggerModule that's enabled and not verbose with
	 * an inert logger.
	 */
	public FileLoggerModule(String name, File standardLoggerFile, File errorLoggerFile)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, false, true, standardLoggerFile, errorLoggerFile);
	}

	/**
	 * Initializes a new SystemLoggerModule without an ErrorLoggerFile. Errors
	 * will be saved to the StandardLoggerFile.
	 */
	public FileLoggerModule(String name, LoggerModule logger, boolean verbose, boolean enabled, File standardLoggerFile)
			throws IllegalArgumentException {
		this(name, logger, verbose, enabled, standardLoggerFile, standardLoggerFile);
	}

	/**
	 * Initializes a new SystemLoggerModule with an inert logger. Errors will be
	 * saved to the StandardLoggerFile.
	 */
	public FileLoggerModule(String name, boolean verbose, boolean enabled, File standardLoggerFile)
			throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, verbose, enabled, standardLoggerFile);
	}

	/**
	 * Initializes a new SystemLoggerModule that's enabled and not verbose with
	 * an inert logger. Errors will be saved to the StandardLoggerFile.
	 */
	public FileLoggerModule(String name, File standardLoggerFile) throws IllegalArgumentException {
		this(name, LoggerModule.INERT_LOGGER, false, true, standardLoggerFile);
	}

	@Override
	protected void log(String log) {
		log += "\n";
		try {
			FileOutputStream outputStream = new FileOutputStream(this.standardLoggerFile, true);
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
			FileOutputStream outputStream = new FileOutputStream(this.errorLoggerFile, true);
			outputStream.write(error.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
