package com.tqs.lab2_1;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private IStockmarketService stockmarket;
    private List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double total = 0;
        for (Stock stock : stocks) {
            total += stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return total;
    }

    /**
     * @param topN the number of most valuable stocks to return
     * @return a list with the topN most valuable stocks in the portfolio
     */
    // AI missed when topN is 0 or negative and when topN is greater than the number of stocks in the portfolio
    public List<Stock> mostValuableStocks(int topN) {
        // Modifications to pass all possible test cases
        if (topN <= 0 || topN > stocks.size()) {
            throw new IllegalArgumentException("Invalid topN value");
        }
        // End of modifications
        List<Stock> mostValuable = new ArrayList<Stock>();
        List<Stock> copy = new ArrayList<Stock>(stocks);
        for (int i = 0; i < topN; i++) {
            Stock mostValuableStock = copy.get(0);
            for (Stock stock : copy) {
                if (stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity() > stockmarket.lookUpPrice(mostValuableStock.getLabel()) * mostValuableStock.getQuantity()) {
                    mostValuableStock = stock;
                }
            }
            mostValuable.add(mostValuableStock);
            copy.remove(mostValuableStock);
        }
        return mostValuable;
    }
}
