package milestone2.model;

import com.google.gson.JsonArray;

public class CompanyDetailHandler {
    private String companyName;
    private String argument;
    GetFromWeb getFromWeb = new GetFromWeb();

    public CompanyDetailHandler(String companyName, String argument) {
        this.companyName = companyName;
        this.argument = argument;
    }

    /**
     * This method is used to get the company detail
     */
    public String CompanyDetail() {
        String result = getGetFromWeb().CompanyDetail(companyName, argument);
        return result;
    }

    /**
     * This method is used to get the company financial detail
     */
    public JsonArray CompanyFinacial() {
        JsonArray result = getGetFromWeb().CompanyFinancialDetail(companyName, argument);
        return result;
    }

    /**
     * This method is used to set the http handler
     */
    public void setGetFromWeb(GetFromWeb getFromWeb) {
        this.getFromWeb = getFromWeb;
    }

    /**
     * This method is used to get the http handler
     */
    public GetFromWeb getGetFromWeb() {
        return getFromWeb;
    }
}
