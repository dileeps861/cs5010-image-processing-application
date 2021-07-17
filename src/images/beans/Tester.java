package images.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tester {
  public static void m1() {
    List<List<PatternBean>> pile = new ArrayList<>(4);
    System.out.println(pile.size());
    pile.add(new ArrayList<>());
  }
  public static void main(String[] args) {
    m1();
  }
}
