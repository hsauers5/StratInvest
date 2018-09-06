/*
 * To change this license header,  choose License Headers in Project Properties.
 * To change this template file,  choose Tools | Templates
 * and open the template in the editor.
 */
package stratinvest;

/**
 *
 * @author harry
 */
public class PECD extends Strategy{
    static String name = "PECD";
public PECD(int yearsAgo,  int period)
    {
        super(yearsAgo, period, name);
    }
    
    public void strategy(int month)
    {
            if (Valuation.getPECD(month) < 1.05)
            {
                buyShares(balance*1.5,  month);
                //System.out.println("Shares bought!");
            }
            
            if (Valuation.getPECD(month) > 1.3)
                sellShares(month);
            
        //System.out.println("PECD Max Loss: " + maxLoss*100 + " PECD MaxGain: " + maxGain*100);
    }
    
}
