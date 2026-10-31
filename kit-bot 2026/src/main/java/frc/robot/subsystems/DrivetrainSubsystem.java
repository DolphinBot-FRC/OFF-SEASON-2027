
package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

import static frc.robot.Constants.DriveConstants.*;

public class DrivetrainSubsystem extends SubsystemBase {

    //Cria a arena 2D 
    private final Field2d field = new Field2d();

    // DEFINE UM OBJETO PARA ARMAZENAR A POSIÇÃO SIMULADA DO ROBO
    private Pose2d simulatedPose = new Pose2d(
        1.0,
        1.0,
        new Rotation2d()
    );

    // Comandos de movimento
    private double simulatedForward = 0.0;
    private double simulatedRotation = 0.0;

    // Velocidades máximas da simulação
    private static final double MAX_SIM_SPEED = 3.0;
    private static final double MAX_SIM_ROTATION = 3.0;

    // Tempo entre atualizações (20 ms)
    private static final double SIM_DT = 0.02;


    // Motores do lado esquerdo
    private final SparkMax leftLeader;
    private final SparkMax leftFollower;

    // Motores do lado direito
    private final SparkMax rightLeader;
    private final SparkMax rightFollower;

    // Sistema de controle Tank / Differential Drive
    private final DifferentialDrive drive;

    public DrivetrainSubsystem() {

        // Inicialização dos motores
        // kBrushed: para motores escovados, como CIM.
        // Para NEO brushless, utilizar kBrushless.

        leftLeader = new SparkMax(
            LEFT_LEADER_ID, MotorType.kBrushed
        );

        leftFollower = new SparkMax(
            LEFT_FOLLOWER_ID, MotorType.kBrushed
        );

        rightLeader = new SparkMax(
            RIGHT_LEADER_ID, MotorType.kBrushed
        );

        rightFollower = new SparkMax(
            RIGHT_FOLLOWER_ID, MotorType.kBrushed
        );

        // Criação do DifferentialDrive
        drive = new DifferentialDrive(
            leftLeader,
            rightLeader
        );

        // Timeout de comunicação CAN
        leftLeader.setCANTimeout(250);
        leftFollower.setCANTimeout(250);
        rightLeader.setCANTimeout(250);
        rightFollower.setCANTimeout(250);

        // Configuração geral dos motores
        SparkMaxConfig config = new SparkMaxConfig();

        config.voltageCompensation(12);
        config.smartCurrentLimit(DRIVE_MOTOR_CURRENT_LIMIT);

        // Follower esquerdo segue o líder esquerdo
        config.follow(leftLeader);

        leftFollower.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        // Follower direito segue o líder direito
        config.follow(rightLeader);

        rightFollower.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        // Configuração do líder direito
        config.disableFollowerMode();

        rightLeader.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        // Inversão do líder esquerdo
        // Necessária para compensar a orientação dos motores
        config.inverted(true);

        leftLeader.configure(
            config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );

        //Define Filde como variável para alteração
        SmartDashboard.putData("Field", field);
        field.setRobotPose(simulatedPose);

    }

    // Movimentação Arcade:
    // xSpeed = frente e trás
    // zRotation = rotação

    public void arcadeDrive(
        double xSpeed,
        double zRotation
    ) {

        simulatedForward = Math.max(-1.0,
        Math.min(1.0, xSpeed));

        simulatedRotation = Math.max(-1.0,
        Math.min(1.0, zRotation));

        drive.arcadeDrive(xSpeed, zRotation);
    }

    // Parar completamente o drivetrain
    public void stop() {

        simulatedForward = 0.0;
        simulatedRotation = 0.0;

        drive.stopMotor();
    }

    public Command driveArcade(
        DoubleSupplier xSpeed,
        DoubleSupplier zRotation
    ) {

        return this.runEnd(

            () -> arcadeDrive(
                xSpeed.getAsDouble(),
                zRotation.getAsDouble()
            ),

            this::stop
        );
    }

    @Override
    public void periodic() {

        // Posição inicial de demonstração
        field.setRobotPose(
            new Pose2d(
                1.0,
                1.0,
                new Rotation2d()
            )
        );
    }

        
    @Override
    public void simulationPeriodic() {

        // Velocidade linear simulada
        double velocity =
            simulatedForward * MAX_SIM_SPEED;

        // Velocidade angular simulada
        double angularVelocity =
            simulatedRotation * MAX_SIM_ROTATION;

        // Orientação atual do robô
        double currentAngle =
            simulatedPose.getRotation().getRadians();

        // Variação angular no intervalo
        double deltaAngle =
            angularVelocity * SIM_DT;

        // Ângulo médio para calcular o deslocamento
        double middleAngle =
            currentAngle + deltaAngle / 2.0;

        // Deslocamento no campo
        double deltaX =
            velocity * Math.cos(middleAngle) * SIM_DT;

        double deltaY =
            velocity * Math.sin(middleAngle) * SIM_DT;

        // Atualiza posição e rotação
        simulatedPose = new Pose2d(
            simulatedPose.getX() + deltaX,
            simulatedPose.getY() + deltaY,
            new Rotation2d(currentAngle + deltaAngle)
        );

        // Atualiza o desenho do robô no campo
        field.setRobotPose(simulatedPose);
    }

}
