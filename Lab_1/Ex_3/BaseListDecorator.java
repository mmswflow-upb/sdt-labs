package Lab_1.Ex_3;

public abstract class BaseListDecorator implements MyList {

    protected final MyList wrappee;

    public BaseListDecorator(MyList wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void add(int value) {
        wrappee.add(value);
    }

    @Override
    public int get(int index) {
        return wrappee.get(index);
    }
}
 
