package sample.models;

public class City
{
    int id_city;
    String name;

    public City() { }

    public City(int id_city, String name) {
        this.id_city = id_city;
        this.name = name;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id_city=" + id_city +
                ", name='" + name + '\'' +
                '}';
    }
}
