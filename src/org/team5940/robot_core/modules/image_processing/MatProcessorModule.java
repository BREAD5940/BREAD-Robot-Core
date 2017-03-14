package org.team5940.robot_core.modules.image_processing;

import org.opencv.core.Mat;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * This extension of {@link Module} performs actions on a Mat.
 * @author David Boles
 *
 */
public interface MatProcessorModule extends Module {

	/**
	 * Processes the mat.
	 * @param mat The mat to process.
	 * @return The processed Mat. THIS MAY OR MAY NOT BE THE ORIGINAL MAT!
	 */
	public Mat processMat(Mat mat);
	
	/**
	 * This {@link MatProcessorModule}, named "inert_mat_processor", should be used whenever you want a mat processor that doesn't do anything - it just returns the input.
	 */
	public static final MatProcessorModule INERT_MAT_PROCESSOR = new MatProcessorModule(){

		@Override
		public String getModuleName() {
			return "inert_mat_processor";
		}

		@Override
		public ModuleHashtable<Module> getModuleDependencies() {
			return new ModuleHashtable<>(LoggerModule.INERT_LOGGER);
		}

		@Override
		public LoggerModule getModuleLogger() {
			return LoggerModule.INERT_LOGGER;
		}

		@Override
		public Mat processMat(Mat mat) {
			return mat;
		}
		
	};
	
}
