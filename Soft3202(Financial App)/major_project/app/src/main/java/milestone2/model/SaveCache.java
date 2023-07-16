package milestone2.model;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class SaveCache {
    private HandleInput handleInput = new HandleInput();
    private String argument;
    private String company_symbol;
    private JsonArray jsonArray;
    private Database db = new Database();

    public SaveCache(String argument, String company_symbol, JsonArray jsonArray) {
        this.argument = argument;
        this.company_symbol = company_symbol;
        this.jsonArray = jsonArray;
    }

    /**
     * @param update_or_add
     * This method is used to save or change the data to the database
     */
    public boolean Change_Cache(String update_or_add){
        System.out.println("I am in Change_Cache: " + jsonArray);
        if(argument.equals("online")) {
            db.setupDB();
            long ce = 0;
            long dp = 0;
            long ni = 0;
            long oc = 0;
            long pl = 0;
            ArrayList<Long> capitalexpenditures = handleInput.GetcapitalExpenditures(jsonArray, argument);
            ArrayList<Long> dividendPayout = handleInput.GetDividendPayout(jsonArray, argument);
            ArrayList<Long> netIncome = handleInput.GetnetIncome(jsonArray, argument);
            ArrayList<Long> operatingcashflow = handleInput.OperatingCashFlow(jsonArray, argument);
            ArrayList<Long> profitloss_ls = handleInput.GetprofitLoss(jsonArray, argument);
            ArrayList<String> year = handleInput.GetYear(jsonArray, argument);
            Boolean check = false;

            for (int i = 0; i < year.size(); i++) {
                try {
                    ce = capitalexpenditures.get(i);
                } catch (Exception e) {
                    capitalexpenditures.add(0l);
                }
                try {
                    dp = dividendPayout.get(i);
                } catch (Exception e) {
                    dividendPayout.add(0l);
                }
                try {
                    ni = netIncome.get(i);
                } catch (Exception e) {
                    netIncome.add(0l);
                }
                try {
                    oc = operatingcashflow.get(i);
                } catch (Exception e) {
                    operatingcashflow.add(0l);
                }
                try {
                    pl = profitloss_ls.get(i);
                } catch (Exception e) {
                    profitloss_ls.add(0l);
                }
                if(ce!= -1l && dp!= -1l && ni!= -1l && oc!= -1l && pl!= -1l) {
                    if (update_or_add.equals("update")) {
                        if(check == false) {
                            db.DeleteSpecificCompany(company_symbol);
                            check = true;
                        }
                        db.add_cache(year.get(i), ce, dp, ni, oc, pl, company_symbol);
                    } else {
                        db.add_cache(year.get(i), ce, dp, ni, oc, pl, company_symbol);
                    }
                }else{
                    System.out.println("error");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param db
     * This method is used to set the database
     */
    public void setDatabase(Database db){
        this.db = db;
    }

    /**
     * @param handleInput
     * This method is used to set the String handler(used to handle the input)
     */
    public void setHandleInput(HandleInput handleInput){
        this.handleInput = handleInput;
    }

}
