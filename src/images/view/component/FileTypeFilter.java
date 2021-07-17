package images.view.component;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Filter to filter only image files type while loading the image.
 */
public class FileTypeFilter extends FileFilter {
  private final String extension;
  private final String description;

  /**
   * Constructor to create filter with given extension and description.
   * 
   * @param description description of the file type
   * @param extension   extension of the file
   */
  public FileTypeFilter(String description, String extension) {
    this.extension = extension;
    this.description = description;
  }

  @Override
  public boolean accept(File f) {
    if (f == null) {
      throw new IllegalArgumentException("Type of file type filter must not be null.");
    }
    if (f.isDirectory()) {
      return true;
    }
    return f.getName().endsWith(extension);
  }

  @Override
  public String getDescription() {
    return description + String.format(" (*%s)", extension);
  }
}
