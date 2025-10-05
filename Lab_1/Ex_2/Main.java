package Lab_1.Ex_2;

public class Main {
    Car car1 = new Car.Builder("Ford", 2018, 120f, FuelType.Diesel, "X1")
    .sound(SoundSystemType.Bluetooth).air(ACType.Climate_Control).sound(SoundSystemType.RadioCD).build();
    Car car2 = new Car.Builder("Toyota", 2022, 180f, FuelType.Gas, "T2").build();
}
