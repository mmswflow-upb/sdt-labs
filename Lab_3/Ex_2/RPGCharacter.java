package Ex_2;

public class RPGCharacter implements Cloneable {
    private String name;
    private CharacterClass characterClass;
    private String story;

    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;
    private int wisdom;
    private int charisma;

    public RPGCharacter(String name,
                        CharacterClass characterClass,
                        String story,
                        int strength,
                        int constitution,
                        int dexterity,
                        int intelligence,
                        int wisdom,
                        int charisma) {
        this.name = name;
        this.characterClass = characterClass;
        this.story = story;
        this.strength = strength;
        this.constitution = constitution;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    // Copy constructor (optional, but nice to have)
    public RPGCharacter(RPGCharacter other) {
        this.name = other.name;
        this.characterClass = other.characterClass;
        this.story = other.story;
        this.strength = other.strength;
        this.constitution = other.constitution;
        this.dexterity = other.dexterity;
        this.intelligence = other.intelligence;
        this.wisdom = other.wisdom;
        this.charisma = other.charisma;
    }

    @Override
    public RPGCharacter clone() {
        try {
            return (RPGCharacter) super.clone(); // all fields are primitives or immutable
        } catch (CloneNotSupportedException e) {
            // Fallback to copy constructor
            return new RPGCharacter(this);
        }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public String getStory() {
        return story;
    }

    public int getStrength() {
        return strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    @Override
    public String toString() {
        return "===== CHARACTER SHEET =====\n" +
               "Name: " + name + "\n" +
               "Class: " + characterClass + "\n" +
               "Story: " + story + "\n\n" +
               "Strength:     " + strength + "\n" +
               "Constitution: " + constitution + "\n" +
               "Dexterity:    " + dexterity + "\n" +
               "Intelligence: " + intelligence + "\n" +
               "Wisdom:       " + wisdom + "\n" +
               "Charisma:     " + charisma + "\n" +
               "============================";
    }
}
