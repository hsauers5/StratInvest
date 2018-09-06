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
public class BirthStrat extends Strategy {
    
    int period;
    double BUYRATE;
    double SELLRATE;
    
    static String name = "BirthStrat";

    /**
     * NOTE!!! YearsAgo must not exceed 55.
     * 
     * @param yearsAgo - how many years ago to backtest
     * @param period - the duration in years of the investment period.
     */
    public BirthStrat(int yearsAgo, int period, double buyRate, double sellRate)
    {
        super(yearsAgo, period, name);
        //nd = period;
        //this.period = period;
        BUYRATE = buyRate;
        SELLRATE = sellRate;
        //test(yearsAgo, period);
    }
    
    public void strategy(int month)
    {                          
        if (Valuation.getPE(month) < 28 || Valuation.getYieldChange(month) > 1.1)// (Valuation.getYieldChange(month) > 0.6 ))//&& Valuation.getYield(month) > 2.0))
        {
            if (balance > 0)
                buyShares(balance, month);
            
        } else if (Valuation.getPE(month) > 29)
        {
            sellShares(month);
        }
        
        if (Valuation.getPECD(month) < 0.75 && Valuation.getPE(month) < 29.5)
        {
            buyShares(balance, month);
        }
                
        if (Valuation.getRateChange(month) > 1.2) // && (!(Valuation.getYieldChange(month) > 1.2)) || Valuation.getRateChange(month) > 1.5)
        {
            sellShares(month);
        }
                        
        if (Valuation.getPECD(month) > 1.29 && Valuation.getYieldChange(month) < 2.0)
        {
            sellShares(month);            
        } else if (shorts != 0) {
            closeShorts(month);
        }
        
        if (Valuation.getShiller(month) > 34.75)
        {
            sellShares(month);
        }
                    
                    
                
            

                //finish(month);
                /*
                if (this.getValue(month) > maxReturn)
                {
                    maxReturn = this.getValue(month);
                    StratInvest.buyBirth = BUYRATE;
                    StratInvest.sellBirth = SELLRATE;
                }
                */
                /*
                System.out.println("Gains: " + gain.toString());
                System.out.println("Losses: " + loss.toString());

                double maxLoss = 1000;
                for (double d : loss)
                    if (d < maxLoss)
                        maxLoss = d;
                double maxGain = 0;
                for (double d : gain)
                    if (d > maxGain)
                        maxGain = d;

                for (double d: loss)
                    avgLoss += d;    

                avgLoss = avgLoss / loss.size();
                System.out.println("BirthRate AvgLoss: " + avgLoss*100);
                if (loss.size() == 0)
                    avgLoss = 0;


                */
                //end = this.period;
                
//System.out.println("PECD Max Loss: " + maxLoss*100 + " PECD MaxGain: " + maxGain*100);
            
    }
}