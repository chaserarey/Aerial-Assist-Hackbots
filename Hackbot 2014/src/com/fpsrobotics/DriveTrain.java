package com.fpsrobotics;

import com.fpsrobotics.interfaces.Analog;
import com.fpsrobotics.interfaces.ControlMap;
import com.fpsrobotics.interfaces.IsAThread;
import com.fpsrobotics.interfaces.Joysticks;
import com.fpsrobotics.interfaces.Solenoids;
import com.fpsrobotics.interfaces.Talons;
import com.fpsrobotics.interfaces.ThreadsAndClasses;
import com.fpsrobotics.interfaces.Values;

/**
 *
 * @author ray
 */
public class DriveTrain implements Runnable, Talons, Joysticks, Values, Analog, Solenoids, ControlMap, ThreadsAndClasses, IsAThread
{
    boolean isInterrupted = false;
    
    /**
     *
     * Controls the drive train through ControlDrive's methods in a separate
     * thread.
     *
     */
    public void run()
    {
        long previousTime = System.currentTimeMillis();
        isInterrupted = false;
        
        while (!isInterrupted)
        {
            if (Math.abs(previousTime - System.currentTimeMillis()) >= THREAD_REFRESH_RATE)
            {
                // Check if we need to adjust speed, or switch to turbo
                driveControl.drive(driveControl.deadzoneConstrain(leftJoystick), driveControl.deadzoneConstrain(rightJoystick), leftDrive, rightDrive, true);
                driveControl.driveTurbo(leftJoystick, rightJoystick, leftDrive, rightDrive, TURBO_BUTTON);
//                driveControl.accelSwitchGears(leftJoystick, rightJoystick, accel);
                
                // Shift if we hold the button
                pneumatics.switchGears(gearSolenoid, leftJoystick.getRawButton(GEAR_SWITCH_ONE));

                previousTime = System.currentTimeMillis();
            }
        }
    }
    
    public void interrupt()
    {
        isInterrupted = true;
    }
}
