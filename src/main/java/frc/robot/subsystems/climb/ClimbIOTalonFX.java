package frc.robot.subsystems.climb;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeIOTalonFX implements ClimbIO {

  private final StatusSignal<Double> PositionMeters;
  private final StatusSignal<Double> VelocityRotPerSec;
  private final StatusSignal<Double> AppliedVolts;
  private final StatusSignal<Double> CurrentAmps;
  private final StatusSignal<Double> TemperatureCelsius;
  
  private final VoltageOut voltageOut;

public ClimbIOTalonFX() {
  climbMotor = new TalonFX(ClimbConstant.CAN_ID);

  TalonFXConfiguration config = new TalonFXConfiguration();
    config.CurrentLimits.StatorCurrentLimit = ClimbConstants.CURRENT_LIMIT;
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    climbMotor.getConfigurator().apply(config);

    PositionMeters = climbMotor.getPosition();
    VelocityRotPerSec = climbMotor.getVelocity();
    AppliedVolts = climbMotor.getMotorVoltage();
    CurrentAmps = climbMotor.getSupplyCurrent();
    TemperatureCelsius = topMotor.getDeviceTemp();

    BaseStatusSignal.setUpdateFrequencyForAll(
        50.0,
        PositionMeters,
        VelocityMetersPerSec,
        AppliedVolts,
        CurrentAmps,
        TemperatureCelsius,
    
    climbMotor.optimizeBusUtilization();

    voltageOut = new VoltageOut(0.0);
  }

  @Override
  public void updateInputs(ClimbIOInputs inputs) {
    inputs.Position = PositionMeters.getValueAsDouble();
    inputs.VelocityRadPerSec = VelocityMetersPerSec.getValueAsDouble();
    inputs.AppliedVolts = AppliedVolts.getValueAsDouble();
    inputs.CurrentAmps = CurrentAmps.getValueAsDouble();
    inputs.TemperatureCelsius = TemperatureCelsius.getValueAsDouble();
  }

  @Override
  public void setVoltage(double volts) {
    climbMotor.setControl(voltageOut.withOutput(volts));
  }

}
