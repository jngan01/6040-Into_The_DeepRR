package org.firstinspires.ftc.teamcode.run;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@TeleOp
public class robodogsTeleOp extends LinearOpMode {

    private DcMotor lf, lb, rf, rb, SlidesH, vertSlideL, vertSlideR;
    private Servo intakeClaw, intakeRotator, intakeArmR, intakeArmL, outtakeArmR, outtakeArmL, outtakeClaw;
    private TouchSensor touch;

    private ExecutorService executorService;

    // Intake Positions
    private final double intakeClawOpen = 1, intakeClawClose = 0;
    private final double intakeRotatorUp = 1, intakeRotatorDown = .2;
    private final double intakeArmUp = 1, intakeArmDown = 0;

    // Outtake positions
    private final double outtakeArmOut = 1, outtakeArmIn = .18;
    private final double outtakeClawOpen = 1, outtakeClawClose = 0;

    @Override
    public void runOpMode() {
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

        outtakeArmR = hardwareMap.get(Servo.class, "outtake_arm_right");
        outtakeArmL = hardwareMap.get(Servo.class, "outtake_arm_left");
        outtakeArmL.setDirection(Servo.Direction.REVERSE);
        outtakeClaw = hardwareMap.get(Servo.class, "outtake_claw");

        SlidesH = hardwareMap.get(DcMotor.class, "SlidesH");

        vertSlideL = hardwareMap.get(DcMotor.class, "vertSlideL");
        vertSlideR = hardwareMap.get(DcMotor.class, "vertSlideR");

        touch = hardwareMap.get(TouchSensor.class, "touch");

        vertSlideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        executorService = Executors.newSingleThreadExecutor();

        intakeClaw.setPosition(intakeClawClose);
        intakeArmL.setPosition(intakeArmUp);
        intakeArmR.setPosition(intakeArmUp);

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            driveControl();
            intakeControl();
            outtakeControl();
            telemetry.update();
        }

        executorService.shutdown();
    }

    // Drive system
    private void driveControl() {
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
    }

    // Intake system
    private void intakeControl() {
        if (gamepad2.a) {
            intakeClaw.setPosition(intakeClawOpen);
            intakeRotator.setPosition(intakeRotatorDown);
        } else if (gamepad2.b) {
            intakeClaw.setPosition(intakeClawClose);
            runDelayedTask(() -> intakeRotator.setPosition(intakeRotatorUp), 100);
        }

        SlidesH.setPower(gamepad2.left_stick_y * 0.5);
    }

    // Outtake system
    private void outtakeControl() {
        if (touch.isPressed()) {
            telemetry.addLine("Touch");
            if (gamepad2.right_stick_y < 0) {
                vertSlideL.setPower(gamepad2.right_stick_y * 0.25);
                vertSlideR.setPower(gamepad2.right_stick_y * 0.25);
            } else {
                stopVerticalSlides();
            }
        } else {
            telemetry.addLine("No touch");
            vertSlideR.setPower(gamepad2.right_stick_y * 0.25);
            vertSlideL.setPower(gamepad2.right_stick_y * 0.25);
        }

        if (gamepad2.left_bumper) {
            outtakeArmR.setPosition(outtakeArmIn);
            outtakeArmL.setPosition(outtakeArmIn);
            outtakeClaw.setPosition(outtakeClawOpen);
            runDelayedTask(() -> {
                intakeArmL.setPosition(intakeArmUp);
                intakeArmR.setPosition(intakeArmUp);
            }, 250);
        } else if (gamepad2.right_bumper) {
            outtakeClaw.setPosition(outtakeClawClose);
            intakeArmL.setPosition(intakeArmDown);
            intakeArmR.setPosition(intakeArmDown);
            runDelayedTask(() -> {
                outtakeArmR.setPosition(outtakeArmOut);
                outtakeArmL.setPosition(outtakeArmOut);
            }, 200);
        }

        if (gamepad2.y) {
            outtakeClaw.setPosition(outtakeClawOpen);
            outtakeArmR.setPosition(outtakeArmIn);
            outtakeArmL.setPosition(outtakeArmIn);
            runDelayedTask(() -> outtakeClaw.setPosition(outtakeClawOpen), 200);

            while (!touch.isPressed()) {
                vertSlideR.setPower(0.1);
                vertSlideL.setPower(0.1);
            }
            stopVerticalSlides();
        }
    }

    // Stop vertical slides
    private void stopVerticalSlides() {
        vertSlideL.setPower(0);
        vertSlideR.setPower(0);
    }

    // Runs a task after a delay **without blocking** the main thread
    private void runDelayedTask(Runnable task, long delayMillis) {
        executorService.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(delayMillis);
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}
