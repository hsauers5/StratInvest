/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stratinvest;

import java.util.ArrayList;

/**
 *
 * @author harry
 */
public abstract class Strategy {
    
    public String name;
    public double principal = 10000;
    public double balance = 10000;
    public double roi = 0;
    public double shares = 0;
    public double geoMean = 0;
    double avgLoss = 0;
    
    public double shorts = 0;
    public double shortPrice = 0;
    
    public double debt = 0;
    public double leverageRate = 0.08;
    
    public double bondRate = 0;
    
    int end = 0;
    
    String stratName;
    
    //public ArrayList<Double> annualReturns = new ArrayList<>();
    //public ArrayList<Double> loss = new ArrayList<Double>();
    
    public Strategy(int yearsAgo, int period, String name)
    {
        stratName = name; //holds strategy name
        int month = 1416 - (12 * yearsAgo) - 1; //get the month index
        end = period * 12 + month; //ends period number of years after yearsAgo.
        test(yearsAgo, month);
    }
    
    public abstract void strategy(int month);
    
    /**
     * 
     * @param yearsAgo - the number of years ago to test strategy, for one year.
     * @param month - the month to test
     */
    
    public boolean pendingSell = false;
    
    public final void test(int yearsAgo, int month) 
    {
        //end = (end * 12) + month;

        for (int i = month; i < end; i++) 
        {
            
            strategy(i);
            
            //buys bonds
            if (balance > 0 && shorts == 0)
            {
                balance = balance * (1 + (StratInvest.bondRates[i / 12] / 100)/12);
            }
            
            if (balance < 0)
                balance *= (1 + leverageRate/12);
            
            if (shares > 0 && shorts == 0)
            {
                balance += (shares * StratInvest.price[i] * (StratInvest.yields[month] / 100)/12);
            }
            
            if (shorts > 0)
            {
                balance -= (shorts * StratInvest.price[i] * (StratInvest.yields[month] / 100)/12);
            }
                        
            if (i % 12 == 0 && i != 0) {
                
                if (getValue(i) < getValue (i-12) )
                {
                    avgLoss += (getValue(i) / getValue(i - 12) - 1);
                }
                    
                               
                /*
                if (i >= 12 && getValue(i) >= getValue(i - 12)) {
                    annualReturns.add(getValue(i) / getValue(i - 12) - 1);
                } else if (i >= 12 && getValue(i) < getValue(i - 12)) {
                    annualReturns.add(getValue(i) / getValue(i - 12) - 1);
                }
                */
            }

            /*
            if (i > 0)
                System.out.println(getValue(i) / getValue(i-1));
            else
                System.out.println(getValue(i) / 10000);
            */
        }
        
        avgLoss /= ((end-month)/12);
        
        roi = (getValue(end) / 10000);
        //System.out.println("Total returns for " + stratName + ": " + roi);
        System.out.println(roi);
        
        //finish(end);

    }
    
    public void buyShares(double amount, int monthIndex)
    {
        //closeShorts(monthIndex);
        
        //double leverage = 0;
        
        balance -= amount;
        
        double price = StratInvest.price[monthIndex];
        
        double stake = amount / price;
        
        shares += stake;
        
    }
    
    public void sellShares(int monthIndex)
    {
        double price = StratInvest.price[monthIndex];
        
        double rtn = price * shares;
        
        shares = 0;
        
        balance += rtn;
                
        //buyBonds(monthIndex);
                
    }
    
    /*
    public void buyBonds(int monthIndex)
    {
        int year = monthIndex / 12;
        double bondRate = StratInvest.bondRates[year];
    }
    */

    public void shortSell(double amount, int monthIndex)
    {
        //balance = balance - amount;
        
        sellShares(monthIndex);
        
        double price = StratInvest.price[monthIndex];
        
        shortPrice = price;
        
        double stake = amount / price;
        
        shorts = stake;
    }
    
    public void closeShorts(int monthIndex)
    {
        double price = StratInvest.price[monthIndex];
        
        double rtn = (shortPrice / price) * (balance);
        
        balance = 0;
        
        shorts = 0;
        
        balance += rtn;
    }
    
    public double getValue(int monthIndex)
    {
        double price = StratInvest.price[monthIndex];
        double rtn = price * shares;
        rtn += balance;
        return rtn;
    }
    
    public void payTaxes()
    {
        double taxable = balance - principal;
        if (taxable > 0)
        {
            balance -= 0.2*taxable;
        }
    }
    
    public void finish(int month)
    {
        //end, sell all shares.
        sellShares(month);
        
        roi = (balance-debt) / principal;
        //System.out.println(roi);
        
        //System.out.println("Annualized return: " + (geoMean = Quant.getGeoMean(month, end, 10000, balance)-1));

    }
}
