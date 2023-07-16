package milestone2.model;

import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;

public class PasatebinString {
    GetFromWeb web = new GetFromWeb();
    HandleInput handleInput = new HandleInput();
    private String CompanyName;
    private JsonArray jsonArray;
    private String argument;
    private String Getweb_arg;
    private String cache_or_not;
    private ArrayList<Long> getdividendPayout;
    private ArrayList<String> Date;
    private ArrayList<Long> profitloss;
    private ArrayList<Long> capitalExpenditures;
    private ArrayList<Long> netIncome;
    private ArrayList<Long> operatingcashflow;


    public PasatebinString(String CompanyName , String argument, String Getweb_arg, JsonArray jsonArray,
                           ArrayList<Long> getdividendPayout, ArrayList<String> Date, ArrayList<Long> profitloss,
                           ArrayList<Long> capitalExpenditures, ArrayList<Long> netIncome, ArrayList<Long> operatingcashflow) {
        this.CompanyName = CompanyName;
        this.argument = argument;
        this.Getweb_arg = Getweb_arg;
        this.jsonArray = jsonArray;
        this.getdividendPayout = getdividendPayout;
        this.Date = Date;
        this.profitloss = profitloss;
        this.capitalExpenditures = capitalExpenditures;
        this.netIncome = netIncome;
        this.operatingcashflow = operatingcashflow;
    }

    /**
     * This method is used to set the company financial detail that will be paste to pastebin
     * @return String
     */
    public String SetString(){
        if(argument.equals("offline")){
            return "Company Name: BA";
        }
        if(Getweb_arg.equals("offline")){
            return "";
        }
        String result = "";

        String getdividendPayoutString = "None";
        String profitlossString = "None";
        String capitalExpendituresString = "None";
        String netIncomeString = "None";
        String operatingcashflowString = "None";
        for (int i = 0; i < Date.size(); i++) {
            try {
                getdividendPayoutString = getdividendPayout.get(i).toString();
            }catch (Exception e){
                getdividendPayoutString = "None";
            }

            try {
                profitlossString = profitloss.get(i).toString();
            }catch (Exception e){
                profitlossString = "None";
            }

            try {
                capitalExpendituresString = capitalExpenditures.get(i).toString();
            }catch (Exception e){
                capitalExpendituresString = "None";
            }

            try {
                netIncomeString = netIncome.get(i).toString();
            }catch (Exception e){
                netIncomeString = "None";
            }

            try {
                operatingcashflowString = operatingcashflow.get(i).toString();
            }catch (Exception e){
                operatingcashflowString = "None";
            }

            result += "Date: " + Date.get(i) + "," + "DividendPayout: " +  getdividendPayoutString + "," +"profitloss: " +  profitlossString + "," + "capitalExpenditures: " + capitalExpendituresString + "," + "netIncome: " + netIncomeString + "," + "operatingcashflow: " + operatingcashflowString + "\n";
        }

        return result;
    }

    /**
     * This method is used to set the Company Derail that will be paste to pastebin
     * @return String
     */
    public String GetCompanyDetail(){
        if(argument.equals("offline")){
            return "Dummy Data";
        }
        if(Getweb_arg.equals("offline")){
             return "1. Symbol: BA\n" +
                    "2. Name: Boeing Company\n" +
                    "3. type: Equity\n" +
                    "4. region: United States\n" +
                    "5. Market Open: 09:30\n" +
                    "6. Market Close: 16:00\n" +
                    "7. time zone: UTC-04\n" +
                    "8. currency: USD\n" +
                    "9. match Score: 1.0000\n"+
                     "Date: 2017; DividendPayout: 1;profitlossString: 1; capitalExpenditures: 1; netIncome:1; operatingcashflow:1";

        }
        String result = getGetFromWeb().CompanyDetail(CompanyName,argument);
        return result;
    }

    /**
     * This method is used to combine the String and get the final result
     * @return String
     */
    public String FinalOutput(){
        if(argument.equals("offline")){
            return "Dummy Data";
        }
        String detail = GetCompanyDetail();
        String result = SetString();
        return detail + "\n" + "\n"+ result;
    }

    /**
     * @param getFromWeb
     * This method is used to set the http handler
     * @return
     */
    public void setGetFromWeb(GetFromWeb getFromWeb) {
        this.web = getFromWeb;
    }

    /**
     * This method is used to get the http handler
     * @return GetFromWeb
     */
    public GetFromWeb getGetFromWeb() {
        return web;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
//        PasatebinString pasatebinString = new PasatebinString("BA", "10-K");
//        System.out.println(pasatebinString.SetString());
    }
}
