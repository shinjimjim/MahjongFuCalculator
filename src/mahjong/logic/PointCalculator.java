package mahjong.logic;

public class PointCalculator {
	//han：翻数（役による得点）、fu：符数（副得点）、isDealer：親かどうか（true = 親）、isTsumo：ツモあがりかどうか
	public static int calculatePoints(int han, int fu, boolean isDealer, boolean isTsumo) {
        int basePoints;

        // 翻数が高い場合（6翻以上）には「跳満」「倍満」「三倍満」「役満」の定額点が使われます（符に関係ない）。
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
            basePoints = fu * (int)Math.pow(2, 2 + han); //基本点=符×2の(2+翻)乗
            if (basePoints > 2000) basePoints = 2000; // 計算結果が 2000 を超える場合 → 満貫扱いにする（2000点固定）
        }

        //親か子か、ツモかロンかで最終点を計算
        if (isTsumo) {
            if (isDealer) {
                return roundUpToNearest100(basePoints * 2); //親がツモ：親は子2人から同額支払い
            } else {
                return roundUpToNearest100((basePoints) + (basePoints * 2)); //子がツモ：子は親と子から1:2の合計支払い
            }
        } else {
            if (isDealer) {
                return roundUpToNearest100(basePoints * 6); //親がロン：放銃者が全額負担
            } else {
                return roundUpToNearest100(basePoints * 4); //子がロン：放銃者が全額負担
            }
        }
    }

	//100点単位に切り上げ（麻雀の点数ルールによる）
    private static int roundUpToNearest100(int points) {
        return ((points + 99) / 100) * 100;
    }
}
