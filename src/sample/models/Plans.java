package sample.models;

public class Plans
{
    int id_plan, total;
    String nombre_plan;

    public Plans() { }

    public Plans(int id_plan, int total, String nombre_plan) {
        this.id_plan = id_plan;
        this.total = total;
        this.nombre_plan = nombre_plan;
    }

    public int getId_plan() {
        return id_plan;
    }

    public void setId_plan(int id_plan) {
        this.id_plan = id_plan;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNombre_plan() {
        return nombre_plan;
    }

    public void setNombre_plan(String nombre_plan) {
        this.nombre_plan = nombre_plan;
    }

    @Override
    public String toString() {
        return "Plans{" +
                "id_plan=" + id_plan +
                ", total=" + total +
                ", nombre_plan='" + nombre_plan + '\'' +
                '}';
    }
}
