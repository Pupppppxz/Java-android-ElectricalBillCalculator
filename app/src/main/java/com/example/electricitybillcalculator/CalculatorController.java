package com.example.electricitybillcalculator;

public class CalculatorController {
    private ItemCalculator[] items;
    private String month, currentSelected;
    private int unit;
    private double price;
    private Electrical electrical = new Electrical();

    public CalculatorController(ItemCalculator[] items) {
        this.items = items;
    }

    public CalculatorController(ItemCalculator[] items, String month, int unit) {
        this.items = items;
        this.month = month;
        this.unit = unit;
        this.currentSelected = "";
    }
    
    public void toggleItemClick(String name) {
        System.out.println(name);
        for (ItemCalculator item: items) {
            if (name.equalsIgnoreCase(item.getName())) {
                System.out.println("click");
                item.toggleBG(true);
                this.setCurrentSelected(name);
            } else {
                item.toggleBG(false);
            }
        }
    }

    public void calculate() {
        this.price = electrical.calculate(unit);
    }

    public double getPrice() {
        return price;
    }

    public String getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(String currentSelected) {
        this.currentSelected = currentSelected;
    }

    public ItemCalculator getItemByName(String name) {
        for (ItemCalculator item: items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return items[0];
    }
}
