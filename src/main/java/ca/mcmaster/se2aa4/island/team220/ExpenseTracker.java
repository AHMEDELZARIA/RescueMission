package ca.mcmaster.se2aa4.island.team220;

public class ExpenseTracker{
    //initialize public variable totalExpense as type int
    public int totalExpense;

    public void expenseTracker(){
        //initialize totalExpense to 0
        this.totalExpense = 0;
    }

    public int getCost(int cost){
        //adds the cost to the totalExpense and then returns totalExpense
        totalExpense += cost;
        return totalExpense;
    }
}