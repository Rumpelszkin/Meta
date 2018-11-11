package com.company;

public class Item {

    int index;
    int profit;
    int weight;
    int assignedNode;

    public Item(int index, int profit, int weight, int assignedNode) {
        this.index = index;
        this.profit = profit;
        this.weight = weight;
        this.assignedNode = assignedNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getAssignedNode() {
        return assignedNode;
    }

    public void setAssignedNode(int assignedNode) {
        this.assignedNode = assignedNode;
    }
}
