package sample.models;

public class InvoicesView {
    private int no;
    private String name_month, nameCustomer;
    private Double total, paid_amount;
    private String limit_date, paid_date, phone_number, name_plan;

    public InvoicesView() {
    }

    public InvoicesView(int no,String name_month, String nameCustomer, Double total, Double paid_amount, String limit_date, String paid_date, String phone_number, String name_plan) {
        this.no = no;
        this.name_month = name_month;
        this.nameCustomer = nameCustomer;
        this.total = total;
        this.paid_amount = paid_amount;
        this.limit_date = limit_date;
        this.paid_date = paid_date;
        this.phone_number = phone_number;
        this.name_plan = name_plan;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName_month() {
        return name_month;
    }

    public void setName_month(String name_month) {
        this.name_month = name_month;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(Double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(String limit_date) {
        this.limit_date = limit_date;
    }

    public String getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(String paid_date) {
        this.paid_date = paid_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName_plan() {
        return name_plan;
    }

    public void setName_plan(String name_plan) {
        this.name_plan = name_plan;
    }
}
