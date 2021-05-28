package org.ppodds.core.game.tetromino;

public class SpinStatus {
    private int step;
    private SpinCheckStatus spinCheckStatus;

    public SpinStatus(SpinCheckStatus status, int step) {
        this.spinCheckStatus = status;
        this.step = step;
    }

    public SpinStatus(SpinCheckStatus status) {
        this.spinCheckStatus = status;
        this.step = 0;
    }

    public int getStep() {
        return step;
    }

    public SpinCheckStatus getSpinCheckStatus() {
        return spinCheckStatus;
    }
}
