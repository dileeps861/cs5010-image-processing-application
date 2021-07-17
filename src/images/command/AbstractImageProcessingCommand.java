package images.command;

import images.model.IModel;

/**
 * Abstract class to share common code between commands.
 * 
 * @author dileepshah
 *
 */
public abstract class AbstractImageProcessingCommand implements ImageProcessingCommand {

  private final IModel model;

  /**
   * Constructor to initialize model.
   * 
   * @throws IllegalArgumentException the illegal argument exception
   */
  public AbstractImageProcessingCommand(IModel model) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("ImageProcessingCommand: Argument cannot be null.");
    }

    this.model = model;
  }

  /**
   * Package private getter to access model from implementing classes.
   * 
   * @return the model
   */
  IModel getModel() {
    return model;

  }

}
