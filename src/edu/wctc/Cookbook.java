package edu.wctc;

import java.util.ArrayList;

public class Cookbook {

    // Hold all the meals that are read in from the file
    //private Meal[] meals = new Meal[100];
    private ArrayList<Meal> meals = new ArrayList<>();
    // Hold the next (empty) index in the array
    private int i = 0;

    public void addElementWithStrings(String mealTypeStr, String mealNameStr, String caloriesStr) {
        MealType mealType;

        // Do we have room in the array for one more?
        if (i <= meals.size()) {

            // Find the correct enum using a switch? Or use .fromValue() instead?
            switch (mealTypeStr) {
                case "Breakfast":
                    mealType = MealType.BREAKFAST;
                    break;
                case "Lunch":
                    mealType = MealType.LUNCH;
                    break;
                case "Dinner":
                    mealType = MealType.DINNER;
                    break;
                case "Dessert":
                    mealType = MealType.DESSERT;
                    break;
                default: //Kris Changed default
                    mealType = MealType.BREAKFAST;
                    System.out.println("Meal Creation Error: Invalid Meal Type " + mealTypeStr + ", defaulted to Breakfast.");
            }

            int calories;

            if (caloriesStr.matches("-?\\d+(\\.\\d+)?")) {
                calories = Integer.parseInt(caloriesStr);
            } else {
                calories = 100;
                System.out.println("Meal Creation Error: Invalid Calories " + caloriesStr + ", defaulted to 100.");
            }
            meals.add(new Meal(mealType, mealNameStr, calories));
        } else {
            System.out.println("Meal Creation Error: Items exceeded system size.  File has " + i + ", while the system can only handle " + meals.size() + ".");
        }
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void printAllMeals() {
        for (Meal item : meals) {
            if (item != null) {
                System.out.println(item);
            }
        }
    }

    public void printMealsByType(MealType mealType) {
        for (Meal item : meals) {
            if (item != null && item.getMealType() == mealType) {
                System.out.println(item);
            }
        }
    }

    public void printByNameSearch(String s) {
        // Maybe add a message if no match found?
        for (Meal item : meals) {
            // Maybe make this case-insensitive?
            if (item != null && item.getItem().indexOf(s) >= 0) {
                System.out.println(item);
            }
        }
    }

    //added by Kris Kristiansen
    public void printByControlBreak() {
        System.out.println("Meal Type\tTotal\t\tMean\tMin\t\tMax\t\tMedian");
        ArrayList <Integer> nums = new ArrayList<>();
        MealType m = null; //Start with Dummy
        for (Meal item : meals) {
            if (item != null) {
                if(m != item.getMealType()) {
                    if (m == null) {
                        m = item.getMealType();
                    } else {
                        //Show totals
                        System.out.printf("%-12s", m.getMeal());
                        printTotals(nums);
                        //reset
                        m = item.getMealType();
                        nums.clear();
                    }
                }
                nums.add(item.getCalories());
            }
        }
        //Show last totals
        System.out.printf("%-12s",m.getMeal());
        printTotals(nums);
    }

    private void printTotals(ArrayList<Integer> nums) {
        int total = 0, min = -1, max = 0, median;
        double mean;
        for (int val : nums) {
            if (min == -1) min = val; //dummy to set first value
            if (min > val) min = val;
            if (max < val) max = val;
            total = total + val;
        }
        mean = (double)total / nums.size();
        //Sort Nums here
        median = nums.get(nums.size()/2);
        System.out.printf("%5d%11.4f%7d%8d%11d\n", total, mean, min, max, median);
    }
}
