package milestone2.model;

import java.util.ArrayList;

public class DatabaseHandler {
    private String CompanyName;
    private Database database = new Database();
    public DatabaseHandler(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    /**
     * This method is used to get the date
     * @return ArrayList<String>
     */
    public ArrayList<String> GetDate(){
        ArrayList<String> date = new ArrayList<>();
        date = database.SelectDate(CompanyName);
        return date;
    }

    /**
     * This method is used to get the Profit Loss
     * @return ArrayList<Long>
     */
    public ArrayList<Long> GetProfitLoss(){
        ArrayList<Long> ProfitLoss = new ArrayList<>();
        ProfitLoss = database.SelectProfitLoss(CompanyName);
        return ProfitLoss;
    }

    /**
     * This method is used to get the Net Income
     * @return ArrayList<Long>
     */
    public ArrayList<Long> GetNetIncome(){
        ArrayList<Long> NetIncome = new ArrayList<>();
        NetIncome = database.SelectNetIncome(CompanyName);
        return NetIncome;
    }

    /**
     * This method is used to get the Operating Cashflow
     * @return ArrayList<Long>
     */
    public ArrayList<Long> GetOperatingCashflow(){
        ArrayList<Long> OperatingCashflow = new ArrayList<>();
        OperatingCashflow = database.SelectOperatingCashflow(CompanyName);
        return OperatingCashflow;
    }

    /**
     * This method is used to get the Dividend Payout
     * @return ArrayList<Long>
     */
    public ArrayList<Long> GetDividendPayout(){
        ArrayList<Long> DividendPayout = new ArrayList<>();
        DividendPayout = database.SelectDividendPayout(CompanyName);
        return DividendPayout;
    }

    /**
     * This method is used to get the Capital Expenditure
     * @return ArrayList<Long>
     */
    public ArrayList<Long> GetCapitalExpenditure(){
        ArrayList<Long> CapitalExpenditure = new ArrayList<>();
        CapitalExpenditure = database.SelectCapitalExpenditure(CompanyName);
        return CapitalExpenditure;
    }

    /**
     * This method is used to set the database
     */
    public void setDatabase(Database database) {
        this.database = database;
    }
}
