/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpsrobotics.constants;

import com.fpsrobotics.hardware.Analogs;

/**
 *
 * @author Ben
 */
public class Constants
{
    public static final int THREAD_REFRESH_RATE = 50;
    
    final static int ORIGINAL_HOME = Analogs.SHOOTER_POTENTIOMETER.getAverageValue();
    
    //ALPHA = 0
    //BETA = -225
    public final static int ALPHA_BETA = -225;
    
    // public static final int HOME_POT_VALUE = 175;
    //public static final int HOME_POT_VALUE = 440;
    public static final int HIGH_POT_VALUE = 1000;
    // public static final int HIGH_POT_VALUE = 800;
    
    public static int HOME_POT_VALUE = ORIGINAL_HOME + 50;
    
    public static final double SHOOTER_MAX_SPEED = 1.0;
    public static final double SHOOTER_MIN_SPEED = 0.2;
}