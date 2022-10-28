package com.example.electricitybillcalculator;

public class ItemHistory extends Item{

    private double unit, price;
    private String month;

    public ItemHistory() {
        super();
    }

    public ItemHistory(String name, int icon) {
        super(name, icon);
    }

    public ItemHistory(String name, double unit, double price, String month) {
        super(name);
        this.unit = unit;
        this.price = price;
        this.month = month;
    }

    public ItemHistory(String name, int icon, double unit, double price, String month) {
        super(name, icon);
        this.unit = unit;
        this.price = price;
        this.month = month;
    }

    public void setIconByName() {
        switch (this.getName()) {
            case "iron":
                setIcon(R.drawable.iron);
                break;
            case "air":
                setIcon(R.drawable.air_conditioner);
                break;
            case "fan":
                setIcon(R.drawable.fan);
                break;
            case "fridge":
                setIcon(R.drawable.fridge);
                break;
            case "light":
                setIcon(R.drawable.light_bulb);
                break;
            case "microwave":
                setIcon(R.drawable.oven);
                break;
            case "rice":
                setIcon(R.drawable.rice_cooker);
                break;
            case "tv":
                setIcon(R.drawable.television);
                break;
            default:
                setIcon(R.drawable.washing_machine);
                break;
        }
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "ItemHistory{" +
                "name=" + this.getName() +
                ", unit=" + unit +
                ", price=" + price +
                ", month='" + month + '\'' +
                '}';
    }
}
