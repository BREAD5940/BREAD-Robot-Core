package org.team5940.robot_core.modules.camera_streaming;

import org.opencv.core.Mat;
import org.team5940.robot_core.modules.Module;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.control.procedures.AbstractProcedureModule;
import org.team5940.robot_core.modules.control.procedures.ProcedureModule;
import org.team5940.robot_core.modules.image_processing.MatProcessorModule;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * An implementation of {@link AbstractProcedureModule} that streams whatever VideoSource is selected by a {@link SelectorModule}. It can also run the images through an array of {@link MatProcessorModule}s.
 * @author Michael Bentley
 */
public class SelectableMJPEGStreamerModule extends AbstractProcedureModule {
	
	/**
	 * Where to grab frames from.
	 */
	private final CvSink input;
	
	/**
	 * Where to put frames.
	 */
	private final CvSource output;
	
	/**
	 * The {@link SelectorModule} that decides which camera to display. 
	 */
	private final SelectorModule selector;
	
	/**
	 * The cameras that are used. 
	 */
	private final VideoSource[] sources;
	
	/**
	 * The camera that is displayed when selector.getCurrentState() returns -1. 
	 */
	private final VideoSource unselectedSource;
	
	/**
	 * The processes run on the image before it is displayed. 
	 */
	private final MatProcessorModule[] processors;

	/**
	 * Initializes a new {@link SelectableMJPEGStreamerModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param selector Chooses which camera to display.
	 * @param unselectedSource The camera displayed when the selector's state is -1. 
	 * @param sources The video sources to switch between. 
	 * @param processors The processors run on the image before it is displayed.
	 * @throws IllegalArgumentException Thrown if any argument is null, the number of selector states is not equal to the number of sources, if sources contains null, or processors contains null.
	 */
	public SelectableMJPEGStreamerModule(String name, LoggerModule logger, SelectorModule selector, VideoSource unselectedSource, VideoSource[] sources, MatProcessorModule[] processors)
			throws IllegalArgumentException {
		super(name, new ModuleHashtable<Module>(processors).chainPut(selector), logger);
		this.logger.checkInitializationArgs(this, SelectableMJPEGStreamerModule.class, new Object[] {selector, unselectedSource, sources, processors});
		if (selector.getNumberOfStates() != sources.length)
			this.logger.failInitializationIllegal(this, SelectableMJPEGStreamerModule.class, "Unequal Quantities", new Object[] {selector, unselectedSource, sources, processors});
		for (VideoSource source : sources)
			if (source == null)
				this.logger.failInitializationIllegal(this, SelectableMJPEGStreamerModule.class, "Null Source", new Object[] {selector, unselectedSource, sources, processors});
		for (MatProcessorModule processor : processors)
			if (processor == null)
				this.logger.failInitializationIllegal(this, SelectableMJPEGStreamerModule.class, "Null Processor", new Object[] {selector, unselectedSource, sources, processors});
		this.input = CameraServer.getInstance().getVideo(unselectedSource);
		this.selector = selector;
		this.unselectedSource = unselectedSource;
		this.sources = sources;
		this.processors = processors;
		this.output = CameraServer.getInstance().putVideo("Current View", 320, 240);
		this.logger.logInitialization(this, SelectableMJPEGStreamerModule.class,
				new Object[] {selector, unselectedSource, sources, processors});
	}
	
	/**
	 * Initializes a new {@link SelectableMJPEGStreamerModule} with a single processor.
	 * @see SelectableMJPEGStreamerModule#SelectableMJPEGStreamerProcedureModule(String, LoggerModule, SelectorModule, VideoSource, VideoSource[], MatProcessorModule[])
	 */
	public SelectableMJPEGStreamerModule(String name, LoggerModule logger, SelectorModule selector, VideoSource unselectedSource, VideoSource[] sources, MatProcessorModule processor)
			throws IllegalArgumentException {
		this(name, logger, selector, unselectedSource, sources, new MatProcessorModule[]{processor});
	}
	
	/**
	 * Initializes a new {@link SelectableMJPEGStreamerModule} without processors.
	 * @see SelectableMJPEGStreamerModule#SelectableMJPEGStreamerProcedureModule(String, LoggerModule, SelectorModule, VideoSource, VideoSource[], MatProcessorModule[])
	 */
	public SelectableMJPEGStreamerModule(String name, LoggerModule logger, SelectorModule selector, VideoSource unselectedSource, VideoSource[] sources)
			throws IllegalArgumentException {
		this(name, logger, selector, unselectedSource, sources, MatProcessorModule.INERT_MAT_PROCESSOR);
	}

	@Override
	protected synchronized void doProcedureStart() throws Exception { }

	@Override
	protected synchronized boolean doProcedureUpdate() throws Exception {
		int currentState = this.selector.getCurrentState();
		if (currentState == -1) this.input.setSource(this.unselectedSource);
		else this.input.setSource(this.sources[currentState]);
		
		Mat frame = new Mat();
		this.input.grabFrame(frame);
		for (MatProcessorModule processor : this.processors)
			frame = processor.processMat(frame);
		this.output.putFrame(frame);
		
		return false;
	}

	@Override
	protected synchronized void doProcedureClean() throws Exception { }
	
	/**
	 * Runs this with a 0ms delay.
	 * @see ProcedureModule#run(boolean, long)
	 */
	@Override
	public void run(boolean forceAcquisition) {
		super.run(forceAcquisition, 20);
	}
}
