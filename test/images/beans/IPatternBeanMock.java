package images.beans;

import images.model.image.Image;

import java.util.Set;


/**
 * Mock for IPatternBean interface.
 * @author dileepshah
 *
 */
public class IPatternBeanMock implements IPatternBean {
  @Override
  public Image getImage() {
    return null;
  }

  @Override
  public String getPattern() {
    return null;
  }

  @Override
  public Set<String> getLegend() {
    return null;
  }
}
