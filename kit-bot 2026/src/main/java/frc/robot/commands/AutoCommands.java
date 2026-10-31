
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

import frc.robot.subsystems.DrivetrainSubsystem;

public final class AutoCommands {

    private AutoCommands() {}

    // Primeiro teste: andar para a frente
    public static Command driveOffLine(
        DrivetrainSubsystem drivetrain
    ) {

        return new DriveForward(
            drivetrain,
            0.25,
            1.0
        ).withName("Auto - Drive Forward");
    }

    // Segunda rotina: frente, giro e frente
    public static Command forwardTurnForward(
        DrivetrainSubsystem drivetrain
    ) {

        return Commands.sequence(

            new DriveForward(
                drivetrain,
                0.25,
                1.0
            ),

            new TurnRobot(
                drivetrain,
                0.25,
                0.5
            ),

            new DriveForward(
                drivetrain,
                0.25,
                1.0
            )

        ).withName("Auto - Forward Turn Forward");
    }
}
