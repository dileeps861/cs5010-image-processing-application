import static org.junit.Assert.assertEquals;

import images.command.ImageProcessingCommand;
import images.constants.EditingOptions;
import images.controller.CommandGenerator;
import images.controller.CommandGeneratorImpl;
import images.model.IModelMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test CommanadGenrator implementation.
 * 
 * @author dileepshah
 *
 */
public class CommandGeneratorImplTest {
  private CommandGenerator commandGenerator;
  private StringBuilder out;
  private IModelMock model;

  @Before
  public void setUp() throws Exception {
    commandGenerator = new CommandGeneratorImpl();
    out = new StringBuilder();
    model = new IModelMock(out);
  }

  @Test
  public void createCommand() {

    // Test Blur
    ImageProcessingCommand commandObject = commandGenerator
        .createCommand(EditingOptions.BLUR.toString(), new IModelMock(out));
    StringBuilder expectedValue = new StringBuilder("BlurImageCommand{}");

    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test Sharpen
    commandObject = commandGenerator.createCommand(EditingOptions.SHARPEN.toString(), model);
    expectedValue = new StringBuilder("SharpenImageCommand{}");

    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test GrayScale
    commandObject = commandGenerator.createCommand(EditingOptions.GRAYSCALE.toString(), model);
    expectedValue = new StringBuilder("GrayscaleImageCommand{}");
    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test Sepia tone
    commandObject = commandGenerator.createCommand(EditingOptions.SEPIA_TONE.toString(), model);
    expectedValue = new StringBuilder("SepiaToneCommand{}");
    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test Dither
    commandObject = commandGenerator.createCommand(EditingOptions.DITHER.toString() + " 2", model);
    expectedValue = new StringBuilder("DitherImageCommand{colorDensity=2}");
    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test Mosaic
    commandObject = commandGenerator.createCommand(EditingOptions.MOSAIC.toString() + " 150",
        model);
    expectedValue = new StringBuilder("MosaicImageCommand{seeds=150}");
    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test Pixelate
    commandObject = commandGenerator.createCommand(EditingOptions.PIXELATE.toString() + " 20",
        model);
    expectedValue = new StringBuilder("PixelateImageCommand{numberOfSquares=20}");
    assertEquals(expectedValue.toString(), commandObject.toString());

    // Test Default
    try {
      commandGenerator.createCommand("XYZ", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals("CommandGeneratorImpl: No appropriate command found.", exception.getMessage());
    }

    // Test Errors
    try {
      commandGenerator.createCommand("dither", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals("CommandGeneratorImpl: Dither image must be in format \"dither <noOfColors>\"",
          exception.getMessage());
    }
    try {
      commandGenerator.createCommand("dither a", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Dither image no of colors must be a number greater than zero.",
          exception.getMessage());
    }

    try {
      commandGenerator.createCommand("dither 12a", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Dither image no of colors must be a number greater than zero.",
          exception.getMessage());
    }

    try {
      commandGenerator.createCommand("dither 0", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Dither image no of colors must be a number greater than zero.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("mosaic", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Invalid mosaic command, must be in format: mosaic <seeds_values>.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("mosaic 12d", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Invalid mosaic command, must be in format: mosaic <seeds_values>.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("mosaic 0", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Invalid mosaic command, must be in format: mosaic <seeds_values>.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("pixelate", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Number of squares must be specified and must be more than zero.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("pixelate cbc", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Number of squares must be specified and must be more than zero.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("pixelate 123d", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Number of squares must be specified and must be more than zero.",
          exception.getMessage());
    }

    try {
      commandObject = commandGenerator.createCommand("pixelate o", model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals(
          "CommandGeneratorImpl: Number of squares must be specified and must be more than zero.",
          exception.getMessage());
    }

    // test model command
    try {
      commandObject = commandGenerator.createCommand(null, model);
      throw new AssertionError("Must throw an exception.");
    } catch (IllegalArgumentException exception) {
      assertEquals("Command and Model cannot be null.", exception.getMessage());
    }

  }

  @Test
  public void testToString() {
    assertEquals("CommandGeneratorImpl{}", commandGenerator.toString());
  }
}