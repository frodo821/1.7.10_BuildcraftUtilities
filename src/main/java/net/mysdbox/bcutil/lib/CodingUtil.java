package net.mysdbox.bcutil.lib;

public class CodingUtil {
	/**
	 * �z��ɗv�f�����邩���ׂ�֐�
	 * @param obj ���ׂ�z��B
	 * @param targ obj�Ŏw�肵���z��ɓ����Ă��邩�ǂ������ׂ����I�u�W�F�N�g
	 * @return boolean obj��targ�������Ă����true�A�����Ă��Ȃ����false
	 */
	public static boolean ArrayContains(Object[] obj, Object targ){
		for(int i = 0; i < obj.length; i++){
			if(obj[i] == targ) return true;
		}
		return false;
	}

	public static final String emptyStr = "";
}
