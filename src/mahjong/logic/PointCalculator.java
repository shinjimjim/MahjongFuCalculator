package mahjong.logic;

public class PointCalculator {
	public static int calculatePoints(int han, int fu, boolean isDealer, boolean isTsumo) {
        int basePoints;

        // 翻が高ければ上限役
        if (han >= 13) {
            basePoints = 8000; // 役満
        } else if (han >= 11) {
            basePoints = 6000; // 三倍満
        } else if (han >= 8) {
            basePoints = 4000; // 倍満
        } else if (han >= 6) {
            basePoints = 3000; // 跳満
        } else if (han == 5 || (han == 4 && fu >= 40) || (han == 3 && fu >= 70)) {
            basePoints = 2000; // 満貫
        } else {
            basePoints = fu * (int)Math.pow(2, 2 + han);
            if (basePoints > 2000) basePoints = 2000; // 満貫上限
        }

        if (isTsumo) {
            if (isDealer) {
                return roundUpToNearest100(basePoints * 2); // 親は2人から同額
            } else {
                return roundUpToNearest100((basePoints) + (basePoints * 2)); // 子は親と子から
            }
        } else {
            if (isDealer) {
                return roundUpToNearest100(basePoints * 6);
            } else {
                return roundUpToNearest100(basePoints * 4);
            }
        }
    }

    private static int roundUpToNearest100(int points) {
        return ((points + 99) / 100) * 100;
    }
}
