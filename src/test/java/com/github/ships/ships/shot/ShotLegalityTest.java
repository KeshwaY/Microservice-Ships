package com.github.ships.ships.shot;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.*;

public class ShotLegalityTest {

    @Test
    public void testObtainShotLegality() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(ShotLegality.LEGAL, ShotLegality.obtainShotLegality(true));
        softAssert.assertEquals(ShotLegality.ILLEGAL, ShotLegality.obtainShotLegality(false));
        softAssert.assertAll();
    }
}
