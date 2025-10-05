package Lab_1.Ex_2;

public class Car {
    private String brand;
    private int productionYear;
    private float enginePower;
    private FuelType fuelType;
    private String chassisNumber;
    private SoundSystemType soundSystem;
    private NavigationType navigation;
    private ACType ac;

    private Car(Builder builder){
        this.brand = builder.brand;
        this.productionYear = builder.productionYear;
        this.enginePower = builder.enginePower;
        this.fuelType = builder.fuelType;
        this.chassisNumber = builder.chassisNumber;
        this.soundSystem = builder.soundSystem;
        this.navigation = builder.navigation;
        this.ac = builder.airConditioning;
    }
    // Car is immutable
    public String getBrand() { return brand; }
    public int getProductionYear() { return productionYear; }
    public float getEnginePower() { return enginePower; }
    public FuelType getFuelType() { return fuelType; }
    public String getChassisNumber() { return chassisNumber; }
    public SoundSystemType getSoundSystem() { return soundSystem; }
    public NavigationType getNavigation() { return navigation; }
    public ACType getAirConditioning() { return ac; }


    //Has some necessary parameters and optional ones (sound, navigation, ac)
     public static class Builder {
        // Required fields
        private final String brand;
        private final int productionYear;
        private final float enginePower;
        private final FuelType fuelType;
        private final String chassisNumber;

        // Optional fields with defaults
        private SoundSystemType soundSystem = SoundSystemType.RadioCD;
        private NavigationType navigation = NavigationType.None;
        private ACType airConditioning = ACType.Manual;

        // Builder constructor for required fields
        public Builder(String brand, int productionYear, float enginePower, FuelType fuelType, String chassisNumber) {
            this.brand = brand;
            this.productionYear = productionYear;
            this.enginePower = enginePower;
            this.fuelType = fuelType;
            this.chassisNumber = chassisNumber;
        }

        // Optional setters that return Builder (chained)
        public Builder sound(SoundSystemType soundSystem) {
            this.soundSystem = soundSystem;
            return this;
        }

        public Builder navigation(NavigationType navigation) {
            this.navigation = navigation;
            return this;
        }

        public Builder air(ACType airConditioning) {
            this.airConditioning = airConditioning;
            return this;
        }

        // Build method creates the immutable Car
        public Car build() {
            return new Car(this);
        }

    }
}

