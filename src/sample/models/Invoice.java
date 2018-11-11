package sample.models;

import java.sql.Date;

public class Invoice
{
    int no_invoice;
    String limit_date, phone_number;
    Customer id_customer;
    Months id_month;
    Plans id_plan;
    double paid_amount;
    Date paid_date;

    public Invoice(){ }

    public Invoice(int no_invoice, String limit_date, String phone_number, Customer id_customer, Months id_month, Plans id_plan) {
        this.no_invoice = no_invoice;
        this.limit_date = limit_date;
        this.phone_number = phone_number;
        this.id_customer = id_customer;
        this.id_month = id_month;
        this.id_plan = id_plan;
    }

    public int getNo_invoice() {
        return no_invoice;
    }

    public void setNo_invoice(int no_invoice) {
        this.no_invoice = no_invoice;
    }

    public String getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(String limit_date) {
        this.limit_date = limit_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Customer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Customer id_customer) {
        this.id_customer = id_customer;
    }

    public Months getId_month() {
        return id_month;
    }

    public void setId_month(Months id_month) {
        this.id_month = id_month;
    }

    public Plans getId_plan() {
        return id_plan;
    }

    public void setId_plan(Plans id_plan) {
        this.id_plan = id_plan;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public Date getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(Date paid_date) {
        this.paid_date = paid_date;
    }
}
