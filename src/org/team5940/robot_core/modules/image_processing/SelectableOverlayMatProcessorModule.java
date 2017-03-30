package org.team5940.robot_core.modules.image_processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.team5940.robot_core.modules.AbstractModule;
import org.team5940.robot_core.modules.ModuleHashtable;
import org.team5940.robot_core.modules.logging.LoggerModule;
import org.team5940.robot_core.modules.sensors.selectors.SelectorModule;

/**
 * An implementation of {@link MatProcessorModule} that adds a selectable mat as an overlay, resizing a copy as necessary.
 * @author David Boles
 *
 */
public class SelectableOverlayMatProcessorModule extends AbstractModule implements MatProcessorModule {
	
	/**
	 * Stores this' selector.
	 */
	private final SelectorModule selector;
	
	/**
	 * Stores the original version of the image to overlay.
	 */
	private final Mat unselectedOverlayImage;
	
	/**
	 * Stores the original version of the image to overlay.
	 */
	private final Mat[] overlayImages;
	
	/**
	 * Stores the opacity to draw the overlay.
	 */
	private final double opacity;
	
	/**
	 * Initializes a new {@link SelectableOverlayMatProcessorModule}.
	 * @param name This' name.
	 * @param logger This' logger.
	 * @param selector The selector to use to determine the mat to overlay.
	 * @param unselectedOverlayImage The mat to overlay if there is no selected state (-1).
	 * @param overlayImages The images to select from to overlay.
	 * @param opacity The opacity to draw the overlays.
	 * @throws IllegalArgumentException Thrown if any argument is null, overlayImages contains a null mat, the number of selector states does not equal the number of overlayImages, or 1 < opacity < 0.
	 */
	public SelectableOverlayMatProcessorModule(String name, LoggerModule logger, SelectorModule selector, Mat unselectedOverlayImage, Mat[] overlayImages, double opacity) throws IllegalArgumentException {
		super(name, new ModuleHashtable<>(), logger);
		this.logger.checkInitializationArg(this, SelectableOverlayMatProcessorModule.class, new Object[]{selector, unselectedOverlayImage, overlayImages, opacity});
		if(overlayImages.length != selector.getNumberOfStates())
			this.logger.failInitializationIllegal(this, SelectableOverlayMatProcessorModule.class, "Unequal Quantities", new Object[]{selector, unselectedOverlayImage, overlayImages, opacity});
		for(Mat mat : overlayImages)
			if(mat == null)
				this.logger.failInitializationIllegal(this, SelectableOverlayMatProcessorModule.class, "Null Mat", new Object[]{selector, unselectedOverlayImage, overlayImages, opacity});
		if(1 < opacity || opacity < 0)
			this.logger.failInitializationIllegal(this, SelectableOverlayMatProcessorModule.class, "Opacity out of bounds!", new Object[]{selector, unselectedOverlayImage, overlayImages, opacity});
		this.selector = selector;
		this.unselectedOverlayImage = unselectedOverlayImage;
		this.overlayImages = overlayImages;
		this.opacity = opacity;
		this.logger.logInitialization(this, SelectableOverlayMatProcessorModule.class, new Object[]{selector, unselectedOverlayImage, overlayImages, opacity});
	}

	@Override
	public synchronized Mat processMat(Mat mat) {
		Mat overlay = this.unselectedOverlayImage;
		int selection = this.selector.getCurrentState();
		if(selection != -1) overlay = this.overlayImages[selection];
		Mat resized = new Mat(new Size(mat.width(), mat.height()), mat.type());
		Imgproc.resize(overlay, resized, new Size(mat.width(), mat.height()));
		Core.addWeighted(mat, 1-this.opacity, resized, this.opacity, -100 * this.opacity, mat);
		return mat;
	}
}
