package com.fpsrobotics;

import edu.wpi.first.wpilibj.Timer;
import com.fpsrobotics.interfaces.Talons;
import com.fpsrobotics.interfaces.Analog;

/**
 * Launches the Catapult 3 Functions: 1) Acceleration Launch 2) Regular Launch
 * 3) Resets the Catapult to Home Position
 *
 * @author Josh
 */
public class CatapultSuggestedB implements Runnable, Talons, Analog
{
    // Variables

    private int speedInverse = -1;      // Set for shooterTalonTwo. We inverse one of the speeds because electronically, one motor is upside-down
    private int TBDint = 0;
    // Max Speed
    private double maxSpeed;
    // Max Distance
    private int highPotValue = TBDint;
    // Acceleration Speed Percent
    private int stopAccelerationPercent;
    private boolean accelLaunch;
    // RESET VARIABLES
    private int homePotValue = TBDint;
    private boolean isDone;

    /**
     * ACCELERATION LAUNCH CONSTRUCTOR
     *
     * @param highPotValue
     * @param maxSpeed Make as a value between -1.00 & 1.00
     * @param stopAccelerationPercent Make as a percent between 1 & 100
     * ** Question for Mr. Neighbour**
     * How is Ray using the same tools from different classes? Can you do it by implementing from an interface?
     */
    public CatapultSuggestedB(int highPotValue, double maxSpeed, int stopAccelerationPercent)
    {
        this.highPotValue = highPotValue;
        this.maxSpeed = maxSpeed;
        this.stopAccelerationPercent = stopAccelerationPercent;
        this.accelLaunch = true;
        isDone = false;
    }

    /**
     * REGULAR LAUNCH CONSTRUCTOR
     *
     * @param highPotValue
     * @param maxSpeed
     */
    public CatapultSuggestedB(int highPotValue, double maxSpeed)
    {
        this.highPotValue = highPotValue;
        this.maxSpeed = maxSpeed;
        this.accelLaunch = false;
        isDone = false;
    }

    /**
     * Runs the "Full Run" Method
     */
    public void run()
    {
        this.fullRun();
    }

    /**
     * Resets the catapult Accelerated/ Regular Launch Resets the catapult
     */
    public void fullRun()
    {
        this.reset();
        if (this.accelLaunch)
        {
            this.acceleratedLaunch();
        } else
        {
            this.regularLaunch();
        }
        this.reset();
    }

    /**
     * Accelerated Launch Function MAKE A PICTURE TO EXPLAIN THIS... :/
     */
    public void acceleratedLaunch()
    {
        // Accleration Distance Percent Variables
        // LAUNCH VARIABLES: "Get to MAX SPEED after SOME PERCENT of MAX DISTANCE"
        double currentSpeed = 0.5;
        double fullAccelDistance = this.stopAccelerationPercent * this.highPotValue;
        int currentAccelDistance;
        int previousAccelDistance = shooterPot.getValue();

        // Restricts the Max Speed
        if (this.maxSpeed > 1.00)
        {
            this.maxSpeed = 1.00;
        }

        // LAUNCH THE CATAPULT WITH ACCELERATION TO INPUTED SPEED
        while (shooterPot.getValue() <= this.highPotValue)
        {
            currentAccelDistance = shooterPot.getValue();
            if (currentAccelDistance == (previousAccelDistance + ((fullAccelDistance - previousAccelDistance) / 2)))
            // MATH showing if distance has increased by half of what it was
            {
                currentSpeed += ((maxSpeed - currentSpeed) / 2);
                // MATH showing for speed to increment by half of what's left
                // Resulting in speed stabalization as distance approaches stop-accel-pot-distance (-2^(-x))
                previousAccelDistance = currentAccelDistance;
            }

            shooterTalonOne.set(currentSpeed);
            shooterTalonTwo.set(currentSpeed * speedInverse);
        }
        // Lets the motors settle
        shooterTalonOne.set(0.00);
        shooterTalonTwo.set(0.00 * speedInverse);
        Timer.delay(2.50);
        this.isDone = true;
    }

    /**
     * Regular Launch Function LAUNCH THE CATAPULT TO INPUTED SPEED
     */
    public void regularLaunch()
    {
        while (shooterPot.getValue() <= this.highPotValue)
        {
            shooterTalonOne.set(this.maxSpeed);
            shooterTalonTwo.set(this.maxSpeed * speedInverse);
        }

        // Lets the motors settle
        shooterTalonOne.set(0.00);
        shooterTalonTwo.set(0.00 * speedInverse);
        Timer.delay(2.50);
        this.isDone = true;
    }

    /**
     * Calls if done: back to user in "CatapultSuggestedA"
     *
     * @return
     */
    public boolean done()
    {
        return this.isDone;
    }

    /**
     * Reset Function
     */
    public void reset()
    {
        while (shooterPot.getValue() >= this.homePotValue)
        {
            shooterTalonOne.set(-0.40);
            shooterTalonTwo.set(-0.40 * speedInverse);
        }

        // Lets the motors settle
        shooterTalonOne.set(0.00);
        shooterTalonTwo.set(0.00 * speedInverse);
        Timer.delay(2.50);

    }
}