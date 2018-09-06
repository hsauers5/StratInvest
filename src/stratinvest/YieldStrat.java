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
public class YieldStrat extends Strategy
{
    static String name = "P/E";
    
    public YieldStrat(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
        //end = period;
        //test(yearsAgo);
        stratName = "DivYield";
    }
    
    /**
     *
     * @param month
     */
    public void strategy(int month)
{
        if (Valuation.getYieldChange(month) > 0.7 && Valuation.getYield(month) > 1.8) //1.8 is optimal
        {
            buyShares(balance, month);
            //System.out.println("Shares bought!"); 109.1961
        }
        
        if (Valuation.getYieldChange(month) < 0.6) // || Valuation.getYield(month) < 1.65)
        {
            sellShares(month);
        }

        //System.out.println("PECD Max Loss: " + maxLoss*100 + " PECD MaxGain: " + maxGain*100);
    }
    
    
    
}
