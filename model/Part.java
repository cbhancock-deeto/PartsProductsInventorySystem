
package model;

public abstract class Part {
    private int id, stock, min, max;
    private String name;
    private double price;
    
    public Part(int id, String name, double price, 
                       int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.min = min;
        this.stock = stock;
        this.max = max;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public void setStock(int stock){
        this.stock = stock;
    }
    
    public void setMin(int min){
        this.min = min;
    }
    
    public void setMax(int max){
        this.max = max;
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public int getStock(){
        return stock;
    }
    
    public int getMin(){
        return min;
    }
    
    public int getMax(){
        return max;
    }
    
}
