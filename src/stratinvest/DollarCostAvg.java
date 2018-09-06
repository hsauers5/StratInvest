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
public class DollarCostAvg extends Strategy{
    
    static String name = "DollarCostAvg";
        
    public DollarCostAvg(int yearsAgo, int period)
    {
        super(yearsAgo, period, name);
        //end = (period * 12) + (1416 - (12*yearsAgo));
        //test(yearsAgo);
    } 
    
    public void strategy(int month) 
    {
        //int month = 1416 - (12*yearsAgo); //get the month index
        
        double fraction = balance / 2;
        
        buyShares(fraction, month);
        

    }
    
}
