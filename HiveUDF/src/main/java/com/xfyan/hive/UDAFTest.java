package com.xfyan.hive;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;

/**
 *���ã���ĳһ�е�ֵ��ָ���ָ�����������
 *ʵ��UDAFEvaluator�ӿڣ�ʵ��init,iterater,merge,terminatePartial,terminate����
 */
@SuppressWarnings("deprecation")
public class UDAFTest extends UDAF{
	public static class ConcatUDAFEvaluator implements UDAFEvaluator{
		String line ="";
		public void init() {
			line = "";
		}
		
		
		public boolean iterater(String value,String separator){
			if(value != null || separator !=null){
				line += value+separator;
				
			}
			
			line += "";
			return true;
		}
		
		public String terminate(){
			return line;
		}
		public String terminatePartial(){
			return line;
		}
		public boolean merge(String another){
			return iterater(line,another);
		}
		
	}
	
	
}
