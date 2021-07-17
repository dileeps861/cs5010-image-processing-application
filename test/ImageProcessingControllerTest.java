import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import images.command.DitherImageCommand;
import images.command.GrayscaleImageCommand;
import images.command.ImageProcessingCommand;
import images.command.MosaicImageCommand;
import images.command.PixelateImageCommand;
import images.command.SharpenImageCommand;
import images.controller.CommandController;
import images.controller.CommandControllerImpl;
import images.controller.CommandGenerator;
import images.controller.CommandGeneratorImpl;
import images.controller.ImageProcessingController;
import images.controller.ImageProcessingControllerImpl;
import images.model.IModel;
import images.model.IModelMock;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;
import images.utilities.ClientUtility;
import images.utilities.ClientUtilityImpl;
import images.utilities.MockClientUtility;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * Test class to test Controller.
 * 
 * @author dileepshah
 *
 */
public class ImageProcessingControllerTest {
  private ImageProcessingController controller;
  private CommandGenerator commandGenerator;
  private CommandController commands;
  private Readable in;
  private Appendable out;
  private ClientUtility clientUtility;
  private IModel model;

  @Before
  public void setUp() throws Exception {
    this.in = new StringReader("");
    this.out = new StringBuilder();
    this.commands = new CommandControllerImpl();
    this.commandGenerator = new CommandGeneratorImpl();
    this.model = new IModelMock(out);

    this.clientUtility = new MockClientUtility(out);

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
  }

  @Test
  public void testPattern() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("pixelate 50\n").append("pattern\n")
        .append("save salad-pattern.txt");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("IModelMock.pixelateImage() Invoked\n")
        .append("Image processing command \"pixelate 50\" called successfully.\n")
        .append("IModelMock.generatePattern() Invoked\n")
        .append("Image processing command \"pattern\" called successfully.\n")
        .append("No Pattern generated to save.\n")

