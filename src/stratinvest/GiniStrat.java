    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package stratinvest;

import static stratinvest.EarningsStrat.movingAvg;
import static stratinvest.StratInvest.birthRates;

    /**
     *
     * @author harry
     */
public class GiniStrat extends Strategy {
    
    int period;
    
    //Gini coefficients from 1960 to 2013.
    static double[] gini = {38.00,38.80,37.80,37.50,37.60,37.40,37.10,37.40,36.30,36.50,36.90,37.10,37.40,37.20,36.60,37.10,37.10,37.40,37.50,37.80,37.90,38.50,39.60,40.10,40.10,40.60,40.90,41.10,41.40,42.00,41.80,41.80,42.50,43.60,43.60,43.30,43.70,44.00,43.90,44.10,44.20,44.60,44.30,44.50,44.70,45.00,45.20,44.40,45.00,45.60,45.60,46.30,46.30,45.90};
    
    static String name = "GiniStrat";

    /**
     * NOTE!!! Can only be 1960-2013. ENDYEARSAGO = 4?
     * 
     * @param yearsAgo - how many years ago to backtest, 55
     * @param period - the duration in years of the investment period. 
     */
    public GiniStrat(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
    }
    
    public void strategy(int month)
    {                        
        //if gini, if change in gini, buy/sell signals
        int year = (month+1)/12 + 1900;
        int yearIndex = year - 1963;
        //System.out.println(month + " " + yearIndex);
        //System.out.println(yearIndex);
        double currentGini = gini[yearIndex];
        
        double changeInGiniOneYr = 1.0;
        
        double avgGini5 = 0;
        if (yearIndex >= 5) {
            for (int i = yearIndex-5; i <= yearIndex-1; i++) {
                avgGini5 += gini[i];
            }
            avgGini5 /= 5;
        } else {
            avgGini5 = currentGini;
        }
        
        double pastGini20 = 1;
        if (yearIndex >= 20) {
            pastGini20 = gini[yearIndex-20];
        }
        
        
        if (yearIndex > 11) {
            changeInGiniOneYr = currentGini / gini[yearIndex-12];
        }
        
        //System.out.println("Current, Change1, Avg" + currentGini +" " + changeInGiniOneYr +" " + avgGini5);
        
        buyShares(balance, month);
        
        if (changeInGiniOneYr < 1.035) {
            sellShares(month);
        }
        
        //System.out.println(currentGini / avgGini5);
        
        //buyShares(balance, month);
        if (currentGini / avgGini5 < 1.01) {
            sellShares(month);
        }
        
    }
}