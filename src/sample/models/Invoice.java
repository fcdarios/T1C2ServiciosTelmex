package sample.models;

import java.sql.Date;

public class Invoice
{
    private int no_invoice;
    private Date limit_date;
    private Months id_month;
    private Plans id_plan;
    private Customer id_customer;
    private Double paid_amount;
    private Date paid_date;

    public Invoice(int no_invoice, Date limit_date, Months id_month, Plans id_plan, Customer id_customer,
                   Double paid_amount, Date paid_date) {
        this.no_invoice = no_invoice;
        this.limit_date = limit_date;
        this.id_month = id_month;
        this.id_plan = id_plan;
        this.id_customer = id_customer;
        this.paid_amount = paid_amount;
        this.paid_date = paid_date;
    }
    public Invoice(){ }

    public int getNo_invoice() {
        return no_invoice;
    }

    public void setNo_invoice(int no_invoice) {
        this.no_invoice = no_invoice;
    }

    public Date getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(Date limit_date) {
        this.limit_date = limit_date;
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

    public Customer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Customer id_customer) {
        this.id_customer = id_customer;
    }

    public Double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(Double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public Date getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(Date paid_date) {
        this.paid_date = paid_date;
    }
}
