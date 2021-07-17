package images.command;


import images.model.IModel;
import images.model.image.Image;

/**
 * Command to execute PixelateImage.
 */
public class PixelateImageCommand extends AbstractImageProcessingCommand {
  
  private final int numberOfSquares;


  /**
   * Constructor to create MosaicImageCommand object.
   * 
   * @param numberOfSquares the squares count width wise
   * @throws IllegalArgumentException the illegal argument exception
   */
  public PixelateImageCommand(IModel model, int numberOfSquares) throws IllegalArgumentException {
    super(model);
    
    this.numberOfSquares = numberOfSquares;
 
  }

  @Override
  public Image execute(Image image) {
    return getModel().pixelateImage(image, numberOfSquares);
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("PixelateImageCommand{");
    sb.append("numberOfSquares=").append(numberOfSquares);
    sb.append('}');
    return sb.toString();
  }
}
