# cs5010-project05-image-model-application

# Project 5 -- Image Model Application
## About/Overview.
##### Version 1
This project implements a image editing model. This project will take the image from user and put some interesting effects on image such as blur the image, sharpen the image, convert it to grayscale, convert to sepia tone, or reduce the color density of the image. 
##### Version 2
Some of the new features are added in the project in this release. Now our project supports image pixelation, pattern generation, and mosaic the image.
##### Version 3
The new revision includes a complete image processing application. The new version includes the UI to make the project more user friendly. Some of the new features added are, user can now run batch file as well as can do the image processing one by one. Also, cross stitch pattern includes color dmc color floss and and UI gives the feasibility to choose different colors.

## List of features.
List all features that are included in my program:
1. Transform the Image to gray scale.
2. Transform the Image to sepia tone.
3. Blur the Image.
4. Sharpen the Image.
5. Now support Reduce the color density with any number of color per channel.
6. Mosaic the the Image.
7. Pixelating the image with given number of desired chunks in the image.
8. Generating pattern of the pixlated or plain image.
9. Feature to interactively create and execute a batch-script from UI.
10. Ability to specify the image to be loaded and saved.
11. Ability to pick one color in a cross-stitch pattern that can then be removed from the pattern completely. The pixels of that color would now be a blank pixel.
12. Ability to exchange one color for another in a cross-stitch pattern by clicking on the color in a displayed pattern and the user can select a different color from the DMC color options given on the UI.
13. Displays cross-stitch patterns using the DMC floss colors.

###### Extra credit features:
1. Cross-stitch pattern to the screen will display both the symbol and the DMC floss color at the same time.
2. The user can select the DMC color palette to use in an image. The user can select the DMC color from pattern and replace it with the color they have on hand. 

## How To Run.
Instructions to run the program is as following:

##### How to run the jar file
1. **For batch processing:**
	Execute the command <code> java -jar cs5010_project_05.jar -script path-of-script-file</code>

2. **For user interface mode:**
	Execute the command <code> java -jar cs5010_project_05.jar -interactive </code>
	
##### Arguments needed:
   One argument is required to pass:
   1. In case of **batch processing mode,** batch file name along with complete path as an argument is required to pass to read the processing commands.
   2. In case of **UI mode** just provide -interactive as an argument.
   
## How to Use the Program.
##### For batch processing:
1. Execute the following command in terminal/ cmd:
   	<code> java -jar cs5010_project_05.jar -script path-of-script-file</code>
2. Now the program will pop-up the UI. 
3. Once all the commands read, you will receive the confirmation with what all are the task completed, with the task details.

##### For UI Mode:
1. Execute the following command in terminal/ cmd:
   	<code> java -jar cs5010_project_05.jar -interactive </code>
2. Now the program will pop-up the UI. 
3. You will see the different options on the UI, follow the labels and messages properly to understand the UI. Image processing options are given in the file menu, you can select the appropriate menu item for selecting the task.
4. All the editing options for image processing are given in the "Edit options" menu. However, for pattern generation with custom custom color is given in the "Custom Pattern Options" menu.
5. For all the editing options given in the "Edit Options" menu, user must click "Process image" button for processing the image.
6. For custom pattern options, after selecting option from  "Custom Pattern Options" menu, a pop-up box will pop and user must provide required value there and then click the "Generate Pattern" Button. This will automatically submit the processing request and user do not need to click "Process Image" Button specifically.
7. There is "Batch Processing" tab given on the UI, click on that tab to submit batch processing request from the user. Sample commands are given in the adjacent text box for the reference. 
8. To replace the color from the pattern generated image, first click on any chunk of the the image given in the "Processed Image" area. After click, UI will pop-up the selected color for the confirmation. If user is ok with the color, they can click on either of the "Remove Color" or "Replace color" buttons located inside the "Pattern Properties and Manipulation" panel. 
9. If replace color is selected, the UI will render a pop-up to select an new color, user can select the new color and press "Ok" button, the color will be replaced from the pattern.
10. If user selected remove color button, selected color will be completely removed from the pattern and in place of that, the blank color would be shown.

