package com.fpsrobotics;

import com.fpsrobotics.interfaces.Analog;
import com.fpsrobotics.interfaces.ControlMap;
import com.fpsrobotics.interfaces.DIOs;
import com.fpsrobotics.interfaces.Joysticks;
import com.fpsrobotics.interfaces.PID;
import com.fpsrobotics.interfaces.Talons;
import com.fpsrobotics.interfaces.ThreadsAndClasses;

/**
 *
 * Controls the piston shooter, button 11 on the joystick causes it to fire.
 * More code will be added later as we decide how to regulate the shooter's
 * throwing arm.
 *
 * @author ray
 */
public class Shooter implements Runnable, Joysticks, Analog, Talons, DIOs, ControlMap, PID, ThreadsAndClasses
{

//    PIDController shooterPID = shooterControl.PIDInit(shooterEncoder, shooterTalon, LOW_SHOOTER_PID_VALUE, HIGH_SHOOTER_PID_VALUE, shooterP, shooterI, shooterD);

    public void run()
    {
        long previousTime = System.currentTimeMillis();

        while (true)
        {
            if (Math.abs(previousTime - System.currentTimeMillis()) >= THREAD_REFRESH_RATE)
            {
                shooterControl.shootManual(gamepadJoystick, shooterTalon, SHOOTER_MANUAL);

                // Presets (dummy, real presets to be added later)
                presets.shooterPresetBoth(gamepadJoystick, shooterPot, shooterEncoder, shooterTalon, 300, 5, SHOOTER_PRESET_ONE, 1.0);
//                presets.shooterPresetPID(gamepadJoystick, shooterPID, SHOOTER_PRESET_TWO, 10);

                previousTime = System.currentTimeMillis();
            }
        }
    }
}
