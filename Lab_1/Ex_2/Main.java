package Lab_1.Ex_2;

public class Main {
    public static void main(String[] args){
        Car car1 = new Car.Builder("Ford", 2018, 120f, FuelType.Diesel, "X1")
            .sound(SoundSystemType.RadioCD).air(ACType.Climate_Control).sound(SoundSystemType.Bluetooth).build();
        Car car2 = new Car.Builder("Toyota", 2022, 180f, FuelType.Gas, "T2").build();
    }
    
}
