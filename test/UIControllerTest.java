import static org.junit.Assert.assertEquals;

import images.beans.IPatternBeanMock;
import images.beans.PatternBean;
import images.constants.EditingOptions;
import images.controller.CommandController;
import images.controller.CommandControllerImpl;
import images.controller.CommandGenerator;
import images.controller.CommandGeneratorImpl;
import images.controller.Features;
import images.controller.UIController;
import images.model.IModelMock;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;
import images.utilities.ClientUtility;
import images.utilities.ClientUtilityImpl;
import images.utilities.MockClientUtility;
import images.view.IImageProcessingViewMock;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Test class to test UI controller.
 */
public class UIControllerTest {
  private Features feature;
  private CommandController commandController;
  private CommandGenerator commandGenerator;
  private IModelMock model;
  private IImageProcessingViewMock view;
  private StringBuilder out;
  private ClientUtility clientUtility;

  /**
   * Initial setup.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    out = new StringBuilder();
    commandController = new CommandControllerImpl();
    commandGenerator = new CommandGeneratorImpl();
    clientUtility = new MockClientUtility(out);
    model = new IModelMock(out);
    view = new IImageProcessingViewMock(out);
    UIController controller = new UIController(commandController, commandGenerator, clientUtility,
        model);
    controller.setView(view);
    feature = controller;
  }

  /**
   * Set the view to controller.
   */
  @Test
  public void setView() {
    UIController controller = new UIController(commandController, commandGenerator, clientUtility,
        model);
    controller.setView(view);
    StringBuilder expectedValue = new StringBuilder(
        "UIController{commandController=CommandControllerImpl{commands=[]}, ");
    expectedValue
        .append("commandGenerator=CommandGeneratorImpl{}, clientUtility=MockClientUtility{},")
        .append(" view=IImageProcessingViewMock{}, model=IModelMock{}}");
    assertEquals(expectedValue.toString(), controller.toString());
  }

