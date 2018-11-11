package sample.models;

public class City
{
    int id_city;
    String name_city;

    public City() { }

    public City(int id_city, String name_city) {
        this.id_city = id_city;
        this.name_city = name_city;
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }
}
