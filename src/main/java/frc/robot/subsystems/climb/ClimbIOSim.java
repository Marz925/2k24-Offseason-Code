package frc.robot.subsystems.climb;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import frc.robot.Constants;

public class ClimbIOSim implements ClimbIO {

  private final ElevatorSim climbMotorSim;
  private double AppliedVolts;

  public ClimbIOSim() {
    climbMotorSim =
        new ElevatorSim(
            ClimbConstant.MOTOR_CONFIG,
            ClimbConstant.GEAR_RATIO,
            ClimbConstant.CARRIAGE_MASS,
            ClimbConstant.DRUM_RADIUS,
            ClimbConstant.MIN_HEIGHT,
            ClimbConstant.MAX_HEIGHT,
            ClimbConstant.SIMULATE_GRAVITY,
            ClimbConstant.STARTING_HEIGHT);

    AppliedVolts = 0.0;
  }

  @Override
  public void updateInputs(ClimbIOInputs inputs) {
    climbMotorSim.update(Constants.LOOP_PERIOD_SECS);

    inputs.PositionMeters = climbMotorSim.getPositionMeters();
    inputs.VelocityMetersPerSec = climbMotorSim.getVelocityMetersPerSecond();
    inputs.AppliedVolts = AppliedVolts;
    inputs.CurrentAmps = climbMotorSim.getCurrentDrawAmps();
  }

  @Override
  public void setVoltage(double volts) {
    AppliedVolts = MathUtil.clamp(volts, -12, 12);
    climbMotorSim.setInputVoltage(AppliedVolts);
  }
}
