package images.command;



import images.model.IModel;
import images.model.image.Image;

/**
 * Command to execute MosaicImage objects.
 */
public class MosaicImageCommand implements ImageProcessingCommand {
  private final int numberOfSeeds;
  private final  IModel model;

  /**
   * Constructor to create MosaicImageCommand object.
   * 
   * @param numberOfSeeds the seeds count
   * @throws IllegalArgumentException the illegal argument exception
   */
  public MosaicImageCommand( IModel model, int numberOfSeeds) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Argument cannot be null.");
    }
    this.model = model;
    this.numberOfSeeds = numberOfSeeds;
  }

  @Override
  public  Image execute(Image image) {
    return model.mosaicImage(image, numberOfSeeds);
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("MosaicImageCommand{");
    sb.append("seeds=").append(numberOfSeeds);
    sb.append('}');
    return sb.toString();
  }
}
