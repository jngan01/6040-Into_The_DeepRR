package org.firstinspires.ftc.teamcode.run;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class servoTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "servo");
        CRServo crServo = hardwareMap.get(CRServo.class, "crServo");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
        if (gamepad1.a) {
            servo.setPosition(1);
        } else if (gamepad1.b) {
            servo.setPosition(0);
        }
        if (gamepad1.x) {
            crServo.setPower(1);
        } else if (gamepad1.y) {
            crServo.setPower(-1);
        }
    }
}
}
