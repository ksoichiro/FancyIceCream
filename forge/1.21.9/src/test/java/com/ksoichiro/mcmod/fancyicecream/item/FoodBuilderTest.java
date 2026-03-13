package com.ksoichiro.mcmod.fancyicecream.item;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodBuilderTest {

    /**
     * Creates a FoodBuilder instance using Mockito with CALLS_REAL_METHODS
     * to avoid triggering Minecraft class initialization in the constructor.
     * The effects list is initialized via reflection.
     */
    private FoodBuilder createTestBuilder() throws Exception {
        FoodBuilder builder = mock(FoodBuilder.class, CALLS_REAL_METHODS);
        Field effectsField = FoodBuilder.class.getDeclaredField("effects");
        effectsField.setAccessible(true);
        effectsField.set(builder, new ArrayList<>());
        return builder;
    }

    @SuppressWarnings("unchecked")
    private List<Object> getEffectsList(FoodBuilder builder) throws Exception {
        Field effectsField = FoodBuilder.class.getDeclaredField("effects");
        effectsField.setAccessible(true);
        return (List<Object>) effectsField.get(builder);
    }

    @Test
    void hasEffects_returnsFalseWhenNoEffectsAdded() throws Exception {
        FoodBuilder builder = createTestBuilder();
        assertFalse(builder.hasEffects());
    }

    @Test
    void hasEffects_returnsTrueWhenEffectsPresent() throws Exception {
        FoodBuilder builder = createTestBuilder();
        // Add a dummy entry to the effects list via reflection
        // (bypasses the effect() method which requires Minecraft's MobEffect)
        getEffectsList(builder).add(new Object());
        assertTrue(builder.hasEffects());
    }

    @Test
    void hasEffects_returnsTrueWithMultipleEffects() throws Exception {
        FoodBuilder builder = createTestBuilder();
        List<Object> effects = getEffectsList(builder);
        effects.add(new Object());
        effects.add(new Object());
        assertTrue(builder.hasEffects());
    }
}