        .append("Pattern with pattern file name \"salad-pattern.txt\", saved successfully.\n");
    assertEquals(expectedValue.toString(), out.toString());
  }
  
  @Test
  public void testPatternWithChunk() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("pixelate 50\n").append("pattern 50\n")
        .append("save salad-pattern.txt");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("IModelMock.pixelateImage() Invoked\n")
        .append("Image processing command \"pixelate 50\" called successfully.\n")
        .append("IModelMock.generatePattern() Invoked\n")
        .append("Image processing command \"pattern 50\" called successfully.\n")
        .append("No Pattern generated to save.\n")

        .append("Pattern with pattern file name \"salad-pattern.txt\", saved successfully.\n");
    assertEquals(expectedValue.toString(), out.toString());
  }
  
  @Test
  public void testPatternWithInvalidChunk() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("pixelate 50\n").append("pattern ab\n")
        .append("save salad-pattern.txt");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("IModelMock.pixelateImage() Invoked\n")
        .append("Image processing command \"pixelate 50\" called successfully.\n")
        .append("IModelMock.generatePattern() Invoked\n")
        .append("Image processing command \"pattern ab\" called successfully.\n")
        .append("No Pattern generated to save.\n")

        .append("Pattern with pattern file name \"salad-pattern.txt\", saved successfully.\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testPixelate() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("pixelate 50\n")
        .append("save salad-pixelate.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")

        .append("IModelMock.pixelateImage() Invoked\n")
        .append("Image processing command \"pixelate 50\" called successfully.\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testMosaic() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("mosaic\n").append("save salad-mosaic.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("CommandGeneratorImpl: Invalid mosaic command, ")
        .append("must be in format: mosaic <seeds_values>.\n");

    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testBlur() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("blur\n").append("save salad-blur.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("IModelMock.blurImage() Invoked\n")
        .append("Image processing command \"blur\" called successfully.\n");

    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSharpen() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("sharpen\n").append("save salad-sharpen.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")

        .append("IModelMock.sharpenImage() Invoked\n")
        .append("Image processing command \"sharpen\" called successfully.\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testSepiaTone() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("sepia-tone\n")
        .append("save salad-sepia-tone.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();

    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("IModelMock.transformImageColorToSepiaTone() Invoked\n")
        .append("Image processing command \"sepia-tone\" called successfully.\n");

    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testGrayscale() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("grayscale\n")
        .append("save salad-grayscale.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")

        .append("IModelMock.transformImageColorToGrayScale() Invoked\n")
        .append("Image processing command \"grayscale\" called successfully.\n");

    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testDither() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("dither 2\n").append("save salad-dither.jpg");

    in = new StringReader(stringBuilder.toString());

    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      throw new AssertionError("Error occurred.");
    }
    StringBuilder expectedValue = new StringBuilder();
    expectedValue.append("Invoked MockClientUtility.loadImage\n")
        .append("Image with name salad.jpg loaded successfully.\n")
        .append("IModelMock.ditherImage() Invoked\n")
        .append("Image processing command \"dither 2\" called successfully.\n");
    assertEquals(expectedValue.toString(), out.toString());
  }

  @Test
  public void testController() {
    StringBuilder stringBuilder = new StringBuilder("load flower.jpeg\n");
    stringBuilder.append("blur\n").append("sharpen\n").append("save flower-sharpened.jpeg\n")
        .append("load salad.jpg\n").append("pattern\n").append("save salad-pattern.txt");

    in = new StringReader(stringBuilder.toString());
    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);

    try {
      controller.start();
    } catch (IOException e) {
      e.printStackTrace();
      assertEquals("IOException", e.getMessage());
    }

    assertEquals("Invoked MockClientUtility.loadImage\n"
        + "Image with name flower.jpeg loaded successfully.\n" + "IModelMock.blurImage() Invoked\n"
        + "Image processing command \"blur\" called successfully.\n"
        + "CommandControllerImpl: Image cannot be null.\n", out.toString());
    out = new StringBuilder();
    in = new StringReader("load DP_CUT.jpg\n" + "blur\n" + "sharpen\n" + "save DP_CUT_SHARPEN.jpg\n"
        + "load goat-original.png\n" + "pattern\n" + "save goat-pattern.txt");
    controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
        this.commandGenerator, clientUtility, model);
    try {
      controller.start();
    } catch (IOException e) {
      e.printStackTrace();
      assertEquals("IOException", e.getMessage());
    }
    assertEquals("Image with name DP_CUT.jpg loaded successfully.\n"
        + "Image processing command \"blur\" called successfully.\n"
        + "CommandControllerImpl: Image cannot be null.\n" + "", out.toString());
  }

  @Test
  public void testCommandController() {

    commands.setCommand(new SharpenImageCommand(model));

    commands.setCommand(new GrayscaleImageCommand(model));

    commands.setCommand(new GrayscaleImageCommand(model));

    commands.setCommand(new DitherImageCommand(model, 2));

    commands.setCommand(new MosaicImageCommand(model, 1000));

    commands.setCommand(new PixelateImageCommand(model, 50));
    Image image = ImageFactory.buildImage(ImageType.STANDARD, 1, 1, new int[1][1][3]);
    while (true) {
      Image img = commands.runCommand(image);
      if (img != null && img.getHeight() == 0) {
        break;
      }
    }

    assertEquals("IModelMock.sharpenImage() Invoked\n"
        + "IModelMock.transformImageColorToGrayScale() Invoked\n"
        + "IModelMock.transformImageColorToGrayScale() Invoked\n"
        + "IModelMock.ditherImage() Invoked\n" + "IModelMock.mosaicImage() Invoked\n"
        + "IModelMock.pixelateImage() Invoked\n", out.toString());

    commands.setCommand(new MosaicImageCommand(model, 100));
    try {
      commands.runCommand(null);
      new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException e) {
      assertEquals("CommandControllerImpl: Image cannot be null.", e.getMessage());
    }

    try {
      commands.setCommand(null);
      new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException e) {
      assertEquals("CommandControllerImpl: Command cannot be null.", e.getMessage());
    }

    new ImageProcessingControllerImpl(this.in, this.out, this.commands, this.commandGenerator,
        clientUtility, model);

    image = commands
        .runCommand(ImageFactory.buildImage(ImageType.STANDARD, 1, 1, new int[1][1][3]));
    assertEquals(null, image);

  }

  @Test
  public void testToStringCommandController() {
    ImageProcessingCommand imageProcessingCommand = new MosaicImageCommand(model, 500);
    commands.setCommand(imageProcessingCommand);
    assertTrue(commands.toString().contains("commands=[MosaicImageCommand{seeds=500}]"));

  }

  @Test
  public void testToStringController() {

    String actualValue = controller.toString();

    assertTrue(actualValue.contains("ImageProcessingControllerImpl{in="));
    assertTrue(actualValue.contains("out=, commandController=CommandControllerImpl{"));
    assertTrue(actualValue.contains("clientUtility=MockClientUtility"));
    assertTrue(actualValue.contains("model=IModelMock{}"));

  }

  @Test
  public void testNullArguments() {
    try {
      controller = new ImageProcessingControllerImpl(null, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      throw new AssertionError("Must throw and exception.");
    } catch (Exception e) {
      assertEquals("ImageProcessingControllerImpl: Arguments must not be null.", e.getMessage());
    }

    try {
      controller = new ImageProcessingControllerImpl(this.in, null, this.commands,
          this.commandGenerator, clientUtility, model);
      throw new AssertionError("Must throw and exception.");
    } catch (Exception e) {
      assertEquals("ImageProcessingControllerImpl: Arguments must not be null.", e.getMessage());
    }

    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, null, this.commandGenerator,
          clientUtility, model);
      throw new AssertionError("Must throw and exception.");
    } catch (Exception e) {
      assertEquals("ImageProcessingControllerImpl: Arguments must not be null.", e.getMessage());
    }

    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands, null,
          clientUtility, model);
      throw new AssertionError("Must throw and exception.");
    } catch (Exception e) {
      assertEquals("ImageProcessingControllerImpl: Arguments must not be null.", e.getMessage());
    }

    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, null, model);
      throw new AssertionError("Must throw and exception.");
    } catch (Exception e) {
      assertEquals("ImageProcessingControllerImpl: Arguments must not be null.", e.getMessage());
    }

    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, null);
      throw new AssertionError("Must throw and exception.");
    } catch (Exception e) {
      assertEquals("ImageProcessingControllerImpl: Arguments must not be null.", e.getMessage());
    }

  }

  @Test
  public void testCommandLength() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load\n").append("sharpen\n").append("save salad-sharpen.jpg");

    in = new StringReader(stringBuilder.toString());

    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      controller.start();
    } catch (Exception e) {
      throw new AssertionError("Must not throw and exception.");
    }

    assertEquals("Load command must be in format \"load <filename>.\"\n", out.toString());
    stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("sharpen\n").append("save");

    out = new StringBuilder();
    in = new StringReader(stringBuilder.toString());
    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      controller.start();
    } catch (Exception e) {
      throw new AssertionError("Must not throw and exception.");
    }

    assertEquals("Image with name salad.jpg loaded successfully.\n"
        + "Image processing command \"sharpen\" called successfully.\n"
        + "Save command must be in format \"save <filename>.\"\n" + "", out.toString());

    stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("pixelate\n").append("save salad-pixelate.jpg");

    out = new StringBuilder();
    in = new StringReader(stringBuilder.toString());
    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      controller.start();
      controller.start();
    } catch (Exception e) {
      throw new AssertionError("Must not throw and exception.");
    }

    assertEquals(
        "Image with name salad.jpg loaded successfully.\n"
            + "Pixelate command must be in format \"pixelate <number_of_squares>.\"\n" + "",
        out.toString());

    stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("dither\n").append("save salad-pixelate.jpg");

    out = new StringBuilder();
    in = new StringReader(stringBuilder.toString());
    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      controller.start();
      controller.start();
    } catch (Exception e) {
      throw new AssertionError("Must not throw and exception.");
    }

    assertEquals(
        "Image with name salad.jpg loaded successfully.\n"
            + "CommandGeneratorImpl: Dither image must be in format" + " \"dither <noOfColors>\"\n",
        out.toString());

  }

  @Test
  public void testFileNotFoundException() {
    clientUtility = new ClientUtilityImpl();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load saladd.jpg\n").append("dither\n").append("save salad-pixelate.jpg");

    out = new StringBuilder();
    in = new StringReader(stringBuilder.toString());
    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      controller.start();
    } catch (Exception e) {
      throw new AssertionError("Must not throw and exception.");
    }
    assertEquals(
        "Error while saving reading/ writing the file: saladd.jpg (No such file or directory)\n",
        out.toString());
  }

  @Test
  public void testPatternNull() {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("load salad.jpg\n").append("pattern\n").append("save salad-pixelate.jpg");

    out = new StringBuilder();
    in = new StringReader(stringBuilder.toString());
    try {
      controller = new ImageProcessingControllerImpl(this.in, this.out, this.commands,
          this.commandGenerator, clientUtility, model);
      controller.start();
    } catch (Exception e) {
      throw new AssertionError("Must not throw and exception.");
    }
    assertEquals("Image with name salad.jpg loaded successfully.\n"
        + "Image processing command \"pattern\" called successfully.\n"
        + "Image with name \"salad-pixelate.jpg\" saved successfully.\n", out.toString());
  }

  @Test
  public void testCommandControllerEmptyImage() {
    Image imageArg = ImageFactory.buildImage(ImageType.STANDARD, 1, 1,
        new int[][][] { { { 1, 1, 1 } } });
    Image image = commands.runCommand(imageArg);
    assertEquals(0, image.getWidth());
    assertEquals(0, image.getHeight());
    assertEquals("Dummy image.", image.copyImage().toString());
    assertEquals("Dummy image.", image.padImage(0).toString());
    assertEquals("Dummy image.", image.removePad(0).toString());
    assertEquals(0, image.getImage().length);

  }
}