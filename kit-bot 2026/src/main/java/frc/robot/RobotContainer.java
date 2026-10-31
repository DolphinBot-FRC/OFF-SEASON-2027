
package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.commands.AutoCommands;
import frc.robot.subsystems.DrivetrainSubsystem;

import static frc.robot.Constants.FuelConstants.*;
import static frc.robot.Constants.OperatorConstants.*;

import frc.robot.subsystems.FuelSubsystem;

public class RobotContainer {

    // Subsistema de intake e shooter
    private final FuelSubsystem fuelSubsystem =
    new FuelSubsystem();

    // Subsistema de locomoção
    private final DrivetrainSubsystem drivetrain =
        new DrivetrainSubsystem();

    // Controle do piloto
    private final CommandXboxController driverController =
        new CommandXboxController(DRIVER_CONTROLLER_PORT);

    // Seletor de autônomo
    private final SendableChooser<Command> autoChooser =
        new SendableChooser<>();

    public RobotContainer() {

      //Mapea os botões
        configureBindings();

        // Controle padrão do drivetrain
        drivetrain.setDefaultCommand(
            drivetrain.driveArcade(

                // Frente e trás (Caso l1 esteja pressionado usa velocidade de boost. caso não esteja pressionado usa a velocidade normal)
                () -> -driverController.getLeftY()
                    * (driverController.getHID().getLeftBumperButton()
                        ? BOOST_DRIVE_SCALING
                        : DRIVE_SCALING),

                // Rotação
                () -> -driverController.getRightX()
                    * (driverController.getHID().getLeftBumperButton()
                        ? ROTATION_DRIVE_SCALING
                        : ROTATION_SCALING)
            )
        );

        // Configuração dos autônomos
        autoChooser.setDefaultOption(
            "Drive Forward",
            AutoCommands.driveOffLine(drivetrain)
        );

        autoChooser.addOption(
            "Forward Turn Forward",
            AutoCommands.forwardTurnForward(drivetrain)
        );

        SmartDashboard.putData(
            "Autonomous",
            autoChooser
        );
    }

    private void configureBindings() {

    // L2 - Intake
    driverController
        .leftTrigger(TRIGGER_THRESHOLD)
        .whileTrue(
            fuelSubsystem.intakeCommand()
        );

    // R2 - Shooter
    driverController
        .rightTrigger(TRIGGER_THRESHOLD)
        .whileTrue(
            fuelSubsystem.shootCommand()
        );
}

    public Command getAutonomousCommand() {

        return autoChooser.getSelected();
    }
}
