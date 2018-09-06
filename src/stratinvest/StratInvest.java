package stratinvest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import static jdk.nashorn.internal.objects.NativeMath.round;

public class StratInvest {

    static ArrayList<Strategy> strategies = new ArrayList<>();

   //indexed by strategy, then by period/year within each sub-arraylist.
    //static ArrayList<ArrayList<Double>> totalReturns = new ArrayList<>();
    
    static ArrayList[] totalLosses = null;
    static ArrayList[] avgReturns = null;

    static String[] temp = new String[1416];

    static double[] price = new double[1416]; //price of S&P500 Index by month
    static double[] pe = new double[1416]; //P/E ratio of index by month
    static double[] shiller = new double[1416]; //10-year trailing P/E by month

    static double[] birthRates = new double[55];
    
    static double[] bondRates = new double[118];
    
    static double[] yields = new double[1416];


    public static void main(String[] args) throws IOException
    {
        /*    backtests with data.    */
        backtest();
        
        /*    gets current rating via APIs    */
        //Current.getCurrent();
    }
        
    public static void backtest() throws IOException
    {
        //System.out.println("Price");
        readData("prices", price);
        //System.out.println("P/E");
        readData("pe", pe);
        //System.out.println("Shiller P/E");
        readData("shiller", shiller);

        readData("birthrates", birthRates);
        
        readData("rates", bondRates);
        
        readData("dividends", yields);

        /*
        Beginning historical backtesting in 20-year increments.
        Increment by 1-year. Should come out to 117 individual investment periods.
        Aggregate data & report. 
        */

        int INCREMENT = 1; //investment period in years
        int YEARSAGO = 55; //how many years ago: range is 118:1 + investment period. 55 for BirthRates
        int ENDYEARSAGO = 0;
        
        int PERIODS = YEARSAGO - (INCREMENT + ENDYEARSAGO);
        
        //double[] avgReturns = {0};
                
        //double maxLoss = 0;
        
        Boolean firstTime = true; //is this the first test? Used for strategy comparison.
        
        for (int i = YEARSAGO; i >= (INCREMENT+ENDYEARSAGO); i--)
        {
            //System.out.println("Year: " + (2017-i));
            
            strategies = new ArrayList<>();
            
            beginTests(i, INCREMENT);
            
            if (firstTime)
            {
                avgReturns = new ArrayList[strategies.size()];
                
                totalLosses = new ArrayList[strategies.size()];
                
                for (int j = 0; j < avgReturns.length; j++)
                {
                    avgReturns[j] = new ArrayList<>();
                    totalLosses[j] = new ArrayList<>();
                }
                
                firstTime = false;
            }
            
            for (int j = 0; j < strategies.size(); j++)
            {
                //System.out.println(strategies.get(j).roi);
                avgReturns[j].add(strategies.get(j).roi);
                
                totalLosses[j].add(strategies.get(j).avgLoss);
                
            }
            
            
            
        }
        
        System.out.println("\nGains & Losses: ");
        
        double avgReturn = 0;
        
        //for (int k = 0; k < avgReturns.length; k++)
        {
            
            //avgReturn += avgReturns[k];
            
            //System.out.println(strategies.get(k).stratName + ": " + avgReturns[k]);
        }
        
        
        double avgLossOfMarket = 0;
        
        for (int k = 0; k < strategies.size(); k++)
        {
            double maxLoss = 0;
            double avgLoss = 0;
            avgReturn = 0;
            
            for (int j = 0; j < totalLosses[k].size(); j++)
            {
                avgLoss += (double)totalLosses[k].get(j);
                
                //System.out.println(totalLosses[k]);
                
            }
            
            for (int j = 0; j < avgReturns[k].size(); j++)
            {
                avgReturn += (double)avgReturns[k].get(j);
                
                if ((double)avgReturns[k].get(j) < maxLoss)
                {
                    maxLoss = (double)avgReturns[k].get(j);
                }
                
            }
            
                      
            avgLoss /= totalLosses[k].size();
            
            //if (totalLosses[k].size() == 0)
                //avgLoss = -0.000000001;
            
            if (k == 0)
                avgLossOfMarket = avgLoss;
            /*
            
                risk-adjusted return: total return of strat / (average downturn of strat / average downturn of lump)
            
            */
            //avgReturns[k] /= ();
            
            //avgReturns[k] = (avgReturns[k]);
            
            //avgReturns[k] = avgReturn;
            
            //avgReturns[k] = Quant.round(avgReturns[k], 4);
            
            avgReturn /= avgReturns[k].size();
            
            //avgReturn = Quant.getGeoMean(INCREMENT, avgReturn);
            
            avgReturn /= INCREMENT;
            
            //System.out.println("\n" + avgReturn + "\n");
            
//            double riskRet = Quant.round((avgReturn / (avgLoss / avgLossOfMarket))*100, 4);
            
            avgReturn = Quant.round(avgReturn*100, 4);
            
            avgLoss = Quant.round(avgLoss*100, 4);
            maxLoss = Quant.round(maxLoss*100, 4);
            
            //riskRet = Quant.getGeoMean(50, riskRet);
            
            //avgReturns[k] = Quant.getGeoMean(YEARSAGO - (INCREMENT + ENDYEARSAGO), avgReturns[k]);'
            
            //double annualized = Quant.getGeoMean(YEARSAGO - (INCREMENT + ENDYEARSAGO), avgReturns[k]-1);
            
            System.out.println(strategies.get(k).stratName + "| " + "Risk-Adjusted Leveraged Return: "
                    //+ riskRet 
                    + " Average return: " //+ annualized + ", " 
                    + avgReturn + " Average downturn: " + avgLoss + " Max downturn: " 
                    + maxLoss + " ");
                    //+ totalLosses[k]);
        }
    }

    public static void readData(String filename, double[] toArray) throws IOException
    {
            //must read data from csv.
            BufferedReader br = new BufferedReader(new FileReader("/home/harry/NetBeansProjects/StratInvest/src/stratinvest/" + filename + ".csv"));
            String line = null;
        while ((line = br.readLine()) != null) {
          String[] values = line.split(",");
          int i = 0;
          for (String str : values) {
            //System.out.println(str);
            temp[i] = str;
            i++;
          }
        }
        br.close();  

        for (int i = 0; i < toArray.length; i++)
        {
            //System.out.println(temp[i]);
            toArray[i] = Double.valueOf(temp[i].replace("%",""));
        }

    }	

    static void beginTests(int YEARS, int PERIOD)
    {        
        //Strategy LumpSum = new LumpSum(YEARS, PERIOD);
        //strategies.add(LumpSum);
        
        //Strategy PE = new PE(YEARS, PERIOD);
        //strategies.add(PE);        
        
        //Strategy Shiller = new Shiller(YEARS, PERIOD);
        //strategies.add(Shiller);

        //Strategy BirthStr = new BirthStrat(YEARS, PERIOD, 0, 0);
        //strategies.add(BirthStr);
        
        //Strategy YieldStr = new YieldStrat(YEARS, PERIOD);
        //strategies.add(YieldStr);
        
        Strategy EarningsStr = new EarningsStrat(YEARS, PERIOD);
        strategies.add(EarningsStr);
        
        /*
        for (double d : yields)
            System.out.println(d);
        */
        
        //Strategy DCA = new DollarCostAvg(YEARS, PERIOD);
        //strategies.add(DCA);
        
        //Strategy PECD = new PECD(YEARS, PERIOD);
        //strategies.add(PECD);
        
    
    }
}
