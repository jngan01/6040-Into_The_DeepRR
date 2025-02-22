package org.firstinspires.ftc.teamcode.run.rrSubClass;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
public class IntakeSubClass {
    private Servo intakeClaw, intakeRotator, intakeArmR, intakeArmL;

    private final double intakeClawOpen = 1, intakeClawClose = 0;
    private final double intakeRotatorUp = 1, intakeRotatorDown = .45, intakeRotatorHover = .8;
    private final double intakeArmUp = .6, intakeArmDown = 0;

    public IntakeSubClass(HardwareMap hardwareMap){
        intakeClaw = hardwareMap.get(Servo.class, "intake_claw");
        intakeRotator = hardwareMap.get(Servo.class, "intake_rotator");
        intakeArmR = hardwareMap.get(Servo.class, "intake_arm_right");
        intakeArmL = hardwareMap.get(Servo.class, "intake_arm_left");
        intakeArmL.setDirection(Servo.Direction.REVERSE);

    }

    public class IntakeHover implements Action {
        @Override

        public boolean run(@NonNull TelemetryPacket packet){
           intakeClaw.setPosition(intakeClawOpen);
           intakeRotator.setPosition(intakeRotatorHover);
           intakeArmL.setPosition(intakeArmDown);
           intakeArmR.setPosition(intakeArmDown);
            return false;
        }
    }
    public class IntakeLower implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet){
            intakeClaw.setPosition(intakeClawOpen);
            intakeRotator.setPosition(intakeRotatorDown);
            intakeArmL.setPosition(intakeArmDown);
            intakeArmR.setPosition(intakeArmDown);
            return false;
        }
    }
    public class IntakeUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet){
            intakeRotator.setPosition(intakeRotatorUp);
            intakeArmL.setPosition(intakeArmUp);
            intakeArmR.setPosition(intakeArmUp);
            return false;
        }
    }
    public class IntakeClawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet){
            intakeClaw.setPosition(intakeClawClose);
            return false;
        }
    }
    public class IntakeClawOpen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet){
            intakeClaw.setPosition(intakeClawOpen);
            return false;
        }
    }

    public Action IntakeHover(){
        return new IntakeHover();
    }

    public Action IntakeLower(){
        return new IntakeLower();
    }
    public Action IntakeClawClose(){
        return new IntakeClawClose();
    }
    public Action IntakeClawOpen(){
        return new IntakeClawOpen();
    }
    public Action IntakeUp(){
        return new IntakeUp();
    }



}