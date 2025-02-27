package com.tqs.lab2_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
// @MockitoSettings(strictness = Strictness.LENIENT) // Uncomment this line to use lenient mock behavior, can be particularly useful if we are defining an expectation that is not used in the test
public class StocksPortfolioTest {
    @Mock
    private IStockmarketService stockMarketServiceMock;

    @InjectMocks
    private StocksPortfolio stocksPortfolio;

    @BeforeEach
    public void setUp() {
        stocksPortfolio = new StocksPortfolio(stockMarketServiceMock);
    }

    @DisplayName("Test total value")
    @Test
    public void getTotalValue() {
        // 1. Prepare a mock to substitute the remote service (@Mock annotation) (if not using @ExtendWith(MockitoExtension.class))
        // IStockmarketService stockMarketServiceMock = mock(IStockmarketService.class);

        // 2. Prepare an instance of the subject under test (SuT) and use the mock to set the (remote) service instance. (if not using @InjectMocks)
        // StocksPortfolio stocksPortfolio = new StocksPortfolio(stockMarketServiceMock);

        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(stockMarketServiceMock.lookUpPrice("EBAY")).thenReturn(3.0);
        when(stockMarketServiceMock.lookUpPrice("MSFT")).thenReturn(2.0);
        // when(stockMarketServiceMock.lookUpPrice("NOTUSED")).thenReturn(1.5);

        // 4. Execute the test (use the service in the SuT)
        stocksPortfolio.addStock(new Stock("EBAY", 2));
        stocksPortfolio.addStock(new Stock("MSFT", 4));
        // stocksPortfolio.addStock(new Stock("UAVR", 0)); // No problem mock will return 0.0
        double result = stocksPortfolio.totalValue();

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(14.0, result); // junit style

        assertThat( result, is(14.0) ); // hamcrest style

        verify( stockMarketServiceMock, times(2)).lookUpPrice( anyString() ); // Change to number 2 to other if error occurs
    }

    @DisplayName("Test most valuable stocks")
    @Test
    public void getMostValuableStocks() {
        when(stockMarketServiceMock.lookUpPrice("EBAY")).thenReturn(3.0);
        when(stockMarketServiceMock.lookUpPrice("MSFT")).thenReturn(2.0);

        stocksPortfolio.addStock(new Stock("EBAY", 2));
        stocksPortfolio.addStock(new Stock("MSFT", 4));
        List<Stock> result = stocksPortfolio.mostValuableStocks(2);

        assertEquals(2, result.size());
        assertEquals("MSFT", result.get(0).getLabel());
        assertEquals("EBAY", result.get(1).getLabel());
    }

    // Two tests bellow did not pass before the modifications signaled in the comments in the StocksPortfolio.java file

    @DisplayName("Test most valuable stocks with negative topN")
    @Test
    public void getMostValuableStocksNegativeTopN() {
        stocksPortfolio.addStock(new Stock("EBAY", 2));
        stocksPortfolio.addStock(new Stock("MSFT", 4));
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            stocksPortfolio.mostValuableStocks(-1);
        });
    }

    @DisplayName("Test most valuable stocks with topN greater than the number of stocks in the portfolio")
    @Test
    public void getMostValuableStocksTopNGreaterThanPortfolio() {
        stocksPortfolio.addStock(new Stock("EBAY", 2));
        stocksPortfolio.addStock(new Stock("MSFT", 4));
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            stocksPortfolio.mostValuableStocks(3);
        });
    }
}