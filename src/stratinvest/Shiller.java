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
public class Shiller extends Strategy{
    
static String name = "Shiller";
    
public Shiller(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
        //end = (period * 12) + (1416 - (12*yearsAgo));
        //test(yearsAgo, period);
    }

    public void strategy(int month) 
    {

        if (Valuation.getShiller(month) < 28)
            {
                buyShares(balance, month);
                //System.out.println("Shares bought!");
            }
        
        
    }
    
    
    
}
