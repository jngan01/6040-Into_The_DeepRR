package org.firstinspires.ftc.teamcode.run;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.run.rrSubClass.OuttakeSubClass;


@Autonomous(name = "rightSideAutoRR", group = "Autonomous")
public class rightSideAutoRR extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(17, -62.5, Math.toRadians(270));
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);
        OuttakeSubClass outtake = new OuttakeSubClass(hardwareMap);

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(
                drive.actionBuilder(beginPose)


                        //Below is the good code
                        .strafeTo(new Vector2d(4, -33))
                        .afterTime(0, outtake.closeOuttakeClaw())
                        .afterTime(0, outtake.prepareSpecimen())
                        .afterTime(2, outtake.placeSpecimen())
                        .afterTime(.75, outtake.resetOuttake())
                        .strafeTo(new Vector2d(34, -50))
                        //.waitSeconds(.5)
                        .turnTo(Math.toRadians(90))
                        //.waitSeconds(.5)
                        .strafeTo(new Vector2d(34, -58))
                        .afterTime(1, outtake.closeOuttakeClaw())
                        .afterTime(0, outtake.prepareSpecimen())

                        .strafeTo(new Vector2d(17, -50))
                        //.waitSeconds(.25)
                        .turnTo(Math.toRadians(270))
                        //.waitSeconds(.5)
                        .strafeTo(new Vector2d(2, -33))
                        .waitSeconds(.5)
                        //.strafeTo(new Vector2d(10, -40))
                        .strafeTo(new Vector2d(34, -50))
                        //.waitSeconds(.5)
                        .turnTo(Math.toRadians(90))
                        //.waitSeconds(.5)
                        .strafeTo(new Vector2d(34, -58))
                        .waitSeconds(.5)

                        .strafeTo(new Vector2d(17, -50))
                        //.waitSeconds(.25)
                        .turnTo(Math.toRadians(270))
                        //.waitSeconds(.5)
                        .strafeTo(new Vector2d(0, -33))
                        .waitSeconds(.5)
                        //.strafeTo(new Vector2d(10, -40))
                        .strafeTo(new Vector2d(45, -59))

                       /* .strafeTo(new Vector2d(6, -34))
                        .waitSeconds(.5)
                        .strafeTo(new Vector2d(20, -40))
                        .strafeTo(new Vector2d(32, -55))
                        .waitSeconds(.5)
                        .turnTo(Math.toRadians(90))
                        .waitSeconds(.5)
                        .strafeTo(new Vector2d(32, -60))
                        .waitSeconds(.5)*/

                        //.strafeTo(new Vector2d(17, -50))
                        //.waitSeconds(.25)
                        //.turnTo(Math.toRadians(270))
                        //.waitSeconds(.5)
                        //.strafeTo(new Vector2d(6, -34))



                        .build());
    }
}
