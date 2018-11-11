package sample.models;

public class Months
{
    int id_month;
    String name;

    public Months() { }

    public Months(int id_month, String name) {
        this.id_month = id_month;
        this.name = name;
    }

    public int getId_month() {
        return id_month;
    }

    public void setId_month(int id_month) {
        this.id_month = id_month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Months{" +
                "id_month=" + id_month +
                ", name='" + name + '\'' +
                '}';
    }
}
