package org.team5940.robot_core.modules.image_processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;

/**
 * An implementation of {@link MatProcessorModule} that adds a single mat as an overlay, resizing a copy as necessary.
 * @author Michael Bentley, David Boles
 *
 */
public class OverlayMatProcessorModule extends AbstractModule implements MatProcessorModule {
	
	/**
	 * Stores the original version of the image to overlay.
	 */
	private final Mat overlayImage;
	
	/**
	 * Stores the opacity to draw the overlay.
	 */
	private final double opacity;
	
	/**
	 * Initializes a new {@link MatProcessorModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param overlayImage The image to overlay.
	 * @param opacity The opacity of the image to overlay.
	 * @throws IllegalArgumentException Thrown if any argument is null or 1 < opacity < 0.
	 */
	public OverlayMatProcessorModule(String name, LoggerModule logger, Mat overlayImage, double opacity) throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, OverlayMatProcessorModule.class, new Object[]{overlayImage, opacity});
		if(1 < opacity || opacity < 0)
			this.logger.failInitializationIllegal(this, OverlayMatProcessorModule.class, "Opacity out of bounds!", new Object[]{overlayImage, opacity});
		this.overlayImage = overlayImage;
		this.opacity = opacity;
		this.logger.logInitialization(this, OverlayMatProcessorModule.class, new Object[]{overlayImage, opacity});
	}

	@Override
	public synchronized Mat processMat(Mat mat) {
		Mat resized = new Mat(new Size(mat.width(), mat.height()), mat.type());
		Imgproc.resize(this.overlayImage, resized, new Size(mat.width(), mat.height()));
		Core.addWeighted(mat, 1-this.opacity, resized, this.opacity, -100 * this.opacity, mat);//TODO 5th argument is a hack. Images should be loaded with alpha layer
		return mat;
	}
}
