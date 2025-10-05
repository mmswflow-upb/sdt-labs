package Lab_1.Ex_1;

public class MyArrayList implements MyList {
    
    private int[] array;
    private int size;

    public MyArrayList(){
        size = 0;
        array = new int[10];
    }

    @Override
    public void add(int n) {
        if(size >= array.length){
            grow();
        }
        array[size++] = n;
    }

    public void grow(){
        
        int[] newArray = new int[size];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    @Override
    public int get(int index) {
        if(index > size) throw new IndexOutOfBoundsException();
        return array[index];
    } 
}
