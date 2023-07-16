package milestone2.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class HandleInput {

    /**
     * @param array
     * @param argument
     * @return ArrayList<Long>
     * This method is used to handle the capital expenditure data
     */
    public ArrayList OperatingCashFlow(JsonArray array, String argument) {
        if(argument.equals("offline")){
            ArrayList<Long> data = new ArrayList<>();
            data.add(0L);
            data.add(1L);
            data.add(2L);
            data.add(3L);
            data.add(4L);
            return data;
        }
            ArrayList<Long> list = new ArrayList<>();
            try {
                for (JsonElement element : array) {
                    try {
                        long operatingCashflow = element.getAsJsonObject().get("operatingCashflow").getAsLong();
                        list.add(0, operatingCashflow);
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                ArrayList<Long> error = new ArrayList<>();
                error.add(-1L);
                return error;
            }
            return list;
    }

    /**
     * @param array
     * @param argument
     * @return ArrayList<String>
     * This method is used to handle the date data
     */
    public ArrayList GetYear(JsonArray array, String argument) {
        if(argument.equals("offline")){
            ArrayList<String> data = new ArrayList<>();
            data.add("2017");
            data.add("2018");
            data.add("2019");
            data.add("2020");
            data.add("2021");
            return data;
        }
            ArrayList<String> list = new ArrayList<>();
            try {
                for (JsonElement element : array) {
                    try {
                        String fiscalDateEnding = element.getAsJsonObject().get("fiscalDateEnding").getAsString();
                        list.add(0, fiscalDateEnding);
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                ArrayList<String> error = new ArrayList<>();
                error.add("No Data");
                return error;
            }

            return list;

    }

    /**
     * @param array
     * @param argument
     * @return ArrayList<Long>
     * This method is used to handle the Capital Expenditure data
     */
    public ArrayList GetcapitalExpenditures(JsonArray array, String argument) {
        if(argument.equals("offline")){
            ArrayList<Long> data = new ArrayList<>();
            data.add(0L);
            data.add(1L);
            data.add(2L);
            data.add(3L);
            data.add(4L);
            return data;
        }
            ArrayList<Long> list = new ArrayList<>();
            try {
                for (JsonElement element : array) {
                    try {
                        Long capitalExpenditures = element.getAsJsonObject().get("capitalExpenditures").getAsLong();
                        list.add(0, capitalExpenditures);
                    } catch (Exception e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                ArrayList<Long> error = new ArrayList<>();
                error.add(-1L);
                return error;
            }

            return list;
    }

    /**
     * @param array
     * @param argument
     * @return ArrayList<Long>
     * This method is used to handle the Profit Loss data
     */
    public ArrayList GetprofitLoss(JsonArray array, String argument) {
        if(argument.equals("offline")){
            ArrayList<Long> data = new ArrayList<>();
            data.add(0L);
            data.add(1L);
            data.add(2L);
            data.add(3L);
            data.add(4L);
            return data;
        }

            ArrayList<Long> list = new ArrayList<>();
        try {
            for (JsonElement element : array) {
                try {
                    Long profitLoss = element.getAsJsonObject().get("profitLoss").getAsLong();
                    list.add(0, profitLoss);
                } catch (Exception e) {
                    continue;
                }
            }
        }catch (Exception e){
            ArrayList<Long> error = new ArrayList<>();
            error.add(-1L);
            return error;
        }

            return list;
    }

    /**
     * @param array
     * @param argument
     * @return ArrayList<Long>
     * This method is used to handle the Dividend Payout data
     */
    public ArrayList GetDividendPayout(JsonArray array, String argument) {
        if(argument.equals("offline")){
            ArrayList<Long> data = new ArrayList<>();
            data.add(0L);
            data.add(1L);
            data.add(2L);
            data.add(3L);
            data.add(4L);
            return data;
        }
            ArrayList<Long> list = new ArrayList<>();
        try {
            for (JsonElement element : array) {
                try {
                    Long dividendPayout = element.getAsJsonObject().get("dividendPayout").getAsLong();
                    list.add(0, dividendPayout);
                } catch (Exception e) {
                    continue;
                }
            }
        }catch (Exception e){
            ArrayList<Long> error = new ArrayList<>();
            error.add(-1L);
            return error;
        }

            return list;
    }

    /**
     * @param array
     * @param argument
     * @return ArrayList<Long>
     * This method is used to handle the Net Income data
     */
    public ArrayList GetnetIncome(JsonArray array, String argument) {
        if(argument.equals("offline")){
            ArrayList<Long> data = new ArrayList<>();
            data.add(0L);
            data.add(1L);
            data.add(2L);
            data.add(3L);
            data.add(4L);
            return data;
        }


        ArrayList<Long> list = new ArrayList<>();
        try {
            for (JsonElement element : array) {
                try {
                    Long netIncome = element.getAsJsonObject().get("netIncome").getAsLong();
                    list.add(0, netIncome);
                } catch (Exception e) {
                    continue;
                }
            }
            if(list.size() == 0){
                ArrayList<Long> error = new ArrayList<>();
                error.add(-1L);
            }
        }catch (Exception e){
            ArrayList<Long> error = new ArrayList<>();
            error.add(-1L);
            return error;
        }
        return list;
    }

}
