import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class MainTest {
    // food -> <ingredients>
    //    Map<Set<String>, Set<String>> foods = new HashMap<>();
    // allergen --> <foods>
    //    Map<String, HashMap<String, Set<String>>> allergens = new HashMap<>();

    @Test
    void tst() {
        Map<Set<String>, Set<String>> foods = initFoods();
        getConfirmedAllergenIgredients(foods);
    }

    private Map<String, String> getConfirmedAllergenIgredients(Map<Set<String>, Set<String>> foods) {
        Map<String, Set<String>> allergenIngredients = new HashMap<>();
        for (Map.Entry<Set<String>, Set<String>> foodsEntry : foods.entrySet()) {
            foodsEntry.getValue().forEach(allergen -> addOrMerge(allergenIngredients, allergen, foodsEntry.getKey()));
        }
        Map<String, String> confirmedAllergenIngredients = new HashMap<>();
        while (true) {
            System.out.println("************************");
            System.out.println(allergenIngredients);
            Set<String> confirmedAllergens = allergenIngredients.entrySet().stream()
                    .filter(e -> 1 == e.getValue().size())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            System.out.println(confirmedAllergens);
            if (confirmedAllergens.isEmpty()) {
                break;
            }
            for (String confirmedAllergen : confirmedAllergens) {
                String confirmedFood = allergenIngredients.get(confirmedAllergen).iterator().next();
                confirmedAllergenIngredients.put(confirmedAllergen, confirmedFood);
                allergenIngredients.remove(confirmedAllergen);
                for ( Set<String> ingredients : allergenIngredients.values()) {
                    ingredients.remove(confirmedFood);
                }
            }
            System.out.println(confirmedAllergenIngredients);
        }
        System.out.println("======================");
        System.out.println(confirmedAllergenIngredients);
        System.out.println("----------------------");
        System.out.println(allergenIngredients);
        return confirmedAllergenIngredients;
    }

    private void addOrMerge(Map<String, Set<String>> map, String key, Set<String> value) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>(value));
        } else {
            // map.get(key).addAll(value);
            map.get(key).retainAll(value);
        }
    }

    private Map<Set<String>, Set<String>> initFoods() {
//        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
//        trh fvjkl sbzzf mxmxvkd (contains dairy)
//        sqjhc fvjkl (contains soy)
//        sqjhc mxmxvkd sbzzf (contains fish)
        Map<Set<String>, Set<String>> foods = new HashMap<>();
        foods.put(Set.of("mxmxvkd", "kfcds", "sqjhc", "nhms"), Set.of("dairy", "fish"));
        foods.put(Set.of("trh", "fvjkl", "sbzzf", "mxmxvkd"), Set.of("dairy"));
        foods.put(Set.of("sqjhc", "fvjkl"), Set.of("soy"));
        foods.put(Set.of("sqjhc", "mxmxvkd", "sbzzf"), Set.of("fish"));
        return foods;

    }

}
