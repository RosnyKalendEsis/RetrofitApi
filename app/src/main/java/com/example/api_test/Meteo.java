package com.example.api_test;

import java.util.List;

public class Meteo {
    private String product;
    private String init;
    private List<DataSerie> dataseries;

    public Meteo() {
    }

    public Meteo(String product, String init, List<DataSerie> dataSeries) {
        this.product = product;
        this.init = init;
        this.dataseries = dataSeries;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public List<DataSerie> getDataSeries() {
        return dataseries;
    }

    public void setDataSeries(List<DataSerie> dataSeries) {
        this.dataseries = dataSeries;
    }

}
class DataSerie{
    private int temp2m;
    private int transparency;

    public DataSerie() {
    }

    public DataSerie(int temp2m, int transparency) {
        this.temp2m = temp2m;
        this.transparency = transparency;
    }

    public int getTemp2m() {
        return temp2m;
    }

    public void setTemp2m(int temp2m) {
        this.temp2m = temp2m;
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }
}
