/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stratinvest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

/**
 *
 * @author harry
 */
public class Quant {
    
    public static double getHistoricalAverage(double[] data)
    {
        double sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += data[i];
        
        return sum / data.length;
    }
    
    public static double getAverage50(double[] data)
    {
        double sum = 0;
        for (int i = data.length - (50*12); i < data.length; i++) {
            sum += data[i];
        }

        return sum / (50*12);
    }
    
    public static double getAverage20(double[] data)
    {
        double sum = 0;
        for (int i = data.length - (20 * 12); i < data.length; i++) {
            sum += data[i];
        }

        return sum / (20 * 12);
    }
    
    public static double getGeoMean(int monthStart, int monthEnd, double principal, double balance)
    {
        double years = (monthEnd - monthStart) / 12;
        
        double rtn = Math.pow((balance/principal),(1/years));
        return rtn;
    }
    
    public static double getGeoMean(int period, double totalRet)
    {
        double per = (double) period;
        //per = 10;
        double rtn = Math.pow((totalRet),(1/per));
        return rtn;
    }
    
    public static double round(double value, int places) 
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static double calcDeviation(double[] index)
    {
        double sum = 0;
        double newSum = 0;

        for (int i = 0; i < index.length; i++) {
            sum = sum + index[i];
        }
        double mean = (sum) / (index.length);

        for (int j = 0; j < index.length; j++) {
            // put the calculation right in there
            newSum = newSum + ((index[j] - mean) * (index[j] - mean));
        }
        double squaredDiffMean = (newSum) / (index.length);
        double standardDev = (Math.sqrt(squaredDiffMean));
        return standardDev;
    }
    
    public static double calcBeta(double[] strat, double[] index)
    {
        double stratDev = -1;
        double indexDev = -1;
        double beta = 0;

        return beta;
        
    }
}
