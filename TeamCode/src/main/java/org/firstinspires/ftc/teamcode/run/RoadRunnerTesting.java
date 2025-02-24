package org.firstinspires.ftc.teamcode.run;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.run.rrSubClass.intakeClaw;
import org.firstinspires.ftc.teamcode.run.rrSubClass.OuttakeSubClass;
import org.firstinspires.ftc.teamcode.run.rrSubClass.IntakeSubClass;

@Autonomous(name = "RoadRunnerTesting", group = "Autonomous")
public class RoadRunnerTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(90));
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);
        intakeClaw intakeClaw = new intakeClaw(hardwareMap);
        OuttakeSubClass outtake = new OuttakeSubClass(hardwareMap);
        IntakeSubClass intake = new IntakeSubClass(hardwareMap);

        waitForStart();

        if(isStopRequested()) return;

        while (opModeIsActive()) {

        Actions.runBlocking(
                drive.actionBuilder(beginPose)

//                        .afterTime(1, intakeClaw.ClawOpen())
//                        .strafeTo(new Vector2d(10, 10))
//                        .waitSeconds(.5)
//                        .afterTime(1, intakeClaw.ClawClose())
//                        .strafeTo(new Vector2d(0, 0))
//                        .waitSeconds(.5)

                        .afterTime(0, outtake.resetOuttake())
                        .afterTime(2, outtake.prepareSpecimen())
                        .afterTime(4, outtake.placeSpecimen())

                        //Intake pick up test

                        /*.afterTime(1, intake.IntakeHover())
                        .strafeTo(new Vector2d(0, 5))
                        .afterTime(2, intake.IntakeLower())
                        .strafeTo(new Vector2d(0, 0))
                        .afterTime(3, intake.IntakeClawClose())
                        .strafeTo(new Vector2d(0, 5))
                        .afterTime(2, intake.IntakeUp())
                        .strafeTo(new Vector2d(0, 0)) */

                        //Intake-Outtake transition
                        /*
                        .afterTime(0, outtake.takeFromIntake())
                        .afterTime(1, intake.IntakeUp())
                        .afterTime(1, outtake.closeOuttakeClaw())
                        */
                        //.afterTime(1, outtake.prepareSpecimen())

                        //Specimen full cycle test
                        /*
                        .afterTime(0, outtake.resetOuttake())
                        .afterTime(2, outtake.prepareSpecimen())
                        .afterTime(5, outtake.placeSpecimen())
                        .afterTime(1, outtake.resetOuttake())

                         */
                        //Sample full cycle test
                        /*
                        .afterTime(0, intake.IntakeHover())
                        .afterTime(1, outtake.takeFromIntake())
                        .afterTime(1, intake.IntakeLower())
                        .afterTime(1, intake.IntakeClawClose())
                        .afterTime(1, intake.IntakeUp())
                        .afterTime(1, outtake.closeOuttakeClaw())
                        .afterTime(1, intake.IntakeHover())
                        .afterTime(1, outtake.prepareSpecimen())
                        .afterTime(5, outtake.placeSpecimen())
                        .afterTime(4, outtake.resetOuttake())
                        */







                        .build());
    }
}
}
