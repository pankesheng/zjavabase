package test.zcj.demo.designpattern.forms.decorator;

// 额外加一个鸡蛋
class JiDan extends Material{  
      
    public JiDan(Noodle noodle){  
        super(noodle);  
    }  
  
    @Override  
    public String getDescriptin() {  
        return noodle.getDescriptin()+" + 鸡蛋";  
          
    }  
  
    @Override  
    public double cost() {  
        return noodle.cost()+1.5;  
    }  
      
} 