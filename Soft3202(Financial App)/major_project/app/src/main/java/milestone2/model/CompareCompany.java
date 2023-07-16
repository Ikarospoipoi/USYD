package milestone2.model;

import milestone2.view.CompanyPojo;

public class CompareCompany {
    private CompanyPojo companyPojo;
    private CompanyPojo CompareCompany;
    private String argument;

    public CompareCompany(CompanyPojo companyPojo, CompanyPojo SavedCompanyPojo, String argument) {
        this.companyPojo = companyPojo;
        this.CompareCompany = SavedCompanyPojo;
        this.argument = argument;
    }

    /**
     * This method is used to compare the company detail
     */
    public String CompareData(){
        if(companyPojo.getSaved_Data() == null || CompareCompany.getSaved_Data() == null){
            return "No Data";
        }
        if(companyPojo.getSaved_Data().get(companyPojo.getSaved_Data().size() -1) == -1 || CompareCompany.getSaved_Data().get(CompareCompany.getSaved_Data().size() -1) == -1){
            return "No Data";
        }
        String result = "";
        if(companyPojo.getSaved_Data().get(companyPojo.getSaved_Data().size() -1 ) < CompareCompany.getSaved_Data().get(CompareCompany.getSaved_Data().size() -1)){
            result = "This company's "+ companyPojo.getType()+ " chart value is bigger!";
        }
        else{
            result = "False";
        }
        return result;
    }

    /**
     * This method is used to get the company detail
     */
    public String FinalResult(){
        if(argument.equals("offline")){
            return "Compare Result: This is a Dummy compare result. \nThis company's Dividend Payout chart value is bigger!";
        }

        if(CompareData().equals("No Data")||CompareData().equals("False")){
            return "False";
        }
        return CompareData();
    }

    public void setCompanyPojo(CompanyPojo companyPojo) {
        this.companyPojo = companyPojo;
    }

    public void setCompareCompany(CompanyPojo compareCompany) {
        this.CompareCompany = compareCompany;
    }
}
