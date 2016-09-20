package com.vm62.diary.common.utils;

import java.text.DecimalFormat;

/**
 * Static utility methods operating with numerics
 *
 */
public abstract class NumberUtils {

	/**
     * Hide Utility Class Constructor
     */
    private NumberUtils(){

    }

    /**
	 * Converts given string to double.
     * Returns null if conversion is not possible.
     *
     * @param string
     * @return
	 */
	public static Double getDouble(String string) {
		if (string != null) {
			string = string.replaceAll(",", "\\.");
			try	{
				return new Double(string);
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Converts given string to integer.
	 * Returns null if conversion is not possible.
	 *
	 * @param string
	 * @return
	 */
	public static Integer getInteger(String string) {
		if (!ValidationUtils.isNullOrTrimEmpty(string)) {
			try	{
				return Integer.valueOf(string.trim());
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Default value for delta used to compare doubles
	 */
	public final static double DELTA = 0.0000000001;
	
	/**
	 * Compares two doubles and determines equality within a positive delta. 
	 * 
	 * @param d1 first value to be compared
	 * @param d2 second value to be compared
	 * @param delta the maximum delta between <code>d1</code> and
	 *            <code>d2</code> for which both numbers are still
	 *            considered equal.
	 * @return <code>true</code> if values are equal within provided delta, <code>false</code> otherwise. 
	 */
	public static boolean equals(double d1, double d2, double delta) {
		return Math.abs(d1 - d2) < delta;
	}
	
	/**
	 * If given Integer value is null or positive returns value,
	 * else returns null.  
	 * @param value Integer value to be checked
	 * @return positive Integer value or null
	 */
	public static Integer toNullOrPositiveInteger(Integer value) {
		return value == null || value > 0 ? value : null;
	}


	/**
	 * Converts double value into nominator/denominator representation.
	 * For example: 0.05 -> "1/20"
	 * This method is entrance point with initial maximal denominator 10 futher it could be rerunned with values, 100, 1000
	 * in case nominator will be found 0
	 * @param value
	 * @return
	 */
	public static String doubleToFractionalString(Double value) {
		double flooredValue = Math.floor(value);
		if (flooredValue > 0) {
			String rest = doubleToFractionalString(value - flooredValue, 10);
			if ((rest.contains(",")) || (rest.contains("."))) {
				return new DecimalFormat("0.00000").format(value);
			}
			if ("0".equals(rest)){
				return String.valueOf(Double.valueOf(flooredValue).intValue());
			}
			return Double.valueOf(flooredValue).intValue() + " " + rest;
		}
		return doubleToFractionalString(value, 10);
	}

    public static String doubleToFractionalString(Double value, final Integer maximalDenominator) {
        if (Double.valueOf(value.intValue()).equals(value) ) {
            return String.valueOf(value.intValue());
        } else {
            Double decimal = value;

            Integer nominator = 1;
            Integer denominator = 1;

            //compute 7 first denominators - 7 is more than enough
            int[] denominators = new int[10];
            for (int i=0; i<10; i++) {
                denominators[i] = decimal.intValue();
                decimal = 1.0 / (decimal - denominators[i]);
            }

            for (int i=0; i<10; i++) {

                int temp = 0;

                nominator = 1;
                denominator = 1;

                // recompure numerator and denominator using i first denominators
                for(int j=i; j>=0; j-- ) {
                    denominator = nominator;
                    nominator = (nominator * denominators[j]) + temp;
                    temp = denominator;
                }

                //skip further iterations if next denominator is greater than 10
                if (i < 10-1 && denominators[i+1] > maximalDenominator) {
                    break;
                }
            }

	        if (nominator == 0){
		        if (maximalDenominator <100){
			        return doubleToFractionalString(value, maximalDenominator * 10);
		        } else {
			        return new DecimalFormat("0.00000").format(value);
		        }
	        }

            return nominator.toString() + "/" + denominator.toString();
        }
    }
}
