package tqs.euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.euromillions.CuponEuromillions;
import tqs.euromillions.Dip;
import tqs.euromillions.EuromillionsDraw;

public class EuromillionsDrawTest {

    private CuponEuromillions sampleCoupon;

    @BeforeEach
    public void setUp()  {
        sampleCoupon = new CuponEuromillions();
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(new Dip(new int[]{1, 2, 3, 48, 49}, new int[]{1, 9}));
    }


    @DisplayName("reports correct matches in a coupon")
    @Test
    public void testCompareBetWithDrawToGetResults() {
        Dip winningDip, matchesFound;

        // test for full match, using the 3rd dip in the coupon as the Draw results
        winningDip = sampleCoupon.getDipByIndex(2);
        EuromillionsDraw testDraw = new EuromillionsDraw(winningDip);
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);

        assertEquals(winningDip, matchesFound, "expected the bet and the matches found to be equal");

        // test for no matches at all
        testDraw = new EuromillionsDraw(new Dip(new int[]{9, 10, 11, 12, 13}, new int[]{2, 3}));
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);
        // compare empty with the matches found
        assertEquals( new Dip(), matchesFound);
    }

    @DisplayName("reports correct number of dips in a coupon")
    @Test
    public void testCountDips() {
        assertEquals(3, sampleCoupon.countDips(), "expected 3 dips in the coupon");
    }

    @DisplayName("reports correct format of a coupon")
    @Test
    public void testCorrectFormatCoupon() {
        // Criamos um cupom com dips de valores fixos
        CuponEuromillions coupon = new CuponEuromillions();
        Dip dip1 = new Dip(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2});
        Dip dip2 = new Dip(new int[]{6, 7, 8, 9, 10}, new int[]{3, 4});

        coupon.appendDip(dip1);
        coupon.appendDip(dip2);

        // Construímos a string esperada conforme o formato definido
        String expected = String.format("Dip #1:%s\nDip #2:%s\n", dip1.format(), dip2.format());

        // Verificamos se o método format() retorna o resultado esperado
        assertEquals(expected, coupon.format(), "Coupon format did not match expected string");
    }

    @DisplayName("reports correct matches in a dip")
    @Test
    public void testGetDrawResults() {
        Dip winningDip = sampleCoupon.getDipByIndex(2);
        EuromillionsDraw testDraw = new EuromillionsDraw(winningDip);
        assertEquals(winningDip, testDraw.getDrawResults(), "expected the draw results to be the same as the winning dip");
    }

    @DisplayName("reports correct matches in a coupon")
    @Test
    public void testGenerateRandomDraw() {
        EuromillionsDraw testDraw = EuromillionsDraw.generateRandomDraw();
        assertNotNull(testDraw, "expected a draw to be generated");
    }

}
