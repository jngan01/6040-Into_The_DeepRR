package org.firstinspires.ftc.teamcode.run.rrSubClass;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class OuttakeSubClass {

    private Servo outtakeArmR, outtakeArmL, outtakeClaw;
    private DcMotor vertSlideL, vertSlideR;
    private final double outtakeArmOut = .93, outtakeArmIn = .05;
    private final double outtakeClawOpen = 1, outtakeClawClose = .4;
    private TouchSensor touch;

    public OuttakeSubClass(HardwareMap hardwareMap) {

        outtakeArmR = hardwareMap.get(Servo.class, "outtake_arm_right");
        outtakeArmL = hardwareMap.get(Servo.class, "outtake_arm_left");
        outtakeArmL.setDirection(Servo.Direction.REVERSE);
        outtakeClaw = hardwareMap.get(Servo.class, "outtake_claw");
        vertSlideL = hardwareMap.get(DcMotor.class, "vertSlideL");
        vertSlideR = hardwareMap.get(DcMotor.class, "vertSlideR");
        vertSlideL.setDirection(DcMotor.Direction.FORWARD);
        vertSlideR.setDirection(DcMotor.Direction.FORWARD);


        vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vertSlideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        touch = hardwareMap.get(TouchSensor.class, "touch");

    }

    public class openOuttakeClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(outtakeClawOpen);
            outtakeArmL.setPosition(outtakeArmOut);
            outtakeArmR.setPosition(outtakeArmOut);
            return false;
        }
    }

    public class closeOuttakeClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(outtakeClawClose);
            return false;
        }
    }

    public class prepareSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(outtakeClawClose);
            outtakeArmL.setPosition(outtakeArmOut - .06);
            outtakeArmR.setPosition(outtakeArmOut - .06);



            //I'm gonna need to check the correct direction
            vertSlideL.setTargetPosition(-1118);
            vertSlideR.setTargetPosition(-1600);
            //vertSlideR.setTargetPosition(-400);
            vertSlideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            /*while(vertSlideL.isBusy()){
                vertSlideL.setPower(.8);
                vertSlideL.getCurrentPosition();
                if(vertSlideL.getCurrentPosition() < vertSlideL.getTargetPosition()){
                    vertSlideL.setPower(0);
                    return false;
                }
            }*/
            vertSlideL.setPower(.7);
            vertSlideR.setPower(.7);
            //vertSlideL.setPower(0);
            return false;
        }
    }

    public class placeSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(outtakeClawClose);
            outtakeArmL.setPosition(outtakeArmOut - .06);
            outtakeArmR.setPosition(outtakeArmOut - .06);
            //I'm gonna need to check the correct direction, basically reverse of prepareSpecimen
            vertSlideL.setTargetPosition(-300);
            vertSlideR.setTargetPosition(-480);

            vertSlideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            vertSlideL.setPower(.7);
            vertSlideR.setPower(.7);
            return false;
        }
    }

    public class resetOuttake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(outtakeClawOpen);
            outtakeArmL.setPosition(outtakeArmOut);
            outtakeArmR.setPosition(outtakeArmOut);

            while (!touch.isPressed()) {
                vertSlideR.setPower(0.7);
                vertSlideL.setPower(0.7);
            }
            vertSlideR.setPower(0);
            vertSlideL.setPower(0);
            return false;
        }
    }

    public class takeFromIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            outtakeClaw.setPosition(outtakeClawOpen);
            outtakeArmL.setPosition(outtakeArmIn);
            outtakeArmR.setPosition(outtakeArmIn);
            return false;
        }
    }

    public Action closeOuttakeClaw() {
        return new closeOuttakeClaw();
    }

    public Action openOuttakeClaw() {
        return new openOuttakeClaw();
    }

    public Action prepareSpecimen() {
        return new prepareSpecimen();
    }

    public Action placeSpecimen() {
        return new placeSpecimen();
    }

    public Action resetOuttake() {
        return new resetOuttake();
    }
    public Action takeFromIntake() {
        return new takeFromIntake();
    }
}

