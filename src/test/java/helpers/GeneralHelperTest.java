package helpers;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeneralHelperTest {

    @Test
    public void checkSumTest1() {
        assertEquals(Long.valueOf(3533307532L), GeneralHelper.fieldChecksum("AnkiDroid"));
    }
}