public class ClimbConstant {
  public static final int CAN_ID;
  public static final double CURRENT_LIMIT;
  public static final DCMotor CLIMB_MOTOR;

  static {
    switch (Constants.ROBOT) {
      case ROBOT_KRAKEN_X60:
      case ROBOT_SIM:
      default:
        CAN_ID = 1;
        CURRENT_LIMIT = 40.0;
        CLIMB_MOTOR = DCMotor.getKrakenX60(1);
        break;
    }
  }
}

