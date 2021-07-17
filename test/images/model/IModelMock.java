package images.model;

import images.beans.IPatternBean;
import images.model.image.Image;

import java.io.IOException;
import java.util.Set;




/**
 * Mock of the IModelMock interface. Created only for testing
 * @author dileepshah
 *
 */
public class IModelMock implements IModel {
  private final Appendable appendable;
  private IPatternBean patternBean;
  private Image image;

  /**
   * Setter method to set pattern bean.
   * @param patternBean the pattern bean object
   */
  public void setPatternBean(IPatternBean patternBean) {
    this.patternBean = patternBean;
  }

  /**
   * Setter method to set image object.
   * @param image the image object
   */
  public void setImage(Image image) {
    this.image = image;
  }

  /**
   * Constructor to create mock model object.
   * @param appendable the appendable object
   */
  public IModelMock(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public Image transformImageColorToGrayScale(Image image) {
    try {
      this.appendable.append(
          this.getClass().getSimpleName() + ".transformImageColorToGrayScale() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public Image transformImageColorToSepiaTone(Image image) {
    try {
      this.appendable.append(
          this.getClass().getSimpleName() + ".transformImageColorToSepiaTone() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public Image blurImage(Image image) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".blurImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public Image sharpenImage(Image image) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".sharpenImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public Image ditherImage(Image image, int numberOfColors) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".ditherImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public Image pixelateImage(Image image, int numberOfSquares) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".pixelateImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public IPatternBean generatePattern(Image image, int numberOfSquares) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".generatePattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.patternBean;
  }

  @Override
  public IPatternBean generatePattern(Image image, int numberOfSquares, Set<String> colors) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".generatePattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.patternBean;
  }

  @Override
  public Image mosaicImage(Image image, int numberOfSeeds) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".mosaicImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("IModelMock{");
    sb.append('}');
    return sb.toString();
  }

  @Override
  public IPatternBean replaceColor(IPatternBean patternBean, String oldColor, String newColor) {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".replaceColor() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.patternBean;
  }

  @Override
  public Image getImage() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.image;
  }

  @Override
  public IPatternBean getPatternBean() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getPatternBean() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.patternBean;
  }

  @Override
  public void clearProcessing() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".clearProcessing() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    
    
  }

}
