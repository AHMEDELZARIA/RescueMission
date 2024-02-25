package ca.mcmaster.se2aa4.island.team220;

public class ExpenseTracker{
    public int totalExpense;

    public void expenseTracker(){
        this.totalExpense = 0;
    }

    public int getCost(int cost){
        totalExpense += cost;
        return totalExpense;
    }
}