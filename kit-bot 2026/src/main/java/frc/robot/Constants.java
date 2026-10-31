
package frc.robot;

public final class Constants {

    private Constants() {}

    public static final class DriveConstants {

        // CAN IDs dos motores
        public static final int LEFT_LEADER_ID = 1;
        public static final int LEFT_FOLLOWER_ID = 2;

        public static final int RIGHT_LEADER_ID = 3;
        public static final int RIGHT_FOLLOWER_ID = 4;

        // Limite de corrente (referência do KitBot 2026)
        public static final int DRIVE_MOTOR_CURRENT_LIMIT = 40;
    }

    public static final class OperatorConstants {

        // Porta USB do controle do Driver
        public static final int DRIVER_CONTROLLER_PORT = 0;

        // Sensibilidade inicial dos controles
        public static final double DRIVE_SCALING = 0.40;
        public static final double ROTATION_SCALING = 0.40;

        // Velocidade do boost
        public static final double BOOST_DRIVE_SCALING = 0.90;
        public static final double ROTATION_DRIVE_SCALING = 0.70;
    }

    
    public static final class FuelConstants {

        // CAN IDs dos novos SPARK MAX
        public static final int INTAKE_MOTOR_ID = 5;
        public static final int SHOOTER_MOTOR_ID = 6;

        // L2 - Entrada de elementos
        public static final double L2_INTAKE_SPEED = 0.35;
        public static final double L2_SHOOTER_SPEED = -0.25;

        // R2 - Lançamento
        public static final double R2_INTAKE_SPEED = -0.25;
        public static final double R2_SHOOTER_SPEED = 0.60;

        // Sensibilidade dos gatilhos
        public static final double TRIGGER_THRESHOLD = 0.5;
    }

}
