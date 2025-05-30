package mahjong.model;

public class Meld { //面子クラス　順子（シュンツ）、刻子（コーツ）、槓子（カンツ）を表現。
	public enum Type { SEQUENCE, TRIPLE, QUAD } //これは面子の種類を表す列挙型（enum）です。これを使うことで、Meld の種類を明確に区別できます。
	//SEQUENCE = 順子（例: 3-4-5）、TRIPLE = 刻子（例: 三枚同じ牌）、QUAD = 槓子（例: 四枚同じ牌）
	//フィールド（インスタンス変数）
    private Type type; //順子、刻子、槓子のいずれか
    private Tile tile; //面子の中心となる牌（たとえば「三萬の刻子」なら tile=3萬）
    private boolean isOpen; //副露（ポン/チー/カン）したかどうか（true=鳴き、false=門前）

    //コンストラクタ
    public Meld(Type type, Tile tile, boolean isOpen) { //種類・牌・副露状態の情報を与えてインスタンスを作成
        this.type = type;
        this.tile = tile;
        this.isOpen = isOpen;
    }

    //ゲッター　面子の種類・牌・副露状態を外部から読み取るためのメソッド
    public Type getType() { return type; } //getType() → 順子？刻子？槓子？
    public Tile getTile() { return tile; } //getTile() → 面子の基準となる牌は何？
    public boolean isOpen() { return isOpen; } //isOpen() → 鳴いてる？
    
    //補助メソッド（便利な判定）
    public boolean isHonor() { //字牌かどうか（白發中、東南西北）
        return tile.isHonor();
    }

    public boolean isTerminal() { //isTerminal()：老頭牌（1か9） or 特定の順子か
    	//1 or 9 か？　または　順子のとき、7-8-9 の形（この場合 tile は 7）なら老頭牌扱いとする。
        return tile.isTerminal() || (type == Type.SEQUENCE && tile.getNumber() == 7);
    }

    @Override
    public String toString() { //このクラスのインスタンスを文字列で表示したときの形式。
        return type + ":" + tile + (isOpen ? "(暗)" : "(明)");
    }
}
