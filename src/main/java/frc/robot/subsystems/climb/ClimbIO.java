package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.AutoLog;

public interface ClimbIO {

  @AutoLog
  public static class ClimbIOInputs {
    public double PositionMeters = 0.0;
    public double VelocityMetersPerSec = 0.0;
    public double AppliedVolts = 0.0;
    public double CurrentAmps = 0.0;
    public double TemperatureCelsius = 0.0;
  }

  public default void updateInputs(ClimbIOInputs inputs) {}

  public default void setVoltage(double volts) {}
}