##### Note: 
1. Make sure that when running the program, input file is in the same directory where the jar file is.
2. Generated files are saved inside the "generated-files" folder, so you must have this folder created just where the jar file is located.
3. Make sure that all the images which are required to be processed are in the same folder where text files and jar file are located.
4. Make sure to give absolute(complete) path of the batch run text file in case of -script mode.

###### Sample path structures are as below:
	For text files and image files:</ br>
	   text file: D:/Desktop/res/input-1.txt
	   image file: /res/salad.jpg
	   jar file: /res/cs5010_project_05.jar
   So as per above path generated image and files path would be:</ br>
	image file: 	/res/generated-files/salad.jpg
	text file: /res/generated-files/salad-pattern.txt
4. Make sure not to omit the "-" from the command while executing the jar and providing the file name or interactive command.	   
	   
	   

	
###### Sample files given with the project:  
	1. flower.jpeg, salad.jpg
	2. input-1.txt, input-2.txt
##### FAQs in case of having trouble running the jar:
1. 	Getting command not found exception while executing <code>java -jar cs5010_project_05.jar</code> command.<br />
	<b>Solution:</b> Make sure JRE is installed in your machine and environment path is setuped correctly.
2. 	Error while saving reading/ writing the file: salad.jpg (No such file or directory).<br />
	<b>Solution:</b> Make sure you have image file in same folder where jar file is located. And also make sure where jar file is located you have folder "generated-files". If not make a new "generated-files" folder and also, move images which are to be edited to the folder where jar is. 

3. If in UI mode getting not supported JRE version, make sure you have JRE 11 installed.
4. java.io.FileNotFoundException while trying to run in -script mode.</br>
	**Solution:** Make sure you have provided the complete path of script 	file. FOr example, D:/Desktop/input.txt


## Design/Model Changes.
My program includes a few minor design changes which are as below:

1. My original design did not included the factory pattern for the classes, but as going ahead in designing the project UML, I realized that most of the object creation are to be done with default constructor parameters and don't require much of the user interaction and also to avoid unauthorized creation of object. I included factories for objects.
2. In original designed, I designed to have EigthColorDensity and SixteenColorDensity interfaces as I surmised that there would be some specific implementations for eight color and sixteen color density reduction. However, in the later stage of analysis and initial stage of development, I did not find any such USPs so removed these interfaces.
3. Removed AbstractDitherImage abstract class as it was being redundant, since the only dithering algorithms used as of now is FloydSteinbergDithering. Earlier I assumed that I might be a requirement to have some other algorithms as well.
4. Minor changes in class, variable, and methods name to give them more appropriate name or to correct the spelling.<br />
	A few name changes are:<br />
	<i>FilterImage ---> ImageFilter</i><br />
	<i>Kernal ---> Kernel</i><br />
	<i>ColorTransform ---> ImageColorTransform</i><br />
	<i>ImageProcessing(interface) ---> ImageUtilities(class with static methods)</i><br />
