    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package stratinvest;

import java.util.ArrayList;
import static stratinvest.StratInvest.birthRates;

    /**
     *
     * @author harry
     */
public class EarningsStrat extends Strategy {
    
    int period;
    double BUYRATE;
    double SELLRATE;
    
    double maxPrice = 0;
    double threshold = 0.05;
    double buyPrice = 0;
            
    static ArrayList<Double> movingAvg = new ArrayList<>();
    
    static String name = "EarningsStrat";

    /**
     * NOTE!!! YearsAgo must not exceed 55.
     * 
     * @param yearsAgo - how many years ago to backtest
     * @param period - the duration in years of the investment period.
     */
    public EarningsStrat(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
                
        int beginMonth = 1416 - (12 * yearsAgo) - 1;
        
        for (int i = 0; i < beginMonth; i++) {
            if (StratInvest.price[i] > maxPrice) {
                maxPrice = StratInvest.price[i];
            }
        }
        
        movingAvg = new ArrayList<>();
        
        /*
        for (int i = 5; i >= 0; i--)
        {
            System.out.println ("Adding... " + i);
            movingAvg.add(StratInvest.price[beginMonth-i]);
        }
        */
        
    }
    
    //actually momentum
    public void strategy(int month)
    {       
        //BUYS DIPS
        for (int i = 5; i >= 0; i--)
        {
            //System.out.println ("Adding... " + i);
            movingAvg.add(StratInvest.price[month-i]);
        }
        
        movingAvg.add(StratInvest.price[month]);
        
        movingAvg.remove(0);
        double avgPrice = 0;
        
        for (int i = 0; i < 6; i++) {
            avgPrice += movingAvg.get(i);
        }
        avgPrice /= 6;
        
        double avg3 = 0;
        for (int i = 4; i < 6; i++) {
            avg3 += movingAvg.get(i);
        }
        avg3 /= 2;
        
        if (StratInvest.price[month] > maxPrice) {
            maxPrice = StratInvest.price[month];
        }
                       
        //MOMENTUM
        if (Valuation.getPreviousReturn(month) > 1.011)
        {
            if (balance > 0)
                buyShares(balance, month);
        }
        
        if (Valuation.getPreviousReturn(month) < 1.011)
        {
            sellShares(month);            
            if (Valuation.getPreviousReturn(month) < 0.85)
                shortSell(balance, month);
            else if (shorts > 0)
                closeShorts(month);
         else if (shorts > 0)
                closeShorts(month); 
        }
        
        if (avg3 > avgPrice*0.9) {
            if (balance > 0)
                buyShares(balance, month);
        }
                
        if (StratInvest.price[month] <= avgPrice * 1.01) {
            sellShares(month);
        }
        
        
        /*
        //EARNINGS STRATEGY
        double earningsYield = 1 / Valuation.getPE(month);
        double bondYield = Valuation.getRate(month) / 100;
       
        double avgEY = 0;
        double avgBY = 0;
        int months = 12;
        
        for (int i = 0; i < months; i++)
        {
            avgEY += 1 / Valuation.getPE(month-i);
            avgBY += Valuation.getRate(month-i) / 100;
        }
        
        avgEY /= months;
        avgBY /= months;
        
        if (avgEY > avgBY*1.25)
        {
            if (balance > 0)
                buyShares(balance, month);
        }
        
        if (avgEY <= avgBY*1.25)
        {
            sellShares(month);
        }
        
        if (earningsYield > avgEY*1.2)
        {
            buyShares(balance, month);
        }
        
        if (bondYield < avgBY*0.85)
        {
            buyShares(balance, month);
        }
                
        if (Valuation.getShiller(month) > 34)
        {
            sellShares(month);
        }
                
        
        if (balance > 0 && earningsYield > bondYield*1.25)
        {
            buyShares(balance, month);
        }
        
        if (earningsYield <= bondYield*1.25)
        {
            sellShares(month);
        }
        */
        
           
        
        //System.out.println("Earnings yield: " + earningsYield + "\n BondYield: " + bondYield);
    }
}