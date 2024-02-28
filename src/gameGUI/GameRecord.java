package gameGUI;

import 首页.Home;

public class GameRecord {

	int numOfLevel; // 关卡数
	int difficulty; // 难度1~7
	int numOfGold; // 当前金币数量
	int numOfTollPassed; // 通过的收费站数量
	private int numOfToll; // 初始收费站
	private int numOfStep; // 步长
	boolean isNormalMode;
	private int seconds; // 秒数
	private static Home homeFrame; // 首页面板
	private static final int WIDTH = 12;
	private static final int HEIGHT = 8;

	public GameRecord(boolean isNormalMode, int numOfLevel) {
		this.isNormalMode = isNormalMode;
		this.numOfLevel = numOfLevel;
		numOfStep = 0;
		numOfToll = getNumOfToll();
		difficulty = getDifficulty();
		seconds = -1;
		numOfGold = getInitialGold();
	}

	public Home getHomeFrame() {
		return homeFrame;
	}

	public void setHomeFrame(Home homeFrame) {
		GameRecord.homeFrame = homeFrame;
	}

	public long getSeed() {
		if (isNormalMode)
			return numOfLevel*23+99-difficulty;
		return 0;
	}

	int getNumOfToll() { // 获取收费站数
		int n = difficulty-1;
		if (n > 3)
			return 3;
		return n;
	}

	int getInitialGold() { // 获取关卡初始金币数
		// 根据收费站数
		if (numOfToll>0)
			return numOfToll*5-1;
		return 4;
	}

	int getWidth() { // 获取迷宫宽（左到右）
		return (WIDTH + difficulty * 2) * 2 - 1;
	}

	int getHeight() { // 获取迷宫高（上到下）
		return 2 * HEIGHT + difficulty * 2 + 5;
	}

	int getDifficulty() { // 根据模式和关卡数获取游戏难度
		if (isNormalMode) {
			int d = (numOfLevel+1)/2;
			if (d < 8)
				return d;
		}else {
			int d = (numOfLevel+2)/3;
			if (d < 8)
				return d;
		}
		return 7;
	}

	public int getNumOfStep() { // 获取步长
		return numOfStep;
	}

	public void addStep() { // 步数+1
		numOfStep++;
	}
	public void reduceStep() { // 步数-1
		numOfStep--;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	int getScore() {
  int score; // 获取分数
		int good = numOfTollPassed*71 + numOfGold*10 + numOfGold;
		int bad = numOfStep+(seconds/20)*5;
		if (numOfStep > 60) {
			bad = 60+(numOfStep-65)/2+(seconds/24)*5;
			if (numOfStep > 100)
				bad = 90+(numOfStep-105)/2+(seconds/22)*4;
			if (numOfStep > 150)
				bad = 130 + (seconds/18)*4;
		}
		score = good - bad + 12;
		if (numOfTollPassed>0 && numOfGold<=getInitialGold() && score<22){
			score = score + (5-Math.abs(numOfGold-getInitialGold()))*3;
		}
		if ((numOfTollPassed==0 && numOfGold<8) &&  (score < 20)) {
				if (score < 5)
					score += 14;
				else
					score += 12;
			}
		if (score > 70)
			score -= 12;
		return score;
	}

}

