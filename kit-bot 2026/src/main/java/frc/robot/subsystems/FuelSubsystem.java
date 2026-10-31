
package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FuelConstants.*;

public class FuelSubsystem extends SubsystemBase {

    // Motor do Intake
    private final SparkMax intakeMotor;

    // Motor do Shooter
    private final SparkMax shooterMotor;

    public FuelSubsystem() {

        // Inicializa os dois motores
        // Exemplo para motores REV NEO brushless

        intakeMotor = new SparkMax(
            INTAKE_MOTOR_ID,
            MotorType.kBrushless
        );

        shooterMotor = new SparkMax(
            SHOOTER_MOTOR_ID,
            MotorType.kBrushless
        );

        // Inicia com os motores parados
        stop();
    }

    // Controle individual dos motores
    public void setMotors(
        double intakeSpeed,
        double shooterSpeed
    ) {

        // Limita a potência entre -1 e 1
        intakeSpeed = Math.max(-1.0,
            Math.min(1.0, intakeSpeed));

        shooterSpeed = Math.max(-1.0,
            Math.min(1.0, shooterSpeed));

        intakeMotor.set(intakeSpeed);
        shooterMotor.set(shooterSpeed);
    }

    // Para os dois motores
    public void stop() {

        intakeMotor.set(0.0);
        shooterMotor.set(0.0);
    }

    // =================================
    // L2 - INTAKE
    // =================================

    public Command intakeCommand() {

        return this.runEnd(

            () -> setMotors(
                L2_INTAKE_SPEED,
                L2_SHOOTER_SPEED
            ),

            this::stop

        ).withName("Fuel - Intake");
    }

    // =================================
    // R2 - SHOOTER
    // =================================

    public Command shootCommand() {

        return this.runEnd(

            () -> setMotors(
                R2_INTAKE_SPEED,
                R2_SHOOTER_SPEED
            ),

            this::stop

        ).withName("Fuel - Shoot");
    }

    @Override
    public void periodic() {

        // Atualizações periódicas do subsistema
    }
}
