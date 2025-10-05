package Lab_1.Ex_3;

public class LoggingDecorator extends BaseListDecorator {
    
    public LoggingDecorator(MyList list) {
        super(list);
    }

    @Override
    public void add(int n){
        
        System.out.println(n + " was added to the list");
        super.add(n);
    }

    @Override
    public int get(int index) {
        
        return super.get(index);
    }
}
