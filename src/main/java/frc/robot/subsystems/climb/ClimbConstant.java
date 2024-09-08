package frc.robot.subsystems.climb;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import frc.robot.util.LoggedTunableNumber;

public class ClimbConstant {

  public static final int CAN_ID;
  public static final double CURRENT_LIMIT;
  public static final DCMotor CLIMB_MOTOR;
  public static final double GEAR_RATIO;
  public static final double CARRIAGE_MASS;
  public static final double MIN_HEIGHT;
  public static final double MAX_HEIGHT;
  public static final boolean SIMULATE_GRAVITY;
  public static final double STARTING_HEIGHT;
  public static final double DRUM_RADIUS;
  public static final DCMotor MOTOR_CONFIG;
  public static final LoggedTunableNumber CLIMB_HEIGHT_METERS;
  public static final LoggedTunableNumber RETRACT_HEIGHT_METERS;
  public static final LoggedTunableNumber RAISE_VOLTAGE;
  public static final LoggedTunableNumber LOWER_VOLTAGE;
  public static final LoggedTunableNumber UNLATCH_VOLTAGE;
  public static final LoggedTunableNumber WAIT_TIME_UNLATCH;

  static {
    CLIMB_HEIGHT_METERS = new LoggedTunableNumber("Climber/Amp Speed");
    RETRACT_HEIGHT_METERS = new LoggedTunableNumber("Climber/Stow Height");

    RAISE_VOLTAGE = new LoggedTunableNumber("Climber/Raise Voltage");
    LOWER_VOLTAGE = new LoggedTunableNumber("Climber/Lower Voltage");
    UNLATCH_VOLTAGE = new LoggedTunableNumber("Climber/Unlatch Voltage");

    WAIT_TIME_UNLATCH = new LoggedTunableNumber("Time until arm unlatched");

    switch (Constants.ROBOT) {
      case ROBOT_KRAKEN_X60:
      case ROBOT_SIM:
      default:
        CAN_ID = 1;
        CURRENT_LIMIT = 40.0;
        CLIMB_MOTOR = DCMotor.getKrakenX60(1);
        GEAR_RATIO = 1.0;
        CARRIAGE_MASS = 0.0;
        MAX_HEIGHT = 1;
        MIN_HEIGHT = 0.0;
        SIMULATE_GRAVITY = false;
        STARTING_HEIGHT = 0.0;
        DRUM_RADIUS = Units.inchesToMeters(1.25);
        MOTOR_CONFIG = DCMotor.getKrakenX60(1);
        break;
    }
  }
}
