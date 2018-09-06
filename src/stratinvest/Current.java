/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stratinvest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author harry
 */
public class Current {
    
    static String recommendation = "...";
    
    public static void getCurrent() throws IOException
    {
        double currentPE = Double.valueOf(getCurrentPE());
        double currentShiller = Double.valueOf(getCurrentShiller());
        double currentRate = Double.valueOf(getCurrentRate());
        
        double oldPE = Double.valueOf(getOldPE()); //PE of one year ago by month
        double avgRate = Double.valueOf(getAverageRate()); //ten year trailing average interest rate
        
        double PECD = currentPE / oldPE;
        double rateChange = currentRate / avgRate;
                
        String indexPrice = getIndexPrice();
        
        double price = Double.valueOf(indexPrice.replaceAll(",", ""));
        
        //System.out.println(PECD + " " + rateChange);
        
        if (currentPE < 28)
        {
            buyShares();
        }

        else if (currentPE > 29)
        {
            sellShares();
        }
        
        if (PECD < 0.75 && currentPE < 29.5)
        {
            buyShares();
        }
                
        if (rateChange > 1.2)
        {
            sellShares();
        }
        
        if (currentShiller > 20)
            if (PECD > 1.31)
            {
                shortSell();
            } else
                closeShorts();
        
        if (PECD > 1.29)
        {
            sellShares();
        }
        
        if (currentShiller > 34.75)
        {
            sellShares();
        }

        System.out.println("Price: $" + indexPrice + ". " + recommendation);
        //System.out.println(getRecommendation());
    }
    
    public static String getRecommendation()
    {
        if (recommendation.equals("Buy Shares."))
        {
            return "BUY";
        } else if (recommendation.equals("Sell Shares, Move into Treasuries."))
        {
            return "SELL";
        } else if (recommendation.equals("Sell Shares, Go Short."))
        {
            return "SHORT";
        } else 
            return "CLOSE";
    }
    
    public static void buyShares()
    {
        recommendation = "Buy Shares.";
    }
    public static void sellShares()
    {
        if (!recommendation.equals("Sell Shares, Go Short."))
            recommendation = "Sell Shares, Move into Treasuries.";
    }
    public static void shortSell()
    {
        recommendation = "Sell Shares, Go Short.";
    }
    public static void closeShorts()
    {
        if (recommendation.equals("Buy Shares."))
            recommendation = "Buy Shares.";
        else
            recommendation = "Close Positions, Move into Treasuries.";
    }
    
    public static String getCurrentPE() throws FileNotFoundException, IOException
    {
        String page = "http://www.multpl.com/table?f=m"; //"http://www.wsj.com/mdc/public/page/2_3021-peyield.html";
        
        StringBuilder sb = new StringBuilder(1000);
        
        try {
            URLConnection connection = new URL(page).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException e) {
            // handle exception
        }

        String content = sb.toString();
        
        //System.out.println("test" + content);
        
        String sp = "<tr class=\"even\">        <td class=\"left\">"; //"S&amp;P 500"; //"S&P 500";
        String peCurrent = "";
        
        for (int i = 0; i < content.length()-47; i++)
        {
            if (content.substring(i, i+47).contains(sp))
            {
                System.out.println("Found!");
                peCurrent = content.substring(i+47, i+200);
                break;
            }
        }
        
        //System.out.println(peCurrent);
        
        String beginning = "<td class=\"right\">";  //"<td style=\"font-weight:bold;\" class=\"num\">";
        String peNew = "";
        
        for (int i = 0; i < peCurrent.length()-beginning.length(); i++)
        {
            if (peCurrent.substring(i, i+beginning.length()).contains(beginning))
            {
                //System.out.println("Almost there...");
                peNew = peCurrent.substring(i+beginning.length(), i+beginning.length()+5);
                break;
            }
        }
        
        System.out.println(peNew);
        return peNew;
    }
    
    public static String getCurrentShiller() throws FileNotFoundException, IOException
    {
        String page = "http://www.multpl.com/shiller-pe/table?f=m"; //"http://www.wsj.com/mdc/public/page/2_3021-peyield.html";
        
        StringBuilder sb = new StringBuilder(1000);
        
        try {
            URLConnection connection = new URL(page).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException e) {
            // handle exception
        }

        String content = sb.toString();
        
        //System.out.println("test" + content);
        
        String sp = "<tr class=\"even\">        <td class=\"left\">"; //"S&amp;P 500"; //"S&P 500";
        String peCurrent = "";
        
        for (int i = 0; i < content.length()-47; i++)
        {
            if (content.substring(i, i+47).contains(sp))
            {
                System.out.println("Found!");
                peCurrent = content.substring(i+47, i+200);
                break;
            }
        }
        
        //System.out.println(peCurrent);
        
        String beginning = "<td class=\"right\">";  //"<td style=\"font-weight:bold;\" class=\"num\">";
        String shillerNew = "";
        
        for (int i = 0; i < peCurrent.length()-beginning.length(); i++)
        {
            if (peCurrent.substring(i, i+beginning.length()).contains(beginning))
            {
                //System.out.println("Almost there...");
                shillerNew = peCurrent.substring(i+beginning.length(), i+beginning.length()+5);
                break;
            }
        }
        
        System.out.println(shillerNew);
        return shillerNew;
    }
    
