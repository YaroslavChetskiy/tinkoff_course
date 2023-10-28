package edu.hw3.task6;

import java.util.PriorityQueue;
import java.util.Queue;

public class StockMarketImpl implements StockMarket {

    private static final Queue<Stock> STOCKS = new PriorityQueue<>((f, s) -> {
        if (f.price() == s.price()) {
            return f.name().compareTo(s.name());
        }
        return -Double.compare(f.price(), s.price());
    });

    @Override
    public void add(Stock stock) {
        STOCKS.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        STOCKS.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return STOCKS.peek();
    }
}
