package org.firstinspires.ftc.teamcode.run.rrSubClass;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class intakeClaw {



    public Servo claw;

    public intakeClaw(HardwareMap hardwareMap){

        claw = hardwareMap.get(Servo.class, ("intake_claw"));
        claw.setDirection(Servo.Direction.FORWARD);
    }

    public class clawOpen implements Action {
        @Override

        public boolean run(@NonNull TelemetryPacket packet){
            claw.setPosition(1);
            return false;
        }
    }
    public class clawClose implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet){
            claw.setPosition(0);
            return false;
        }
    }

    public Action ClawOpen(){
        return new clawOpen();
    }
    public Action ClawClose(){
        return new clawClose();
    }
}
