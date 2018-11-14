package sample.models;

public class Plans
{
    private int id_plan;
    private Double total;
    private String name_plan;

    public Plans() { }

    public Plans(int id_plan, String name_plan, Double total) {
        this.id_plan = id_plan;
        this.total = total;
        this.name_plan = name_plan;
    }

    public int getId_plan() {
        return id_plan;
    }

    public void setId_plan(int id_plan) {
        this.id_plan = id_plan;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getName_plan() {
        return name_plan;
    }

    public void setName_plan(String name_plan) {
        this.name_plan = name_plan;
    }
}
