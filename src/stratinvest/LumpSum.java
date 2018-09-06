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


public class LumpSum extends Strategy
{
    static String name = "LumpSum";
    
    public LumpSum(int yearsAgo, int period)
        {
            super(yearsAgo, period, name);
            //end = period;
            //test(yearsAgo);
            stratName = "LumpSum";
        }

        public void strategy(int month)
        {
            if (balance > 0)
                buyShares(balance, month);
        }
    
}
