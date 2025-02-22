package org.firstinspires.ftc.teamcode.run;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.run.rrSubClass.IntakeSubClass;
import org.firstinspires.ftc.teamcode.run.rrSubClass.OuttakeSubClass;
import org.firstinspires.ftc.teamcode.run.rrSubClass.intakeClaw;

@Autonomous(name = "leftSideAutoRR", group = "Autonomous")
public class leftSideAutoRR extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(-17.5, -62.5, Math.toRadians(270));
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);
        intakeClaw intakeClaw = new intakeClaw(hardwareMap);
        OuttakeSubClass outtake = new OuttakeSubClass(hardwareMap);
        IntakeSubClass intake = new IntakeSubClass(hardwareMap);

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        //Goto submersible
                        .strafeTo(new Vector2d(-6, -33))
                        .afterTime(0, outtake.closeOuttakeClaw())
                        .afterTime(0, outtake.prepareSpecimen())
                        .afterTime(2, outtake.placeSpecimen())
                        .afterTime(.75, outtake.resetOuttake())
                        //.waitSeconds(.5)
                        //Position for picking up first sample (going right to left)
                        .strafeTo(new Vector2d(-48, -40))
                        .turnTo(Math.toRadians(90))
                        .afterTime(0, intake.IntakeHover())
                        .strafeTo(new Vector2d(-48, -33))
                        .afterTime(1, intake.IntakeLower())
                        .afterTime(.25, intake.IntakeClawClose())
                        .afterTime(.2, intake.IntakeUp())

                        //Goto basket and position
                        .strafeTo(new Vector2d(-55, -55))
                        .turnTo(Math.toRadians(45))
                        .afterTime(0, outtake.takeFromIntake())
                        .afterTime(.5, outtake.closeOuttakeClaw())
                        .afterTime(.25, intake.IntakeClawOpen())

                        .strafeTo(new Vector2d(-58, -58))
                        .waitSeconds(.5)
                        // 2nd sample positioning
                        .strafeTo(new Vector2d(-56.5, -40))
                        .turnTo(Math.toRadians(90))
                        .strafeTo(new Vector2d(-56.5, -33))
                        .waitSeconds(.5)
                        //Basket
                        .strafeTo(new Vector2d(-55, -55))
                        .turnTo(Math.toRadians(45))
                        .strafeTo(new Vector2d(-57, -57))
                        .waitSeconds(.5)





                        .build());
    }
}
