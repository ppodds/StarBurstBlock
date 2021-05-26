package org.ppodds.core.game.tetromino;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ppodds.core.ResourceManager;
import org.ppodds.core.game.Position;
import org.ppodds.core.game.Tetris;
import org.ppodds.core.game.ui.GamePane;
import org.ppodds.core.game.ui.Hint;
import org.ppodds.core.game.ui.HintPane;
import org.ppodds.core.util.Random;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class Tetromino {

    /**
     * Pane[] 用來存放一整個 Tetromino 裡面有的 4 個 Pane
     * 每個對應的 Pane 對 center 的相對座標會被記錄在 Position[] 對應的index中
     */
    protected Pane[] blocks = new Pane[4];
    protected Position[] blocksPos = new Position[4];
    /**
     * 紀錄現在 Tetromino 的方向
     */
    protected TetrominoState state;
    /**
     * 用來取得當前遊戲狀態用
     */
    protected Tetris game;

    /**
     * 碰撞計算中心位置
     * 若碰撞計算中心位於格線上，則以其左上角格子做基準
     */
    protected Position center;
    /**
     * 用來記錄現在是否是 holding 狀態
     */
    protected boolean holding = false;
    /**
     * 用來記錄是否已進入過遊戲面板中
     */
    protected boolean hasJoinedGame = false;
    /**
     * 用來記錄是否已被 hold 過
     */
    protected boolean hasHold = false;

    /**
     * 用來記錄已經隨機產生的 Tetromino pack 順序
     */
    private static ArrayList<Character> pack;
    /**
     * 用來記錄現在產生到第幾個 Tetromino
     */
    private static int packIndex = 7;

    /**
     * 建立新方塊用
     * <p>
     * 子類別在繼承時需要在建構子中設定 blockPos 並 call updateBlockPosition
     *
     * @param game Tetris主體物件，用來取得當前遊戲狀態資料
     */
    public Tetromino(Tetris game) {
        this.game = game;
        // 一開始會在 Next 提示區塊出現，所以要設定為 提示區塊的 center
        if (this instanceof BlockI)
            this.center = new Position(1, 1);
        else
            this.center = new Position(1, 2);
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new Pane();
            blocks[i].getStylesheets().add(ResourceManager.getStyleSheet("Block").toString());
        }
    }

    /**
     * 生成隨機方塊
     *
     * @param game Tetris主體物件，用來取得當前遊戲狀態資料
     * @return Tetromino的子類別物件，可能為各種不同的Block
     */
    public static Tetromino generateRandomTetromino(Tetris game) {
        ArrayList<Character> types = new ArrayList<Character>(Arrays.asList('I', 'J', 'L', 'O', 'S', 'T', 'Z'));
        if (packIndex == 7) {
            pack = Random.shuffle(types);
            packIndex = 0;
        }
        char picked = pack.get(packIndex);
        packIndex++;
        switch (picked) {
            case 'I':
                return new BlockI(game);
            case 'J':
                return new BlockJ(game);
            case 'L':
                return new BlockL(game);
            case 'O':
                return new BlockO(game);
            case 'S':
                return new BlockS(game);
            case 'T':
                return new BlockT(game);
            case 'Z':
                return new BlockZ(game);
        }
        return null;
    }

    public Position getCenter() {
        return center;
    }

    /**
     * 讓 Tetromino 加入遊戲面板
     * 用於上一個 Tetromino 位置確定的時候
     * <p>
     * Warring: 會改變 center
     */
    public void joinGame() {
        center = new Position(4, 1);
        hasJoinedGame = true;
        for (Position blockPos : blocksPos) {
            if (game.getBlockOnBoard(center.plus(blockPos)) != null) {
                game.gameOver(false);
                return;
            }
        }
        game.getGamePane().getChildren().addAll(blocks);
        updateBlockPosition();
    }

    /**
     * 旋轉 Tetromino ，撞牆的話就停在原地
     * <p>
     * 子類別在實作內容必須維護 blockPos 和 state 的變化，以保證其他功能能正常執行
     * 旋轉時也應考慮是否會撞牆，或超出邊界
     * 結束時需 call updateBlockPosition 以在顯示盤面反映變化
     *
     * @param direction 旋轉的方向
     * @return 是否撞牆
     */
    public abstract SpinStatus spin(SpinDirection direction); // TODO T Spin 的規則 ( 有空再弄，或是等人發 PR

    /**
     * 將方塊下移一格，如果不能下移的話就會被固定在面板上
     *
     * @return 是否觸底，被固定到面板上
     */
    public boolean moveDown() {
        for (int i = 0; i < 4; i++) {
            // 觸底檢查
            if (center.y + blocksPos[i].y + 1 == Tetris.boardHeight) {
                setOnBoard();
                return true;
            }
            Pane checkBlock = game.getBlockOnBoard(center.x + blocksPos[i].x, center.y + blocksPos[i].y + 1);
            if (checkBlock != null) {
                setOnBoard();
                return true;
            }
        }
        down();
        return false;
    }

    /**
     * 將方塊左移一格，如果不能左移就停在原地
     *
     * @return 是否撞牆
     */
    public boolean moveLeft() {
        for (int i = 0; i < 4; i++) {
            // 觸左檢查
            if (center.x + blocksPos[i].x - 1 < 0) {
                return true;
            }
            Pane checkBlock = game.getBlockOnBoard(center.x + blocksPos[i].x - 1, center.y + blocksPos[i].y);
            if (checkBlock != null) {
                return true;
            }
        }
        left();
        return false;
    }

    public boolean moveRight() {
        for (int i = 0; i < 4; i++) {
            // 觸右檢查
            if (center.x + blocksPos[i].x + 1 == Tetris.boardWidth) {
                return true;
            }
            Pane checkBlock = game.getBlockOnBoard(center.x + blocksPos[i].x + 1, center.y + blocksPos[i].y);
            if (checkBlock != null) {
                return true;
            }
        }
        right();
        return false;
    }

    /**
     * hold 自己，若已經被 hold 過則無效並回傳 false
     * <p>
     * hold 完會重置位置屬性到提醒盤面的初始座標，並且移除在遊戲面板上的顯示
     * 結束後會在提示面板上顯示
     *
     * @return 是否成功被 hold
     */
    public boolean hold() {
        if (hasHold)
            return false;
        if (this instanceof BlockI)
            this.center = new Position(1, 1);
        else
            this.center = new Position(1, 2);
        game.getGamePane().getChildren().removeAll(blocks);
        GridPane hintPane = game.getHintPane();
        hintPane.getChildren().addAll(blocks);
        holding = true;
        hasHold = true;
        updateBlockPosition();
        return true;
    }

    /**
     * release 自己， release 完會重置位置屬性到最上方，並且移除在提示面板上的顯示
     * 結束後會在遊戲面板上顯示
     */
    public void release() {
        center = new Position(4, 1);
        game.getHintPane().getChildren().removeAll(blocks);
        GridPane gamePane = game.getGamePane();
        gamePane.getChildren().addAll(blocks);
        holding = false;
        updateBlockPosition();
    }

    /**
     * 將方塊下移到落地為止
     */
    public void hardDrop() {
        boolean done = false;
        while (!done)
            done = moveDown();
    }

    /**
     * 將方塊直接下移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void down() {
        center.y++;
        for (int i = 0; i < 4; i++) {
            GamePane.setRowIndexByCenterY(blocks[i], center.y + blocksPos[i].y);
        }
    }

    /**
     * 將方塊直接左移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void left() {
        center.x--;
        for (int i = 0; i < 4; i++) {
            GamePane.setColumnIndexByCenterX(blocks[i], center.x + blocksPos[i].x);
        }
    }

    /**
     * 將方塊直接右移，不做檢查，主要是為簡化多餘程式而寫
     */
    protected void right() {
        center.x++;
        for (int i = 0; i < 4; i++) {
            GamePane.setColumnIndexByCenterX(blocks[i], center.x + blocksPos[i].x);
        }
    }

    /**
     * 用來依據 spinCheck 的結果對 Position[] 做對應的修改
     * @param blocksPos 要修改的 Position[]
     * @param spinCheckResult 對應的 spinCheckResult
     */
    protected void toLeftOrRight(Position[] blocksPos, SpinStatus spinCheckResult) {
        switch (spinCheckResult) {
            case TORIGHT:
                for (int i=0;i<4;i++)
                    blocksPos[i] = new Position(blocksPos[i].x + 1, blocksPos[i].y);
                break;
            case TOLEFT:
                for (int i=0;i<4;i++)
                    blocksPos[i] = new Position(blocksPos[i].x - 1, blocksPos[i].y);
                break;
        }
    }

    /**
     * 更新方塊的當前位置，可用於在提示面板與遊戲面板的情形
     * <p>
     * Warring:
     * 此方法並不會改變 board 的值，因此僅為顯示用途，並不會作為碰撞計算依據
     * 若需要讓他被碰撞計算，應使用 setOnBoard 方法
     */
    public void updateBlockPosition() {
        if (hasJoinedGame) {
            if (!holding)
                for (int i = 0; i < blocks.length; i++) {
                    GamePane.setColumnIndexByCenterX(blocks[i], blocksPos[i].x + center.x);
                    GamePane.setRowIndexByCenterY(blocks[i], blocksPos[i].y + center.y);
                }
            else
                for (int i = 0; i < blocks.length; i++) {
                    HintPane.setColumnIndexByCenterX(blocks[i], blocksPos[i].x + center.x);
                    HintPane.setRowIndexByCenterY(blocks[i], blocksPos[i].y + center.y, Hint.HOLD);
                }
        } else {
            game.getHintPane().getChildren().addAll(blocks);
            for (int i = 0; i < blocks.length; i++) {
                HintPane.setColumnIndexByCenterX(blocks[i], blocksPos[i].x + center.x);
                HintPane.setRowIndexByCenterY(blocks[i], blocksPos[i].y + center.y, Hint.NEXT);
            }
        }
    }

    /**
     * 檢查新的 Block 位置是否會導致 Tetromino 超出邊界或是碰撞
     * 用於 spin 時的檢查
     *
     * @param newBlocksPos 旋轉過後新的 Block 相對位置
     * @param hasKicked 是否已經踢過牆
     * @return 是否會產生碰撞或超出邊界
     */
    protected SpinStatus spinCheck(Position[] newBlocksPos, boolean hasKicked) {
        for (int i = 0; i < 4; i++) {
            Position newPosition = center.plus(newBlocksPos[i]);
            // 邊界檢查
            if (newPosition.y < 0 || newPosition.y == Tetris.boardHeight || newPosition.x == Tetris.boardWidth || newPosition.x < 0) {
                if (hasKicked)
                    return SpinStatus.FAIL;
                else {
                    Position[] testBlocksPos = new Position[4];
                    // 測試右移
                    if (newPosition.x == 0) {
                        for (int j=0;j<4;j++) {
                            testBlocksPos[j] = new Position(newBlocksPos[j].x+1, newBlocksPos[j].y);
                        }
                        if (spinCheck(testBlocksPos, true) == SpinStatus.SUCCESS) {
                            return SpinStatus.TORIGHT;
                        }
                        else
                            return SpinStatus.FAIL;
                    } // 測試左移
                    else if (newPosition.x == Tetris.boardWidth) {
                        for (int j=0;j<4;j++) {
                            testBlocksPos[j] = new Position(newBlocksPos[j].x-1, newBlocksPos[j].y);
                        }
                        if (spinCheck(testBlocksPos, true) == SpinStatus.SUCCESS) {
                            return SpinStatus.TOLEFT;
                        }
                        else
                            return SpinStatus.FAIL;
                    }
                    return SpinStatus.FAIL;
                }
            }
            if (game.getBlockOnBoard(newPosition) != null)
                return SpinStatus.FAIL;
        }
        return SpinStatus.SUCCESS;
    }

    /**
     * 將方塊固定到面板上，固定後會被計算碰撞
     * 使用於方塊落底
     */
    protected void setOnBoard() {
        for (int i = 0; i < 4; i++) {
            Position newPosition = center.plus(blocksPos[i]);
            if (newPosition.y < 0) {
                game.gameOver(false);
                break;
            }
            else
                game.setBlockOnBoard(blocks[i], newPosition);
        }
        game.resetControlling();
        game.getBoss().addSkillCounter();
        game.getBoss().damage(game.eliminate(), null);
    }
    /**
     * 依照旋轉的方向改變 Tetromino 的狀態
     *
     * @param direction 旋轉方向
     */
    protected void changeState(SpinDirection direction) {
        switch (direction) {
            case CLOCKWISE:
                switch (state) {
                    case UP:
                        state = TetrominoState.RIGHT;
                        break;
                    case DOWN:
                        state = TetrominoState.LEFT;
                        break;
                    case LEFT:
                        state = TetrominoState.UP;
                        break;
                    case RIGHT:
                        state = TetrominoState.DOWN;
                        break;
                }
                break;
            case COUNTERCLOCKWISE:
                switch (state) {
                    case UP:
                        state = TetrominoState.LEFT;
                        break;
                    case DOWN:
                        state = TetrominoState.RIGHT;
                        break;
                    case LEFT:
                        state = TetrominoState.DOWN;
                        break;
                    case RIGHT:
                        state = TetrominoState.UP;
                        break;
                }
                break;
        }

    }
}