  /**
   * Test if error thrown in the form of message if process image called without
   * calling loadImage().
   */
  @Test
  public void processImageWithoutImage() {
    feature.editOptionSelection(EditingOptions.BLUR.toString());
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must load the image before proceeding to process the image."
            + "IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must load the image before proceeding to process the image.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if error thrown in the form of message if process image called without
   * select any editing option.
   */
  @Test
  public void processImageWithoutOption() {

    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must select editing option before processing the image.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test No command given.
   */
  @Test
  public void processImageNullCommand() {
    feature.editOptionSelection(null);
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Invalid edit option selected.IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must select editing option before processing the image.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test empty command given.
   */
  @Test
  public void processImageEmptyCommand() {
    feature.editOptionSelection("");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Error: unknown command > IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must select editing option before processing the image.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if blur option works correctly.
   */
  @Test
  public void processImageBlurTest() {
    feature.editOptionSelection(EditingOptions.BLUR.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.blurImage() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if gray sharpen works correctly.
   */
  @Test
  public void processImageSharpenTest() {
    feature.editOptionSelection(EditingOptions.SHARPEN.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.sharpenImage() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if Grayscale option works correctly.
   */
  @Test
  public void processImageGrayscaleTest() {
    feature.editOptionSelection(EditingOptions.GRAYSCALE.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.transformImageColorToGrayScale() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if sepiatone option works correctly.
   */
  @Test
  public void processImageSepiaToneTest() {
    feature.editOptionSelection(EditingOptions.SEPIA_TONE.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.transformImageColorToSepiaTone() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if dither option throws error if now number of colors provided.
   */
  @Test
  public void processImageDitherWrongCommandTest() {
    feature.editOptionSelection(EditingOptions.DITHER.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("CommandGeneratorImpl: Dither image must be in format \"dither <noOfColors>\"");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if dither option works correctly.
   */
  @Test
  public void processImageDitherTest() {
    feature.editOptionSelection(EditingOptions.DITHER.toString());
    view.setUploadedImagePath("user/image.jpg");
    view.setUserProvideSelectionValues("2");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.ditherImage() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if pixelate option throws error if the not given.
   */
  @Test
  public void processImagePixelateWrongCommandTest() {
    feature.editOptionSelection(EditingOptions.PIXELATE.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("CommandGeneratorImpl: Number of squares must "
            + "be specified and must be more than zero.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if generate pixelate option works correctly.
   */
  @Test
  public void processImagePixelateTest() {
    feature.editOptionSelection(EditingOptions.PIXELATE.toString());
    view.setUserProvideSelectionValues("50");
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.pixelateImage() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if pixelate option throws error if the user value not given.
   */
  @Test
  public void processImagePatternWrongCommandTest() {
    feature.editOptionSelection(EditingOptions.GENERATE_PATTERN.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Pixelation/ Mosaic/ Dither/ Pattern user provided "
            + "value must be specified and must be a non zero number. Provided: null");
    assertEquals(expectedValue.toString(), out.toString());

  }

  /**
   * Test if mosaic option throws error if user value is empty.
   */
  @Test
  public void processImageMosaicEmptyUserValueTest() {
    feature.editOptionSelection(EditingOptions.MOSAIC.toString());
    view.setUserProvideSelectionValues("50");
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.mosaicImage() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if mosaic throws error if no value is provided.
   */
  @Test
  public void processImageMosaicWrongCommandTest() {
    feature.editOptionSelection(EditingOptions.MOSAIC.toString());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("CommandGeneratorImpl: Invalid mosaic command, "
            + "must be in format: mosaic <seeds_values>.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if pixelate option throws error if user value is empty.
   */
  @Test
  public void processImagePatternEmptyUservalueTest() {
    // Test for empty user value provided
    feature.editOptionSelection(EditingOptions.GENERATE_PATTERN.toString());
    view.setUserProvideSelectionValues("");
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Pixelation/ Mosaic/ Dither/ Pattern user provided value"
            + " must be specified and must be a non zero number. Provided: ");

    assertEquals(expectedValue.toString(), out.toString());

  }

  /**
   * Test if pixelate option throws error if user value is blank.
   */
  @Test
  public void processImagePatternBlankUserValueTest() {
    // Test for blank user value provided
    feature.editOptionSelection(EditingOptions.GENERATE_PATTERN.toString());
    view.setUserProvideSelectionValues(" ");
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Pixelation/ Mosaic/ Dither/ Pattern user provided value "
            + "must be specified and must be a non zero number. Provided:  ");
    assertEquals(expectedValue.toString(), out.toString());

  }

  /**
   * Test if generate pattern option throws error if user value is non-numerical.
   */
  @Test
  public void processImagePatternNonNumericUserValueTest() {

    // Test for non numerical user value provided
    feature.editOptionSelection(EditingOptions.GENERATE_PATTERN.toString());
    view.setUserProvideSelectionValues("12a");
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Pixelation/ Mosaic/ Dither/ Pattern user provided value must be "
            + "specified and must be a non zero number. Provided: 12a");
    assertEquals(expectedValue.toString(), out.toString());

  }

  /**
   * Test if generate pattern option throws error if pattern bean generated is
   * null.
   */
  @Test
  public void processImagePatternPatternBeanNull() {
    feature.editOptionSelection(EditingOptions.GENERATE_PATTERN.toString());
    view.setUserProvideSelectionValues("50");
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getUserProvideSelectionValues() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IModelMock.generatePattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Pattern could not be generated.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if generate pixelate option works correctly.
   */
  @Test
  public void processImagePatternTest() {
    feature.editOptionSelection(EditingOptions.GENERATE_PATTERN.toString());
    view.setUserProvideSelectionValues("50");
    model.setPatternBean(new IPatternBeanMock());
    view.setUploadedImagePath("user/image.jpg");
    feature.processImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setSelectedMenu() Invoked\n")
        .append("IImageProcessingViewMock.setProcessingValuesVisible() Invoked\n")
        .append("IModelMock.getImage() Invoked\n").append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test for nul constructor arguments.
   */
  @Test
  public void testNullArguments() {
    try {
      new UIController(null, commandGenerator, clientUtility, model);
    } catch (IllegalArgumentException e) {
      assertEquals("UIController: Arguments must not be null", e.getMessage());
    }
    try {
      new UIController(commandController, null, clientUtility, model);
    } catch (IllegalArgumentException e) {
      assertEquals("UIController: Arguments must not be null", e.getMessage());
    }
    try {
      new UIController(commandController, commandGenerator, clientUtility, model);
    } catch (IllegalArgumentException e) {
      assertEquals("UIController: Arguments must not be null", e.getMessage());
    }
    try {
      new UIController(commandController, commandGenerator, null, model);
    } catch (IllegalArgumentException e) {
      assertEquals("UIController: Arguments must not be null", e.getMessage());
    }
    try {
      new UIController(commandController, commandGenerator, clientUtility, null);
    } catch (IllegalArgumentException e) {
      assertEquals("UIController: Arguments must not be null", e.getMessage());
    }
  }


  /**
   * Test generate pattern with custom colors null image.
   */
  @Test
  public void generatePatternWithCustomColorsNullImage() {
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must load the image before proceeding to process the image.");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test generate pattern with custom colors null uploaded and processed image.
   */
  @Test
  public void generatePatternWithCustomColorsNullAllImage() {
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must load the image before proceeding to process the image.");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test generate pattern with custom colors null selected colors set.
   */
  @Test
  public void generatePatternWithCustomColorsNullSelectedColors() {
    view.setUploadedImagePath("user/image.jpg");
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Custom colors are not selected.");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test generate pattern with custom colors empty selected colors set.
   */
  @Test
  public void generatePatternWithCustomColorsEmptySelectedColors() {
    view.setUploadedImagePath("user/image.jpg");
    view.setSelectedColorForPattern(new LinkedHashSet<>());
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Custom colors are not selected.");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test if error thrown in message if the number of chunks not given.
   */
  @Test
  public void generatePatternWithCustomColorsChunkNull() {
    view.setUploadedImagePath("user/image.jpg");
    Set<String> colors = new LinkedHashSet<>();
    colors.add("1");
    colors.add("2");
    view.setSelectedColorForPattern(colors);
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.getNumberOfChunksForPattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Number of chunks for pattern generation must be a number greater than zero");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test if error thrown in message if the number of chunks empty.
   */
  @Test
  public void generatePatternWithCustomColorsInvalidChunkEmpty() {
    view.setUploadedImagePath("user/image.jpg");
    view.setNumberOfChunksForPattern("");
    Set<String> colors = new LinkedHashSet<>();
    colors.add("1");
    colors.add("2");
    view.setSelectedColorForPattern(colors);
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.getNumberOfChunksForPattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Number of chunks for pattern generation must be a number greater than zero");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test if error thrown in message if the number of chunks 0.
   */
  @Test
  public void generatePatternWithCustomColorsInvalidChunkZero() {
    view.setUploadedImagePath("user/image.jpg");
    view.setNumberOfChunksForPattern("0");
    Set<String> colors = new LinkedHashSet<>();
    colors.add("1");
    colors.add("2");
    view.setSelectedColorForPattern(colors);
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.getNumberOfChunksForPattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Number of chunks for pattern generation must be a number greater than zero");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test if error thrown in message if the number of chunks 0.
   */
  @Test
  public void generatePatternWithCustomColorsInvalidChunkNotNumber() {
    view.setUploadedImagePath("user/image.jpg");
    view.setNumberOfChunksForPattern("12d");
    Set<String> colors = new LinkedHashSet<>();
    colors.add("1");
    colors.add("2");
    view.setSelectedColorForPattern(colors);
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.getNumberOfChunksForPattern() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Number of chunks for pattern generation must be a number greater than zero");
    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test valid custom color pattern generation.
   */
  @Test
  public void generatePatternWithCustomColors() {
    view.setUploadedImagePath("user/image.jpg");
    view.setNumberOfChunksForPattern("40");
    view.setPatternBean(new IPatternBeanMock());
    model.setPatternBean(new IPatternBeanMock());
    Set<String> colors = new LinkedHashSet<>();
    colors.add("1");
    colors.add("2");
    view.setSelectedColorForPattern(colors);
    feature.generatePatternWithCustomColors();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IImageProcessingViewMock.getSelectColorsForPattern() Invoked\n")
        .append("IImageProcessingViewMock.getNumberOfChunksForPattern() Invoked\n")
        .append("IModelMock.generatePattern() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n")
        .append("IImageProcessingViewMock.showPattern() Invoked\n")
        .append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n");

    assertEquals(expectedValue.toString(), this.out.toString());
  }

  /**
   * Test if run batch file gives error when batch text is null.
   */
  @Test
  public void runBatchFileNull() {
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getBatchFileText() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Nothing to read from the batch file.");
    feature.runBatchFile();
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test if run batch file gives error when batch text is null.
   */
  @Test
  public void runBatchFileEmpty() {
    StringBuilder expectedValue = new StringBuilder();
    view.setBatchFile("");
    expectedValue.append("IImageProcessingViewMock.getBatchFileText() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Nothing to read from the batch file.");
    feature.runBatchFile();
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test the run batch file works correctly.
   */
  @Test
  public void testRunBatchFile() {
    StringBuilder expectedValue = new StringBuilder();
    view.setBatchFile("load salad.jpg\n" + "mosaic 1650\n" + "save salad-mosaic-1650.jpg");
    expectedValue.append("IImageProcessingViewMock.getBatchFileText() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("IModelMock.mosaicImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("Image processing command \"mosaic 1650\" called successfully.\n");
    feature.runBatchFile();
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test saving when image is not loaded.
   */
  @Test
  public void testSaveFileUnloaded() {
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n").append(
            "You must load and process the image before proceeding to save the image/ pattern.");
    assertEquals(expectedValue.toString(), out.toString());

  }

  /**
   * Test saving when image is loaded.
   */
  @Test
  public void testSaveFileLoadedImage() {

    view.setUploadedImagePath("user/image.jpg");
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n").append(
            "You must load and process the image before proceeding to save the image/ pattern.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test saving when image is processed but file name not given.
   */
  @Test
  public void testSaveFileProcessedImageFileNameNull() {

    view.setUploadedImagePath("user/image.jpg");
    feature.saveFile();

    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n").append(
            "You must load and process the image before proceeding to save the image/ pattern.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test saving when image is processed and file name is given.
   */
  @Test
  public void testSaveFileProcessedImageFileNameGiven() {

    view.setUploadedImagePath("user/image.jpg");
    view.setFileName("user/picture.jpg");
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n").append(
            "You must load and process the image before proceeding to save the image/ pattern.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  /**
   * Test saving when pattern is generated.
   */
  @Test
  public void testSaveFilePattern() {

    view.setUploadedImagePath("user/image.jpg");
    view.setPatternBean(new IPatternBeanMock());
    view.setFileName("user/pattern.txt");
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n").append(
            "You must load and process the image before proceeding to save the image/ pattern.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testLoadImage() {
    feature.loadImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.loadImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showLoadedImage() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testLoadImageException() {
    clientUtility = new ClientUtilityImpl();
    UIController uiController = new UIController(commandController, commandGenerator, clientUtility,
        model);
    uiController.setView(view);
    feature = uiController;

    feature.loadImage();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.loadImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("ClientUtilityImpl: String argument cannot be null and empty");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void resetProcessing() {
    feature.resetProcessing();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.toggleRemoveColorButton() Invoked\n")
        .append("IImageProcessingViewMock.toggleReplaceColorButton() Invoked\n")
        .append("IImageProcessingViewMock.resetPattern() Invoked\n")
        .append("IImageProcessingViewMock.resetProcess() Invoked\n")
        .append("IModelMock.clearProcessing() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void patternWithCustomColorSelected() {
    feature.patternWithCustomColorSelected();
    StringBuilder expectedValue = new StringBuilder(
        "IImageProcessingViewMock.popupCustomColorsDialog() Invoked\n");

    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void replacePatternColorSelection() {
    feature.replacePatternColorSelection();
    StringBuilder expectedValue = new StringBuilder(
        "IImageProcessingViewMock.popupSelectColorDialog() Invoked\n");

    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testShowMessage() {
    feature.showMessage("Message");
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.setMessage() Invoked\n").append("Message");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSaveFileNullLoadedImage() {
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("You must load and process the image before proceeding to save the image/ "
            + "pattern.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSaveFileNullSavePath() {
    feature.loadImage();
    view.setUploadedImagePath("user/image.jpg");
    model.setImage(
        ImageFactory.buildImage(ImageType.STANDARD, 1, 1, new int[][][] { { { 1, 1, 1 } } }));
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.loadImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showLoadedImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.saveFile() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n").append("Not a valid path: null");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSaveFilePatternBean() {
    feature.loadImage();
    Image image = ImageFactory.buildImage(ImageType.STANDARD, 1, 1,
        new int[][][] { { { 1, 1, 1 } } });
    this.model.setPatternBean(new PatternBean(image, "abc", new LinkedHashSet<>()));
    this.view.setFileName(null);
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.loadImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showLoadedImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.saveFile() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Pattern and file data must be given.\n").append(" file name:null");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSaveFileProcessedImage() {
    feature.loadImage();
    view.setUploadedImagePath("user/image.jpg");
    model.setImage(
        ImageFactory.buildImage(ImageType.STANDARD, 1, 1, new int[][][] { { { 1, 1, 1 } } }));
    this.view.setFileName("user/image.jpg");
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.loadImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showLoadedImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n").append("IModelMock.getImage() Invoked\n")
        .append("IModelMock.getImage() Invoked\n")
        .append("IImageProcessingViewMock.saveFile() Invoked\n")
        .append("Invoked MockClientUtility.saveImageFile\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Image saved to file location: user/image.jpg");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSaveFilePatterGenerated() {
    feature.loadImage();
    view.setUploadedImagePath("user/image.jpg");
    Image image = ImageFactory.buildImage(ImageType.STANDARD, 1, 1,
        new int[][][] { { { 1, 1, 1 } } });
    this.model.setPatternBean(new PatternBean(image, "abc", new LinkedHashSet<>()));

    this.view.setFileName("user/pattern.txt");
    feature.saveFile();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.loadImage() Invoked\n")
        .append("IImageProcessingViewMock.getLoadedImagePath() Invoked\n")
        .append("Invoked MockClientUtility.loadImage\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showLoadedImage() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.saveFile() Invoked\n")
        .append("Invoked MockClientUtility.saveTextFile\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("File saved to file location: user/pattern.txt");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testReplaceColorsNullColorValues() {
    feature.replacePatternColor();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("To remove the color, first select the color from image.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testReplaceColorsValidOldColorValue() {
    this.view.setToReplaceColor("1");
    feature.replacePatternColor();

    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.getReplacingColor() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Please provide the new color to replace.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testReplaceColorsValidBothColorValues() {
    this.view.setToReplaceColor("1");
    this.view.setReplacingColor("2");
    Image image = ImageFactory.buildImage(ImageType.STANDARD, 1, 1,
        new int[][][] { { { 1, 1, 1 } } });
    this.model.setPatternBean(new PatternBean(image, "abc", new LinkedHashSet<>()));

    feature.replacePatternColor();

    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.getReplacingColor() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.getReplacingColor() Invoked\n")
        .append("IModelMock.replaceColor() Invoked\n")
        .append("Invoked MockClientUtility.getBufferedImage\n")
        .append("IImageProcessingViewMock.showProcessedImage() Invoked\n")
        .append("IImageProcessingViewMock.showPattern() Invoked\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testReplaceColorsValidBothColorValuesButNotProcessed() {
    this.view.setToReplaceColor("1");
    this.view.setReplacingColor("2");
    feature.replacePatternColor();

    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.getReplacingColor() Invoked\n")
        .append("IModelMock.getPatternBean() Invoked\n")
        .append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.getReplacingColor() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Image is not processed to replace the color.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testRemovePatternColorNullOldColr() {
    feature.removePatternColor();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("To remove the color first select the color from image.");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testRemovePatternColorValidOldColor() {
    this.view.setToReplaceColor("3");
    feature.removePatternColor();
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.getPattern() Invoked\n")
        .append("IImageProcessingViewMock.getToReplaceColor() Invoked\n")
        .append("IImageProcessingViewMock.setMessage() Invoked\n")
        .append("Image is not processed to replace the color.");
    assertEquals(expectedValue.toString(), out.toString());
  }

}