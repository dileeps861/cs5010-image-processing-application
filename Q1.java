package audible;

import java.util.*;

public class Q2 {
    public static List<String> getItems(List<List<String>> entries)
    {
        List<String> result = new ArrayList<>();

        PriorityQueue<Item> sortedItems = new PriorityQueue<>();
        int times = 0;
        for (List<String> entry : entries){
            if(entry.get(0).equals("INSERT")){
                sortedItems.add(new Item(entry.get(1), Integer.parseInt(entry.get(2))));
            }
            else {
                Iterator<Item> itemIterator = sortedItems.iterator();
                int count = 0;
                Item visitedItem = null;
                while (count <= times){
                    visitedItem = itemIterator.next();
                    count++;
                }
                result.add(visitedItem.itemName);
                times++;
            }
        }
        return result;
    }

    private static class Item implements Comparable<Item>{
        String itemName;
        int price;

        public Item(String itemName, int price) {
            this.itemName = itemName;
            this.price = price;
        }

        @Override
        public int compareTo(Item o) {
            if(this.price == o.price){
                return this.itemName.compareTo(o.itemName);
            }
            return Integer.compare(this.price, o.price);
        }
    }
    public static void main(String[] args) {
        List<List<String>> entries = new ArrayList<>();
        entries.add(Arrays.asList("INSERT", "ruler", "4"));
        entries.add(Arrays.asList("VIEW", "-", "-"));
        entries.add(Arrays.asList("INSERT", "notecards", "2"));
        entries.add(Arrays.asList("VIEW", "-", "-"));
        entries.add(Arrays.asList("INSERT", "notebook", "9"));
        entries.add(Arrays.asList("INSERT", "backpack", "10"));
        entries.add(Arrays.asList("INSERT", "pens", "6"));
        entries.add(Arrays.asList("INSERT", "pencils", "5"));
        entries.add(Arrays.asList("VIEW", "-", "-"));
        System.out.println(getItems(entries));
    }
}
