package app;

public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private String cor;

    public Carro(int id, String marca, String modelo, String cor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
    }

    public String toString(){
        return "Id: " + id + ", Marca: " + marca + ", Modelo: " + modelo + ", Cor: " + cor;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }
    public Carro(){

    }
}