5. A few relationships are changed such as ImageColorTransform will have transform matrix and does not use Kernel, which was designed to be used in original design.
6. New methods copyImage(),padImage(), removePad() are added in Image object to clone the image object and to add or remove padding from the image.
7. All Methods of ImageUtilities are static and work as a single point of contact for the driver. Also, it does not store Image object anymore.
8. All the typeChecks are removed, because changed design was not required to check the types anywhere in the project.
9. None of the classes store the Image,rather take the image as parameter in its methods and return the new edited image object. This approach was incorporated to facilitate loose coupling.
10. New features to mosaic the image, pixelate the image, and generate pattern are added. So, design includes these features design  on top of previous project.
11. In preliminary design of project 4 ImageProcessingCommand interface was designed to be implemented by lambda expressions but now class based implementations are used as there was requirement to have some instance variables.
12. ClientUtility interface is added to improve loose coupling between controller and files manipulation.
13. ChunkGenerator interface is added to improve loose coupling between PixelateImage and PatternGenerator.
14. 8 pixel color reduce and 16 pixel color reduce implementations are replaced with generic implementation to improve code redundancy.
15. CommandGenerator interface is added to improve loose coupling between controller and command selection.
16. PatternUtilities classes is added to get chart of DMC color and DMC color code to character mapping.
17. ImageProcessingUtilities class is removed as it was temporary central class for project 3 and now no longer in use.
18. From project 5, includes the UI accessibility for better interactive mode.
19. Added interface features to segregate features from controller.
20. Added enum constants for action commands check, to make code less error prone.
21. Included the new JPanel, to support in hand DMC colors selection.
22. Included new Panel to pop-up color picker panel to included color remove feature. 
23. Included central model interface to provide one place access point for the model.
24. Included two new JFrames to facilitate color selections popup.
25. Included Abstract class AbstractPixelatePatternGanerate, to share common code between Pixelation and Pattern generation.
26. Included IPatternManipulate interface to manipulate pattern data.
27. Included IpatternBean to pass pattern related data in one go and to improve performance by avoiding multiple calculation for one task.



## Assumptions.
1. Maximum color range per channel in the image are 256.
2. User will pass the appropriate image.
3. User have knowledge on how to find any file path or folder path.
4. If pattern generation is called without calling pixelate, image would be considered already pixelated with chunk size of 1 and pattern will be generated accordingly.
5. Mosaic seed value must be more than zero.
6. Number of desired squares in the image pixelation must be more than zero.
7. If pattern generation is called without calling pixelation, user must provide number of squares before generating pattern.
8. User has basic UI understanding.
9. Blank color refers to the color black with RGB value=(0, 0, 0).
10. User must provide the number of chunks for generating pattern from UI. However, this condition is optional in case of batch processing if the previous command was to pixelate.



## Limitations.
1. Program is not sufficiently dynamic and user must need to provide correct path names and file name.
2. Color transformation supported only are gray scale and sepia tone. That too only one type of sepia tone or gray scale transformation is supported.
3. Only two types(blur and sharpen) of Image filters supported. That too one only one type of blurring or sharpening is supported.
4. Only one dithering technique used to retain the essence of the image in this project, so user must expect only one type of essence preservation.
5. Only RGB Colors encoding is supported by the project.
6. Pattern generation might be incorrect, if already pixelated image is passed without specifying the chunk size.
7. Files only from the location where jar is located can be fetched.
8. Files will be saved only in the location "generated-files" which must be in the same folder where jar is located.
9. User can only use drop down list for in hand color selection, color picker is not supported.
10. Adding a line of text on to the image supports only one default type of font.
11. Progragram is not synchronous, so user may face some delays in responses.
12. User must provide the number of chunk to generate the pattern, program cannot identify the chunk size, even if the image is already  pixelated.

## Citation.
1. Salad picture: Image by <a href="https://pixabay.com/users/galyafanaseva-19830457/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=5904093">Galina Afanaseva</a> from <a href="https://pixabay.com/?utm_source=link-attribution&amp;utm_medium=referral&amp;utm_campaign=image&amp;utm_content=5904093">Pixabay</a>
<br />
2. DMC chart is taken from: <a href="http://my.crazyartzone.com/dmc.asp">DMC Floss</a>
3. Flower picture: Image by Dileep Shah, self clicked and I give the full permission to use it anywhere.
4. UNICODE Characters are referenced from: <a href="https://en.wikipedia.org/wiki/List_of_Unicode_characters">Wekipidia</a>
5. UI guide: [A Visual Guide to Swing Components](https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html)
