package net.mysdbox.bcutil.lib;

public class CodingUtil {
	/**
	 * 配列に要素があるか調べる関数
	 * @param obj 調べる配列。
	 * @param targ objで指定した配列に入っているかどうか調べたいオブジェクト
	 * @return boolean objにtargが入っていればtrue、入っていなければfalse
	 */
	public static boolean ArrayContains(Object[] obj, Object targ){
		for(int i = 0; i < obj.length; i++){
			if(obj[i] == targ) return true;
		}
		return false;
	}

	public static final String emptyStr = "";
}
