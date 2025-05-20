package mahjong.logic;

import java.util.ArrayList;
import java.util.List;

import mahjong.model.Meld;
import mahjong.model.Tile;
import mahjong.model.Yaku;

public class YakuChecker { //麻雀における**役（Yaku）**の判定を行うロジッククラス
	//特定のあがり形（手牌、鳴き状態、ツモかロンかなど）に対して、成立している役を List<Yaku> として返します。
	public static List<Yaku> checkYakus(List<Meld> melds, Tile pair, boolean isClosed, boolean isTsumo, boolean isRiichi, boolean isIppatsu) {
        List<Yaku> yakuList = new ArrayList<>();
        //boolean isClosed：門前かどうか、boolean isIppatsu：一発ツモ or 一発ロンか

        // ===== 役満判定 =====
        if (isSuuAnkou(melds, isClosed)) { 
            yakuList.add(new Yaku("四暗刻", 13, true));
        }

        // ===== 通常役 =====
        if (isRiichi) {
            yakuList.add(new Yaku("リーチ", 1, false));
        }

        if (isIppatsu) {
            yakuList.add(new Yaku("一発", 1, false));
        }

        if (isTsumo && isClosed) {
            yakuList.add(new Yaku("門前清自摸和", 1, false));
        }

        if (isTanyao(melds, pair)) {
            yakuList.add(new Yaku("断么九（タンヤオ）", 1, false));
        }

        if (isToitoi(melds)) {
            yakuList.add(new Yaku("対々和", 2, false));
        }

        if (isHonitsu(melds, pair)) {
            yakuList.add(new Yaku("混一色", isClosed ? 3 : 2, false));
        }

        if (isYakuhai(melds, pair)) {
            yakuList.add(new Yaku("役牌", 1, false));
        }

        // 他の役も必要に応じてここに追加

        return yakuList;
    }

    // -------------------------------
    // ▼ 個別役の判定メソッド群 ▼
    // -------------------------------

    private static boolean isSuuAnkou(List<Meld> melds, boolean isClosed) { //melds の中に「暗刻 or 暗槓（鳴いていない刻子・槓子）」が4つあれば成立。
        if (!isClosed) return false;
        long ankouCount = melds.stream()
                .filter(m -> (m.getType() == Meld.Type.TRIPLE || m.getType() == Meld.Type.QUAD) && !m.isOpen())
                .count();
        return ankouCount == 4;
    }

    private static boolean isTanyao(List<Meld> melds, Tile pair) { //pair も含めてすべての牌が「中張牌（2〜8）」であれば成立。
        if (!pair.isSimple()) return false;
        for (Meld m : melds) {
            if (!m.getTile().isSimple()) return false;
        }
        return true;
    }

    private static boolean isToitoi(List<Meld> melds) { //4面子すべてが「刻子 or 槓子」であれば成立。
        return melds.stream().allMatch(m -> m.getType() == Meld.Type.TRIPLE || m.getType() == Meld.Type.QUAD);
    }

    private static boolean isHonitsu(List<Meld> melds, Tile pair) { //すべての数牌が1種類のスートのみ + 字牌が含まれている
        String suit = null;
        boolean hasHonor = false;

        List<Tile> tiles = new ArrayList<>();
        tiles.add(pair);
        for (Meld m : melds) tiles.add(m.getTile());

        for (Tile t : tiles) {
            if (t.isHonor()) {
                hasHonor = true;
            } else {
                if (suit == null) suit = t.getSuit();
                else if (!t.getSuit().equals(suit)) return false;
            }
        }
        return hasHonor;
    }

    //isYakuhai(...)：役牌（三元牌）
    private static boolean isYakuhai(List<Meld> melds, Tile pair) { //白・發・中 のいずれかが 刻子 or 槓子 であれば成立。
        int yakuhaiCount = 0;
        for (Meld m : melds) {
            if ((m.getType() == Meld.Type.TRIPLE || m.getType() == Meld.Type.QUAD) && m.isHonor()) {
                int number = m.getTile().getNumber(); // 東=1, 南=2, ..., 白=5, 發=6, 中=7
                if (number >= 5 && number <= 7) {
                    yakuhaiCount++; // 三元牌（白發中）
                }
                // TODO: 場風・自風チェックも必要なら追加（場風変数を引数に追加）
            }
        }
        return yakuhaiCount > 0;
    }
}
