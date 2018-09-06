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
public class PECDMtm extends Strategy{
    
    static String name = "PECDMtm";
    
    public PECDMtm(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
        //end = (period * 12) + (1416 - (12*yearsAgo));
        //test(yearsAgo);
    }
    
    public void strategy(int month)
    {
        if (Valuation.getPECD(month) > 1 && Valuation.getPECD(month-6) < 1)
        {
            buyShares(balance, month);
            //System.out.println("Shares bought!");
        }
    }
}