    public static String getCurrentRate() throws FileNotFoundException, IOException
    {
        String page = "http://www.multpl.com/10-year-treasury-rate/table/by-month"; //"http://www.wsj.com/mdc/public/page/2_3021-peyield.html";
        
        StringBuilder sb = new StringBuilder(1000);
        
        try {
            URLConnection connection = new URL(page).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException e) {
            // handle exception
        }

        String content = sb.toString();
        
        //System.out.println("test" + content);
        
        String sp = "<tr class=\"even\">        <td class=\"left\">"; //"S&amp;P 500"; //"S&P 500";
        String peCurrent = "";
        
        for (int i = 0; i < content.length()-47; i++)
        {
            if (content.substring(i, i+47).contains(sp))
            {
                System.out.println("Found!");
                peCurrent = content.substring(i+47, i+200);
                break;
            }
        }
        
        //System.out.println(peCurrent);
        
        String beginning = "<td class=\"right\">";  //"<td style=\"font-weight:bold;\" class=\"num\">";
        String peNew = "";
        
        for (int i = 0; i < peCurrent.length()-beginning.length(); i++)
        {
            if (peCurrent.substring(i, i+beginning.length()).contains(beginning))
            {
                //System.out.println("Almost there...");
                peNew = peCurrent.substring(i+beginning.length(), i+beginning.length()+4);
                break;
            }
        }
        //peNew = "3.4";
        System.out.println(peNew);
        return peNew;
    }
    
    public static String getOldPE() throws FileNotFoundException, IOException
    {
        String page = "http://www.multpl.com/table?f=m"; //"http://www.wsj.com/mdc/public/page/2_3021-peyield.html";
        
        StringBuilder sb = new StringBuilder(1000);
        
        try {
            URLConnection connection = new URL(page).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException e) {
            // handle exception
        }

        String content = sb.toString();
        
        //System.out.println("test" + content);
        
        String sp = "<tr class=\"even\">        <td class=\"left\">"; //"S&amp;P 500"; //"S&P 500";
        String peCurrent = "";
        
        for (int i = 0; i < content.length()-47; i++)
        {
            if (content.substring(i, i+47).contains(sp))
            {
                System.out.println("Found!");
                peCurrent = content.substring(i+47, i+2000);
                break;
            }
        }
        
        //System.out.println(peCurrent);
        
        String beginning = "<td class=\"right\">";  //"<td style=\"font-weight:bold;\" class=\"num\">";
        String peNew = "";
        
        int count = 0;
        
        for (int i = 0; i < peCurrent.length()-beginning.length(); i++)
        {
            if (peCurrent.substring(i, i+beginning.length()).contains(beginning))
            {
                if (count < 12)
                    count++;
                else
                {                
                    peNew = peCurrent.substring(i+beginning.length(), i+beginning.length()+5);
                    break;
                }
            }
        }
        
        System.out.println(peNew);
        return peNew;
    }
    
    public static String getAverageRate() throws FileNotFoundException, IOException
    {
        String page = "http://www.multpl.com/10-year-treasury-rate/table/by-year"; //"http://www.wsj.com/mdc/public/page/2_3021-peyield.html";
        
        StringBuilder sb = new StringBuilder(1000);
        
        try {
            URLConnection connection = new URL(page).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException e) {
            // handle exception
        }

        String content = sb.toString();
        
        //System.out.println("test" + content);
        
        String sp = "<tr class=\"even\">        <td class=\"left\">"; //"S&amp;P 500"; //"S&P 500";
        String peCurrent = "";
        
        for (int i = 0; i < content.length()-47; i++)
        {
            if (content.substring(i, i+47).contains(sp))
            {
                System.out.println("Found!");
                peCurrent = content.substring(i+47, i+2000);
                break;
            }
        }
        
        //System.out.println(peCurrent);
        
        String beginning = "<td class=\"right\">";  //"<td style=\"font-weight:bold;\" class=\"num\">";
        String peNew = "";
        
        int count = 0;
        
        double rateTotal = 0;
        
        for (int i = 0; i < peCurrent.length()-beginning.length(); i++)
        {
            if (peCurrent.substring(i, i+beginning.length()).contains(beginning))
            {
                if (count < 11)
                {
                    //System.out.println(peCurrent.substring(i+beginning.length(), i+beginning.length()+4));
                    count++;  
                    if (count >= 1)
                        rateTotal += Double.valueOf(peCurrent.substring(i+beginning.length(), i+beginning.length()+4));
                } else
                    break;
            }
        }
        
        rateTotal /= 10;
        
        peNew = "" + rateTotal;
        
        System.out.println(peNew);
        return peNew;
    }
    
    static String getIndexPrice()
    {
        String page = "http://www.multpl.com/s-p-500-historical-prices/table/by-month"; //"http://www.wsj.com/mdc/public/page/2_3021-peyield.html";
        
        StringBuilder sb = new StringBuilder(1000);
        
        try {
            URLConnection connection = new URL(page).openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
        } catch (IOException e) {
            // handle exception
        }

        String content = sb.toString();
        
        //System.out.println("test" + content);
        
        String sp = "<tr class=\"even\">        <td class=\"left\">"; //"S&amp;P 500"; //"S&P 500";
        String peCurrent = "";
        
        for (int i = 0; i < content.length()-47; i++)
        {
            if (content.substring(i, i+47).contains(sp))
            {
                System.out.println("Found!");
                peCurrent = content.substring(i+47, i+200);
                break;
            }
        }
        
        //System.out.println(peCurrent);
        
        String beginning = "<td class=\"right\">";  //"<td style=\"font-weight:bold;\" class=\"num\">";
        String peNew = "";
        
        for (int i = 0; i < peCurrent.length()-beginning.length(); i++)
        {
            if (peCurrent.substring(i, i+beginning.length()).contains(beginning))
            {
                //System.out.println("Almost there...");
                peNew = peCurrent.substring(i+beginning.length(), i+beginning.length()+8);
                break;
            }
        }
        
        System.out.println(peNew);
        return peNew;
    }
    
}
