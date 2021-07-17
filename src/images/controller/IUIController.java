package images.controller;

import images.view.IImageProcessingView;

/**
 * The controller to manage UI commands.
 * @author dileepshah
 *
 */
public interface IUIController {
  /**
   * Sets the the view to the controller.
   */
  void setView(IImageProcessingView view);
  
}
