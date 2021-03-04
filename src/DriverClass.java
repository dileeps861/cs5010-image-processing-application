import java.io.IOException;

import images.image.Image;
import images.image.ImageFactory;
import images.image.ImageType;
import images.ImageUtilities;
import images.processing.ImageProcessingUtilities;

/**
 * The driver class does images processing and saves it.
 *
 * @author dileepshah
 */
public class DriverClass {

  public static void main(String[] args) {

    try {
      String[] images = new String[] { "boston.jpeg", "flower.jpeg", "hatka.jpg" };
      String destinationDirectory = "res/generated-images/";
      for (String fileName : images) {
        int[][][] imageArray = ImageUtilities.readImage("res/" + fileName);

        Image image = ImageFactory.builImage(ImageType.STANDARD, imageArray.length,
            imageArray[0].length, imageArray);
        String[] fileNameArray = fileName.split("\\.");
        Image transformedImage = image;
        int count = 0;
        while (count < 2) {
          transformedImage = ImageProcessingUtilities.sharpenImage(transformedImage);
          ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
              transformedImage.getHeight(),
              destinationDirectory + fileNameArray[0] + "-sharpened-image-" + count + ".jpg");
          System.out.println("Image with name: " + fileName + ", sharpened successfully and saved "
              + "with name: '" + fileNameArray[0] + "-sharpened-image-" + count + ".jpg'");
          count++;
        }

        transformedImage = image;
        count = 0;
        while (count < 2) {
          transformedImage = ImageProcessingUtilities.blurImage(transformedImage);
          ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
              transformedImage.getHeight(),
              destinationDirectory + fileNameArray[0] + "-blurred-image-" + count + ".jpg");
          System.out.println("Image with name: " + fileName + ", blurred successfully and saved "
              + "with name: '" + fileNameArray[0] + "-blurred-image-" + count + ".jpg'");
          count++;
        }

        transformedImage = ImageProcessingUtilities.transformImgeColorToSepiaTone(image);
        ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
            transformedImage.getHeight(),
            destinationDirectory + fileNameArray[0] + "-sepia-tone-.jpg");
        System.out.println("Image with name: " + fileName + ", transformed to sepia tone "
            + "created successfully and saved " + "with name: '" + fileNameArray[0]
            + "-sepia-tone-.jpg'");
        transformedImage = ImageProcessingUtilities.transformImageColorToGrayScale(image);
        ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
            transformedImage.getHeight(),
            destinationDirectory + fileNameArray[0] + "-gray-scale-.jpg");
        System.out.println("Image with name: " + fileName + ", transformed to gray scaled "
            + "created successfully and saved " + "with name: '" + fileNameArray[0]
            + "-gray-scale-.jpg'");
        transformedImage = ImageProcessingUtilities.eightColorDensity(image);
        ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
            transformedImage.getHeight(),
            destinationDirectory + fileNameArray[0] + "-eight-reduced-.jpg");

        System.out.println("Image with name: " + fileName + ", reduced to eight color created"
            + "successfully and saved with name: '" + fileNameArray[0] + "-eight-reduced-.jpg'");

        transformedImage = ImageProcessingUtilities.eightColorDensityWithEssence(image);
        ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
            transformedImage.getHeight(),
            destinationDirectory + fileNameArray[0] + "-eight-essence-.jpg");
        System.out.println("Image with name: " + fileName + ", essence eight color created"
            + "successfully and saved with name: '" + fileNameArray[0] + "-eight-essence-.jpg'");

        transformedImage = ImageProcessingUtilities.sixteenColorDensity(image);
        ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
            transformedImage.getHeight(),
            destinationDirectory + fileNameArray[0] + "-sixteen-reduced-.jpg");
        System.out.println("Image with name: " + fileName + ", reduced to sixteen color created"
            + "successfully and saved with name: '" + fileNameArray[0] + "-sixteen-reduced-.jpg'");

        transformedImage = ImageProcessingUtilities.sixteenColorDensityWithEssence(image);
        ImageUtilities.writeImage(transformedImage.getImage(), transformedImage.getWidth(),
            transformedImage.getHeight(),
            destinationDirectory + fileNameArray[0] + "-sixteen-essence-.jpg");
        System.out.println("Image with name: " + fileName + ", essence sixteen color created"
            + "successfully and saved with name: '" + fileNameArray[0] + "-eight-sixteen-.jpg'");
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

}
