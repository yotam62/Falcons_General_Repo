package frc.robot;

//import frc.robot.lib.BLine*;

import org.wpilib.command2.Command;
import org.wpilib.command2.Commands;
import org.wpilib.command2.button.CommandXboxController;
import frc.robot.Drive.*;
import frc.robot.Drive.DriveCommands.*;
import frc.robot.Constants.SwerveConstants;

public class RobotContainer {
  public final Drive drive;

  private final CommandXboxController controller = new CommandXboxController(0);

  public RobotContainer() {
    if (Robot.isReal()) {
      drive = new Drive(
          new ModuleReal(SwerveConstants.Mod0.constants, 0),  // fl
          new ModuleReal(SwerveConstants.Mod1.constants, 1),  // bl
          new ModuleReal(SwerveConstants.Mod2.constants, 2),  // br
          new ModuleReal(SwerveConstants.Mod3.constants, 3)); // fr
    } else {
      drive = new Drive(
          new ModuleSim(0, SwerveConstants.SwerveModuleConstants),
          new ModuleSim(1, SwerveConstants.SwerveModuleConstants),
          new ModuleSim(2, SwerveConstants.SwerveModuleConstants),
          new ModuleSim(3, SwerveConstants.SwerveModuleConstants));
    }
    configureBindings();
  }

  private void configureBindings() {
    drive.setDefaultCommand(
        new DriveCommand(
            () -> -controller.getLeftX(),
            () -> -controller.getLeftY(),
            () -> -controller.getRightX(),
            drive,
            controller));

    // controller.x().onTrue(FeedforwardCharacterization.feedforwardCommand(drive, controller));
    // controller.start().onTrue(
    //     Commands.runOnce(() -> drive.setPose(
    //         new Pose2d(
    //             drive.SwervePoseEstimator.getEstimatedPosition().getTranslation(),
    //             MatchState.getAlliance().get().equals(Alliance.BLUE)
    //                 ? Rotation2d.ZERO
    //                 : Rotation2d.fromDegrees(180))),
    //         drive));

    controller.a().whileTrue(
        new AutoAlignHubCommand(
            drive,
            controller.getLeftX(),
            controller.getLeftY(),
            controller.getRightX(),
            controller));
  }

  public Command getAutonomousCommand() {
    return Commands.none();
  }
}