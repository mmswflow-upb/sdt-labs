package Ex_2;

import java.util.Map;
import java.util.Scanner;

public class CharacterSetupApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CharacterPrototypeRegistry registry = new CharacterPrototypeRegistry();

        System.out.println("=== RPG Character Setup ===");
        System.out.println("1. Create character from scratch");
        System.out.println("2. Use and modify a predefined character (Prototype)");
        System.out.print("Choose an option (1/2): ");

        int choice = readInt(scanner);

        RPGCharacter character;

        if (choice == 1) {
            character = createFromScratch(scanner);
        } else {
            character = createFromPrototype(scanner, registry);
            if (character == null) {
                System.out.println("Falling back to creating from scratch.");
                character = createFromScratch(scanner);
            }
        }

        System.out.println();
        System.out.println("Your final character:");
        System.out.println(character);

        scanner.close();
    }

    private static RPGCharacter createFromScratch(Scanner scanner) {
        System.out.println("\n--- Create Character From Scratch ---");
        System.out.print("Enter character name: ");
        String name = readLine(scanner);

        System.out.println("Choose class:");
        for (CharacterClass cc : CharacterClass.values()) {
            System.out.println("- " + cc);
        }
        System.out.print("Class: ");
        CharacterClass characterClass = readClass(scanner);

        System.out.print("Enter character story: ");
        String story = readLine(scanner);

        int str = readStat(scanner, "Strength");
        int con = readStat(scanner, "Constitution");
        int dex = readStat(scanner, "Dexterity");
        int intel = readStat(scanner, "Intelligence");
        int wis = readStat(scanner, "Wisdom");
        int cha = readStat(scanner, "Charisma");

        return new RPGCharacter(name, characterClass, story, str, con, dex, intel, wis, cha);
    }

    private static RPGCharacter createFromPrototype(Scanner scanner, CharacterPrototypeRegistry registry) {
        System.out.println("\n--- Create Character From Prototype ---");
        System.out.println("Available prototypes:");

        for (Map.Entry<String, RPGCharacter> entry : registry.getAllPrototypes().entrySet()) {
            String key = entry.getKey();
            RPGCharacter proto = entry.getValue();
            System.out.println("* " + capitalize(key) + " -> " + proto.getName() +
                    " (" + proto.getCharacterClass() + ")");
        }

        System.out.print("Enter prototype name (e.g. Warrior, Wizard, Rogue): ");
        String protoKey = readLine(scanner);

        RPGCharacter character = registry.getPrototype(protoKey);
        if (character == null) {
            System.out.println("No such prototype: " + protoKey);
            return null;
        }

        System.out.println("\nYou selected prototype:");
        System.out.println(character);
        System.out.println("\nYou can now modify this character.");

        System.out.print("Enter character name (" + character.getName() + "): ");
        String name = readLine(scanner);
        if (!name.isBlank()) {
            character.setName(name);
        }

        System.out.print("Change class? (y/N): ");
        String changeClass = readLine(scanner);
        if (changeClass.equalsIgnoreCase("y")) {
            System.out.println("Choose new class:");
            for (CharacterClass cc : CharacterClass.values()) {
                System.out.println("- " + cc);
            }
            System.out.print("Class: ");
            CharacterClass characterClass = readClass(scanner);
            character.setCharacterClass(characterClass);
        }

        System.out.print("Enter character story (leave empty to keep): ");
        String story = readLine(scanner);
        if (!story.isBlank()) {
            character.setStory(story);
        }

        // Optionally tweak stats
        if (askYesNo(scanner, "Do you want to modify stats? (y/N): ")) {
            int str = readStatWithDefault(scanner, "Strength", character.getStrength());
            int con = readStatWithDefault(scanner, "Constitution", character.getConstitution());
            int dex = readStatWithDefault(scanner, "Dexterity", character.getDexterity());
            int intel = readStatWithDefault(scanner, "Intelligence", character.getIntelligence());
            int wis = readStatWithDefault(scanner, "Wisdom", character.getWisdom());
            int cha = readStatWithDefault(scanner, "Charisma", character.getCharisma());

            character.setStrength(str);
            character.setConstitution(con);
            character.setDexterity(dex);
            character.setIntelligence(intel);
            character.setWisdom(wis);
            character.setCharisma(cha);
        }

        return character;
    }

    // ---------- Helper methods ----------

    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    private static String readLine(Scanner scanner) {
        return scanner.nextLine();
    }

    private static CharacterClass readClass(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return CharacterClass.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid class. Try again: ");
            }
        }
    }

    private static int readStat(Scanner scanner, String statName) {
        System.out.print(statName + " (3-20): ");
        while (true) {
            int value = readInt(scanner);
            if (value >= 3 && value <= 20) {
                return value;
            }
            System.out.print("Value must be between 3 and 20. Try again: ");
        }
    }

    private static int readStatWithDefault(Scanner scanner, String statName, int defaultValue) {
        System.out.print(statName + " (3-20) [" + defaultValue + "]: ");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return defaultValue;
        }
        while (true) {
            try {
                int value = Integer.parseInt(line);
                if (value >= 3 && value <= 20) {
                    return value;
                }
            } catch (NumberFormatException ignored) {}
            System.out.print("Value must be between 3 and 20. Try again (or empty to keep " 
                             + defaultValue + "): ");
            line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                return defaultValue;
            }
        }
    }

    private static boolean askYesNo(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim().toLowerCase();
        return line.equals("y") || line.equals("yes");
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
