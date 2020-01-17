package ru.lokincompany.lokengine.tools.opensimplexnoise;

public class DefaultStringToLongTransformer {
    public static StringToLongTransformer get() {
        return text -> {
            long longSeed = 0;

            char[] symbols = text.toCharArray();

            for (int i = 1; i <= symbols.length; i++) {
                longSeed += Math.pow((int) symbols[i - 1], i);
            }

            return longSeed;
        };
    }
}
