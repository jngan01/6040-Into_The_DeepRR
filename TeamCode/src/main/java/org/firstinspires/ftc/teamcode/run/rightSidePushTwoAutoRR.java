package org.firstinspires.ftc.teamcode.run;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.run.rrSubClass.OuttakeSubClass;


@Autonomous(name = "rightSidePushTwoAutoRR", group = "Autonomous")
public class rightSidePushTwoAutoRR extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(17, -62.5, Math.toRadians(270));
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, beginPose);
        OuttakeSubClass outtake = new OuttakeSubClass(hardwareMap);

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(
                drive.actionBuilder(beginPose)

                        .afterTime(0, outtake.closeOuttakeClaw())
                        .afterTime(0, outtake.prepareSpecimen())
                        .strafeTo(new Vector2d(4, -29.5))
                        .afterTime(0, outtake.placeSpecimen())
                        .afterTime(1, outtake.resetOuttake())
                        .waitSeconds(.75)
                        .strafeTo(new Vector2d(34, -36))


                        .turnTo(Math.toRadians(90))

                        //Pushing samples
                        .strafeTo(new Vector2d(34, -15))
                        .strafeTo(new Vector2d(49, -13))
                        .strafeTo(new Vector2d(49, -58))
                        .strafeTo(new Vector2d(49, -13))
                        .strafeTo(new Vector2d(58, -13))
                        .strafeTo(new Vector2d(58, -58))

                        .strafeTo(new Vector2d(47.5, -52))
                        .strafeTo(new Vector2d(47.5, -58))
                        .afterTime(1, outtake.closeOuttakeClaw())
                        .afterTime(0, outtake.prepareSpecimen())



                        .waitSeconds(.5)

                        .strafeTo(new Vector2d(17, -50))
                        //.waitSeconds(.25)
                        .turnTo(Math.toRadians(270))
                        //.waitSeconds(.5)
                        .strafeTo(new Vector2d(2, -33))
                        .afterTime(.75, outtake.placeSpecimen())
                        .afterTime(.75, outtake.resetOuttake())
                        //.strafeTo(new Vector2d(10, -40))
                        .strafeTo(new Vector2d(60, -50))



                        .build());
    }
}
