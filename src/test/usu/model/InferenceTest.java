package test.usu.model;

import main.usu.model.Inference;
import static org.junit.Assert.*;
import org.junit.Test;

public class InferenceTest {

    @Test
    public void testInferencesAreEqualOnlyWhenPositionAndExclustionAreSame() {
        assertEquals(new Inference(1, 2, "A"), new Inference(1, 2, "A"));
    }

    @Test
    public void testInferencesAreNotEqualWhenEitherPositionOrExclustionMismatch() {
        assertNotEquals(new Inference(1, 2, "A"), new Inference(2, 2, "A"));
        assertNotEquals(new Inference(1, 2, "A"), new Inference(1, 1, "A"));
        assertNotEquals(new Inference(1, 2, "A"), new Inference(1, 2, "B"));
        assertNotEquals(new Inference(1, 2, "A"), new Inference(1, 2, "a"));
    }
}
