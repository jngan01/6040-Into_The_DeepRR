package org.firstinspires.ftc.teamcode.run;
import com.acmerobotics.roadrunner.Line;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@TeleOp
public class Qual2TeleOp extends LinearOpMode {
    private DcMotor lf, lb, rf, rb, SlidesH, vertSlideL, vertSlideR;
    private Servo intakeClaw, intakeRotator, intakeArmR, intakeArmL, outtakeArmR, outtakeArmL, outtakeClaw;
    private TouchSensor touch;

    private ExecutorService executorService;

    // Intake Positions
    private final double intakeClawOpen = 1, intakeClawClose = 0;
    private final double intakeRotatorUp = 1, intakeRotatorDown = .45, intakeRotatorHover = .8;
    private final double intakeArmUp = .6, intakeArmDown = 0;

    // Outtake positions
    private final double outtakeArmOut = .93, outtakeArmIn = .05;
    private final double outtakeClawOpen = 1, outtakeClawClose = .79;

    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware
        lf = hardwareMap.get(DcMotor.class, "left_front");
        lb = hardwareMap.get(DcMotor.class, "left_back");
        rf = hardwareMap.get(DcMotor.class, "right_front");
        rb = hardwareMap.get(DcMotor.class, "right_back");

        lf.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.REVERSE);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeClaw = hardwareMap.get(Servo.class, "intake_claw");
        intakeRotator = hardwareMap.get(Servo.class, "intake_rotator");
        intakeArmR = hardwareMap.get(Servo.class, "intake_arm_right");
        intakeArmL = hardwareMap.get(Servo.class, "intake_arm_left");
        intakeArmL.setDirection(Servo.Direction.REVERSE);

        SlidesH = hardwareMap.get(DcMotor.class, "SlidesH");

        outtakeArmR = hardwareMap.get(Servo.class, "outtake_arm_right");
        outtakeArmL = hardwareMap.get(Servo.class, "outtake_arm_left");
        outtakeArmL.setDirection(Servo.Direction.REVERSE);
        outtakeClaw = hardwareMap.get(Servo.class, "outtake_claw");

        vertSlideL = hardwareMap.get(DcMotor.class, "vertSlideL");
        vertSlideR = hardwareMap.get(DcMotor.class, "vertSlideR");

        touch = hardwareMap.get(TouchSensor.class, "touch");

        vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            double speedMultiplier = gamepad1.left_bumper ? 0.3 : gamepad1.right_bumper ? 1.0 : 0.7;

            lf.setPower(speedMultiplier * frontLeftPower);
            lb.setPower(speedMultiplier * backLeftPower);
            rf.setPower(speedMultiplier * frontRightPower);
            rb.setPower(speedMultiplier * backRightPower);

            //Intake
            if (gamepad2.a) {
                intakeClaw.setPosition(intakeClawOpen);
                intakeRotator.setPosition(intakeRotatorDown);
            } else if (gamepad2.b) {
                intakeClaw.setPosition(intakeClawClose);
                intakeRotator.setPosition(intakeRotatorHover);
            }

            SlidesH.setPower(gamepad2.left_stick_y * 0.5);

            //Outtake
            if (touch.isPressed()) {
                telemetry.addLine("Touch");
                vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                if (gamepad2.right_stick_y < 0) {
                    vertSlideL.setPower(gamepad2.right_stick_y * 0.5);
                    vertSlideR.setPower(gamepad2.right_stick_y * 0.5);
                } else {
                    stopVerticalSlides();
                }
            } else {
                telemetry.addLine("No touch");
                vertSlideR.setPower(gamepad2.right_stick_y * 0.5);
                vertSlideL.setPower(gamepad2.right_stick_y * 0.5);
            }

            if (gamepad2.left_bumper) {
                outtakeArmR.setPosition(outtakeArmIn);
                outtakeArmL.setPosition(outtakeArmIn);
                outtakeClaw.setPosition(outtakeClawOpen);
                intakeArmL.setPosition(intakeArmUp);
                intakeArmR.setPosition(intakeArmUp);
                intakeRotator.setPosition(intakeRotatorUp);

            } else if (gamepad2.right_bumper) {
                outtakeClaw.setPosition(outtakeClawClose);
                double outtakeReady = outtakeClaw.getPosition();
                if(outtakeReady == outtakeClawClose) {
                    intakeClaw.setPosition(intakeClawOpen);
                    intakeArmL.setPosition(intakeArmDown);
                    intakeArmR.setPosition(intakeArmDown);
                    outtakeArmR.setPosition(outtakeArmOut-.06);
                    outtakeArmL.setPosition(outtakeArmOut-.06);
                }

            }
            if(gamepad2.x){
                outtakeClaw.setPosition(outtakeClawClose);
            }
            //Driver 1 outtake claw controls
            if (gamepad1.a){
                outtakeClaw.setPosition(outtakeClawOpen);
            }
            if (gamepad1.b){
                outtakeClaw.setPosition(outtakeClawClose);
            }
            if(gamepad1.left_bumper){
                outtakeArmR.setPosition(outtakeArmOut);
                outtakeArmL.setPosition(outtakeArmOut);
            }
            if(gamepad1.right_bumper){
                outtakeArmR.setPosition(outtakeArmOut-.32);
                outtakeArmL.setPosition(outtakeArmOut-.32);
            }
            if(gamepad1.y){
                outtakeArmR.setPosition(outtakeArmIn);
                outtakeArmL.setPosition(outtakeArmIn);
            }

            if (gamepad2.y) {
                outtakeClaw.setPosition(outtakeClawOpen);
                outtakeArmR.setPosition(outtakeArmIn);
                outtakeArmL.setPosition(outtakeArmIn);
                outtakeClaw.setPosition(outtakeClawOpen);

                while (!touch.isPressed()) {
                    vertSlideR.setPower(0.4);
                    vertSlideL.setPower(0.4);
                }
                stopVerticalSlides();
            }
            telemetry.addData("Lift Pos", -vertSlideL.getCurrentPosition());
            telemetry.update();
        }

    }
    private void stopVerticalSlides() {
        vertSlideL.setPower(0);
        vertSlideR.setPower(0);
    }
}
