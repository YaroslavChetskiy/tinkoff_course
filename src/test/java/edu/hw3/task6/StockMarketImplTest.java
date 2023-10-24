package edu.hw3.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StockMarketImplTest {

    @Test
    @DisplayName("Тестирование биржи с акциями, имеющими разную цену")
    void testStockMarketWithNoEqualPrices() {
        StockMarket stockMarket = new StockMarketImpl();
        var tinkoff = new Stock("TCSG", 3476.5);
        var lukoil = new Stock("LKOH", 7442.5);
        var sber = new Stock("SBER", 270.35);
        var rostelecom = new Stock("RTKM", 77.5);
        stockMarket.add(tinkoff);
        stockMarket.add(lukoil);
        stockMarket.add(sber);
        stockMarket.add(rostelecom);

        assertThat(stockMarket.mostValuableStock()).isEqualTo(lukoil);
        stockMarket.remove(stockMarket.mostValuableStock());

        assertThat(stockMarket.mostValuableStock()).isEqualTo(tinkoff);
        stockMarket.remove(stockMarket.mostValuableStock());

        assertThat(stockMarket.mostValuableStock()).isEqualTo(sber);
        stockMarket.remove(stockMarket.mostValuableStock());

        assertThat(stockMarket.mostValuableStock()).isEqualTo(rostelecom);
    }

    @Test
    @DisplayName("Тестирование биржи с акциями с равными ценами")
    void testStockMarketWithEqualPrices() {
        StockMarket stockMarket = new StockMarketImpl();
        var firstStock = new Stock("ABCD", 123.4);
        var secondStock = new Stock("TEST", 123.4);
        var thirdStock = new Stock("DUMMY", 123.4);
        stockMarket.add(firstStock);
        stockMarket.add(secondStock);
        stockMarket.add(thirdStock);

        assertThat(stockMarket.mostValuableStock()).isEqualTo(firstStock);
        stockMarket.remove(stockMarket.mostValuableStock());

        assertThat(stockMarket.mostValuableStock()).isEqualTo(thirdStock);
        stockMarket.remove(stockMarket.mostValuableStock());

        assertThat(stockMarket.mostValuableStock()).isEqualTo(secondStock);
    }

}
