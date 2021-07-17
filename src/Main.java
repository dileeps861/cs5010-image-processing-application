import images.controller.CommandController;
import images.controller.CommandControllerImpl;
import images.controller.CommandGenerator;
import images.controller.CommandGeneratorImpl;
import images.controller.ImageProcessingController;
import images.controller.ImageProcessingControllerImpl;
import images.controller.UIController;
import images.model.IModel;
import images.model.Model;
import images.utilities.ClientUtility;
import images.utilities.ClientUtilityImpl;
import images.view.IImageProcessingView;
import images.view.IPViewJFrame;
import images.view.UIActionListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * Main class to run the project.
 * 
 * @author dileepshah
 *
 */
public class Main {

  /**
   * Main method, boot-loader of the program.
   * 
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    try {
      if (args.length == 0) {
        throw new IllegalArgumentException("Valid command must be provided for running the jar");
      }
      // Model of the project.
      CommandController commandController = new CommandControllerImpl();

      // Command generator to generate command objects from given string command
      CommandGenerator commandGenerator = new CommandGeneratorImpl();

      // Client utility object, handles file handling tasks.
      ClientUtility clientUtility = new ClientUtilityImpl();

      // Model
      IModel model = new Model();

      String argument = args[0];

      if (argument.contains("-script")) {
        if (args.length < 2) {
          throw new IllegalArgumentException("Not a valid valid file name given.");

        }
        System.out.println("Reading the commands from file: " + args[1]);
        System.out.println("Your commands are being processed...\n"
            + "Please wait for a while before I finish your given tasks... ");

        File file = new File(args[1]);

        try (InputStream inputStream = new FileInputStream(file)) {
          InputStreamReader in = new InputStreamReader(inputStream);
          StringBuilder out = new StringBuilder();

          ImageProcessingController controller = new ImageProcessingControllerImpl(in, out,
              commandController, commandGenerator, clientUtility, model);
          controller.start();
          System.out.println(out);
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      } else if (argument.contains("-interactive")) {
        // The controller cum observer.
        UIController controller = new UIController(commandController, commandGenerator,
            clientUtility, model);

        IImageProcessingView view = new IPViewJFrame("Image Processing");
        // Set the view in the controller

        controller.setView(view);
        view.addActionListener(new UIActionListener(controller));
      } else {
        throw new IllegalArgumentException("Not a valid option provided to this the jar");
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
