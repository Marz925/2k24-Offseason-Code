package frc.robot.subsystems.climb;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.util.Units;

public class ClimbIOTalonFX implements ClimbIO {

  private final TalonFX climbMotor;
  private final StatusSignal<Double> PositionMeters;
  private final StatusSignal<Double> VelocityMetersPerSec;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> CurrentAmps;
  private final StatusSignal<Double> TemperatureCelsius;

  private final VoltageOut voltageOut;

  public ClimbIOTalonFX() {

    climbMotor =
        new TalonFX(
            ClimbConstant.CAN_ID); // Asigns the climbMotor to the motor with the relevant can id

    TalonFXConfiguration config = new TalonFXConfiguration();
    config.CurrentLimits.SupplyCurrentLimit = ClimbConstant.CURRENT_LIMIT;
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    config.SoftwareLimitSwitch.ForwardSoftLimitThreshold =
        Units.rotationsToRadians(ClimbConstant.CLIMB_HEIGHT_METERS.get());

    config.SoftwareLimitSwitch.ReverseSoftLimitThreshold =
        Units.rotationsToRadians(ClimbConstant.RETRACT_HEIGHT_METERS.get());

    config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
    config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;

    climbMotor.getConfigurator().apply(config);

    PositionMeters = climbMotor.getPosition();
    VelocityMetersPerSec = climbMotor.getVelocity();
    AppliedVolts = climbMotor.getMotorVoltage();
    CurrentAmps = climbMotor.getSupplyCurrent();
    TemperatureCelsius = climbMotor.getDeviceTemp();

    BaseStatusSignal.setUpdateFrequencyForAll( // sets how often the variables are updated
        50.0, PositionMeters, VelocityMetersPerSec, AppliedVolts, CurrentAmps, TemperatureCelsius);

    climbMotor.optimizeBusUtilization();

    voltageOut = new VoltageOut(0.0);
  }

  @Override
  public void updateInputs(
      ClimbIOInputs inputs) { // When called it updates the inputs with the new values.
    inputs.PositionMeters = // Finding the position of the arm using the amount of string unwound
        PositionMeters.getValueAsDouble()
            / ClimbConstant
                .GEAR_RATIO // Converting rotations of motors to the rotations of the output shaft
            // of the gears
            * Math.PI
            * ClimbConstant.DRUM_RADIUS
            * 2; // Circumference of the pulley to find how much string is unwrapped in each
    // rotation

    inputs.VelocityMetersPerSec = // Finding the velocity of the arm assuming the rate of the arm
        // unwinding it slower than the rate of the spring pushing the
        // arm
        VelocityMetersPerSec.getValueAsDouble()
            / ClimbConstant
                .GEAR_RATIO // Converting rotations of motors to the rotations of the output shaft
            // of the gears
            * Math.PI
            * ClimbConstant.DRUM_RADIUS
            * 2; // Circumference of the pulley to find the speed of the string being unwrapped

    inputs.AppliedVolts = AppliedVolts.getValueAsDouble(); // Finds the amount of volts
    inputs.CurrentAmps = CurrentAmps.getValueAsDouble(); // Finds the amount of amps
    inputs.TemperatureCelsius = TemperatureCelsius.getValueAsDouble(); // Finds the temperature
  }

  @Override
  public void setVoltage(
      double volts) { // When called it sets the voltages supplied to the motor to the set value
    climbMotor.setControl(voltageOut.withOutput(volts));
  }
}
