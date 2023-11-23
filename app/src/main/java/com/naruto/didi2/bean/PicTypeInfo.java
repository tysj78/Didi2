package com.naruto.didi2.bean;

import java.util.List;

/**
 * add by gxb 20200220
 * @author Administrator
 * 图片类型信息
 */
public class PicTypeInfo {
	
	private String retCode;//状态代码，0成功，1失败
	private String retMsg;//提示失败信息
	private List<OnOffs> pojoList;
	
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public List<OnOffs> getPojoList() {
		return pojoList;
	}

	public void setPojoList(List<OnOffs> pojoList) {
		this.pojoList = pojoList;
	}

	public class OnOffs{
		private String onOff;//类型：装上件/拆下件
		private List<ImType> imTypes;
		
		public String getOnOff() {
			return onOff;
		}

		public void setOnOff(String onOff) {
			this.onOff = onOff;
		}

		public List<ImType> getImTypes() {
			return imTypes;
		}

		public void setImTypes(List<ImType> imTypes) {
			this.imTypes = imTypes;
		}

		public class ImType{
			
//			private String imType;//图片类型
			
			private String val;//图片类型码
			private String desc;//图片类型
			
			public String getVal() {
				return val;
			}
			public void setVal(String val) {
				this.val = val;
			}
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			
			
			@Override
			public String toString() {
				return "ImType [val=" + val + ", desc=" + desc + "]";
			}

			
			
//			public String getImType() {
//				return imType;
//			}
//
//			public void setImType(String imType) {
//				this.imType = imType;
//			}

//			@Override
//			public String toString() {
//				return "ImType [imType=" + imType + "]";
//			}
		}

		@Override
		public String toString() {
			return "OnOffs [onOff=" + onOff + ", imTypes=" + imTypes.size() + "]";
		}
	}

	@Override
	public String toString() {
		return "PicTypeInfo [retCode=" + retCode + ", retMsg=" + retMsg + ", pojoList=" + pojoList + "]";
	}
	
	
}
