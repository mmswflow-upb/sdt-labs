package Ex_2;

import java.util.HashMap;
import java.util.Map;

public class CharacterPrototypeRegistry {

    private final Map<String, RPGCharacter> prototypes = new HashMap<>();

    public CharacterPrototypeRegistry() {
        // Predefined characters
        addPrototype("Warrior", new RPGCharacter(
                "Generic Warrior",
                CharacterClass.WARRIOR,
                "A seasoned fighter from the northern kingdoms.",
                18, // STR
                16, // CON
                12, // DEX
                8,  // INT
                10, // WIS
                10  // CHA
        ));

        addPrototype("Wizard", new RPGCharacter(
                "Generic Wizard",
                CharacterClass.WIZARD,
                "A scholar of the arcane arts.",
                8,
                12,
                12,
                18,
                14,
                10
        ));

        addPrototype("Rogue", new RPGCharacter(
                "Generic Rogue",
                CharacterClass.ROGUE,
                "A sneaky thief with a quick blade and quicker wit.",
                12,
                10,
                18,
                12,
                12,
                14
        ));
    }

    public void addPrototype(String key, RPGCharacter character) {
        prototypes.put(key.toLowerCase(), character);
    }

    public RPGCharacter getPrototype(String key) {
        RPGCharacter proto = prototypes.get(key.toLowerCase());
        if (proto == null) {
            return null;
        }
        // PROTOTYPE PATTERN: return a CLONE, not the original
        return proto.clone();
    }

    public Map<String, RPGCharacter> getAllPrototypes() {
        return prototypes;
    }
}
