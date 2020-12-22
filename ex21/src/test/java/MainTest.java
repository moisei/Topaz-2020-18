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
//        Map<Set<String>, Set<String>> foods = initFoodsExample();
        Map<Set<String>, Set<String>> foods = initFoodsInp();
        Map<String, String> confirmedAllergenIngredients = getConfirmedAllergenIngredients(foods);
        System.out.println(foods);
        System.out.println(confirmedAllergenIngredients);
        foods.keySet().forEach(k -> k.removeAll(confirmedAllergenIngredients.values()));
        System.out.println(foods.keySet());
        long sum = foods.keySet().stream().mapToLong(Set::size).sum();
        System.out.println(sum);

        String sortedValues = confirmedAllergenIngredients.keySet().stream().sorted().map(confirmedAllergenIngredients::get).collect(Collectors.joining(","));
        System.out.println(sortedValues);

    }

    private Map<String, String> getConfirmedAllergenIngredients(Map<Set<String>, Set<String>> foods) {
        Map<String, Set<String>> allergenIngredients = new HashMap<>();
        for (Map.Entry<Set<String>, Set<String>> foodsEntry : foods.entrySet()) {
            foodsEntry.getValue().forEach(allergen -> addOrMerge(allergenIngredients, allergen, foodsEntry.getKey()));
        }
        Map<String, String> confirmedAllergenIngredients = new HashMap<>();
        while (true) {
            Set<String> confirmedAllergens = allergenIngredients.entrySet().stream()
                    .filter(e -> 1 == e.getValue().size())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
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
        }
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

    private Map<Set<String>, Set<String>> initFoodsExample() {
//        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
//        trh fvjkl sbzzf mxmxvkd (contains dairy)
//        sqjhc fvjkl (contains soy)
//        sqjhc mxmxvkd sbzzf (contains fish)
        Map<Set<String>, Set<String>> foods = new HashMap<>();
        foods.put(new HashSet<>(Set.of("mxmxvkd", "kfcds", "sqjhc", "nhms")), Set.of("dairy", "fish"));
        foods.put(new HashSet<>(Set.of("trh", "fvjkl", "sbzzf", "mxmxvkd")), Set.of("dairy"));
        foods.put(new HashSet<>(Set.of("sqjhc", "fvjkl")), Set.of("soy"));
        foods.put(new HashSet<>(Set.of("sqjhc", "mxmxvkd", "sbzzf")), Set.of("fish"));
        return foods;

    }

    @Test
    void name() {
        System.out.println(initFoodsInp());
    }

    // cqvc vmkt sbvbzcg (contains fish, soy, nuts)
    private Map<Set<String>, Set<String>> initFoodsInp() {
        Map<Set<String>, Set<String>> foods = new HashMap<>();
        for (String line : Inp.INP.split("\n")) {
            String[] tokens = line.split("\\(");
            Set<String> ingredients = new HashSet<>(Arrays.asList(tokens[0].trim().split(" ")));
            String[] t1 = tokens[1].replace("contains", "").replace(" ", "").replace(")", "").split(",");
            Set<String> allergens = new HashSet<>(Arrays.asList(t1));
            foods.put(ingredients, allergens);
        }
        return foods;

    }

}
