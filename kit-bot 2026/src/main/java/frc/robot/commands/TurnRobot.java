
package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.subsystems.DrivetrainSubsystem;

public class TurnRobot extends Command {

    private final DrivetrainSubsystem drivetrain;

    private final double rotationSpeed;
    private final double durationSeconds;

    private final Timer timer = new Timer();

    public TurnRobot(
        DrivetrainSubsystem drivetrain,
        double rotationSpeed,
        double durationSeconds
    ) {

        this.drivetrain = drivetrain;
        this.rotationSpeed = rotationSpeed;
        this.durationSeconds = durationSeconds;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

        timer.restart();
    }

    @Override
    public void execute() {

        drivetrain.arcadeDrive(
            0.0,
            rotationSpeed
        );
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
