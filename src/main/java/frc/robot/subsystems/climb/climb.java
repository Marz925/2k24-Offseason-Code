package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class climb extends SubsystemBase {

  private final ClimbIOInputsAutoLogged inputs;
  private final ClimbIO io;

  public climb(ClimbIO io) {
    inputs = new ClimbIOInputsAutoLogged();
    this.io = io;
  }

  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("Climb", inputs);
  }

  public Command extendClimb() {

    return Commands.sequence(
        Commands.runOnce(
            () ->
                io.setVoltage(
                    ClimbConstant.UNLATCH_VOLTAGE
                        .get())), // lets the arm unlock by spinning backwards
        Commands.waitSeconds(ClimbConstant.WAIT_TIME_UNLATCH.get()), // waits until arm is unlocked
        Commands.runOnce(() -> io.setVoltage(0.0)), // stops the motor from spinning
        Commands.runOnce(
            () ->
                io.setVoltage(
                    ClimbConstant.RAISE_VOLTAGE
                        .get())), // Sets the motor to spin at a constant voltage
        Commands.waitUntil(
            () ->
                inputs.PositionMeters
                    >= ClimbConstant.CLIMB_HEIGHT_METERS
                        .get()), // tells the motor to keep spinning until the arm reaches a
        // suitable height
        Commands.runOnce(() -> io.setVoltage(0.0))); // stops the motor from spinning
  }

  public Command retractClimb() {
    return Commands.sequence(
        Commands.runOnce(
            () ->
                io.setVoltage(
                    ClimbConstant.LOWER_VOLTAGE.get())), // Sets the voltage of the motor to retract
        Commands.waitUntil(
            () ->
                inputs.PositionMeters
                    <= ClimbConstant.RETRACT_HEIGHT_METERS
                        .get()), // keeps spining the motor until the height of the climb is at the
        // retract height
        Commands.runOnce(() -> io.setVoltage(0.0)) // stops motor
        );
  }
}
