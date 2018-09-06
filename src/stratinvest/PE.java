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
public class PE extends Strategy
{
    static String name = "P/E";
    
    public PE(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
        //end = period;
        //test(yearsAgo);
        stratName = "P/E";
    }
    
    public void strategy(int month)
{
        if (Valuation.getPE(month) < 28)
        {
            buyShares(balance, month);
            //System.out.println("Shares bought!");
        }

        else if (Valuation.getPE(month) > 29)
        {
            sellShares(month);
        }
        //System.out.println("PECD Max Loss: " + maxLoss*100 + " PECD MaxGain: " + maxGain*100);
    }
    
    
    
}
