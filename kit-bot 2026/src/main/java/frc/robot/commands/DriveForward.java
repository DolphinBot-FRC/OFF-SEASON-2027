
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.DrivetrainSubsystem;

public class DriveForward extends Command {

    private final DrivetrainSubsystem drivetrain;

    private final double speed;
    private final double durationSeconds;

    private final Timer timer = new Timer();

    public DriveForward(
        DrivetrainSubsystem drivetrain,
        double speed,
        double durationSeconds
    ) {

        this.drivetrain = drivetrain;
        this.speed = speed;
        this.durationSeconds = durationSeconds;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

        timer.restart();
    }

    @Override
    public void execute() {

        drivetrain.arcadeDrive(speed, 0.0);
    }

    @Override
    public void end(boolean interrupted) {

        drivetrain.stop();
        timer.stop();
    }

    @Override
    public boolean isFinished() {

        return timer.hasElapsed(durationSeconds);
    }
}
