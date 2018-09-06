/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stratinvest;

/**
 *
 * @author harry
 */
public class Valuation {
    
    /**
     * 
     * @param month - months from January 1st, 1900
     * @return instantaneous p/e over Shiller p/e
     */
    public static double getPECD(int month)
    {
        return StratInvest.pe[month] / (StratInvest.pe[month-12]);
    }
    
    /**
     * 
     * @return historical average PE/Shiller Convergence-Divergence
     */
    public static double getHistoricalPECD()
    {
        double sum = 0;
        for (int i = 0; i < StratInvest.price.length; i++)
        {
            sum += getPECD(i);
        }
        return sum / 1416;
    }

    static double getPE(int month) 
    {
        return StratInvest.pe[month];
        
    }
    
    static double getRate(int month)
    {
        return StratInvest.bondRates[month/12];
    }
    
    /**
     * Computes rate change relative to 10-year trailing average.
     * @param month
     * @return rate change as current rate / 10yr avg
     */
    static double getRateChange(int month)
    {
        double avgRate5 = 0;
        double count = 0;
        
        for (int i = 1; i < 11; i++)
        {
            if (month/12-i >= 0)
            {
                avgRate5 += StratInvest.bondRates[month/12-i];
                count++;
            }
        }
        
        avgRate5 /= count;
        
        if (month >= 12)
            return StratInvest.bondRates[month/12] / avgRate5;
        else
            return 1;
        
    }
    
    static double getYield(int month)
    {
        return StratInvest.yields[month];
    }
    
    static double getYieldChange(int month)
    {
        int timeline = 120; //trailing average in months ago
        
        double avg = 0.0;
        if (month >= timeline)
        {
            for (int i = month-timeline; i < month; i++)
            {
                //System.out.println(getYield(i));
                avg  = avg + getYield(i);
            }
            
            avg  = avg/timeline;
        }
        else
            avg = getYield(month);
                
        //System.out.println(getYield(month) + " " + getYield(month) / avg);
        return getYield(month) / avg;
        
    }
    
    static double getPreviousReturn(int month)
    {
        if (month >= 12)
            return StratInvest.price[month] / StratInvest.price[month-1];
        else
            return 1;
    }
    
    static double getShiller(int month)
    {
        return StratInvest.shiller[month];
    }
    
}
