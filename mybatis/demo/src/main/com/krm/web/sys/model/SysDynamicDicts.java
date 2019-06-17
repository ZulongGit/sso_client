package com.krm.web.sys.model;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.krm.common.base.BaseEntity;

/**
     动态数据字典模型类
   2017-10-16
*/
@Table(name="sys_dynamic_dict")
public class SysDynamicDicts extends BaseEntity<SysDynamicDicts> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4755580340217621958L;
	@Id
	private String id; 				//主键
	private	String	keyName;			//文本属性名
	private	String	valueName;			//值属性名
	@Transient
	private	String	keyName1;			//文本属性名1
	@Transient
	private	String	valueName1;			//值属性名1
	private	String	sqlContent;			//SQL内容
	private	String	type;			//类型
	private	String	remarks;			//说明
	private	String	dataScope;			//数据范围
	private	String	tableName;			//关联表
	private	String	sqlMode;			//模式（1：简单:2：自定义）
	private String  userIdFieldBind;      //用户字段绑定
    private String  organCodeFieldBind;      //机构号字段绑定
    @Transient
    private String  userIdFieldBind1;      //用户字段绑定1
    @Transient
    private String  organCodeFieldBind1;      //机构号字段绑定1

	public SysDynamicDicts() {
		super();
		super.setModuleName("动态数据字典信息");
	}
	
	/**
	 * 主键
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	/**
	 * 文本属性名
	 * @param keyName
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyName() {
		return keyName;
	}

	/**
	 * 值属性名
	 * @param valueName
	 */
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public String getValueName() {
		return valueName;
	}
	/**
	 * 文本属性名1
	 * @param keyName1
	 */
	public void setKeyName1(String keyName1) {
		this.keyName1 = keyName1;
	}
	public String getKeyName1() {
		return keyName1;
	}
	
	/**
	 * 值属性名1
	 * @param valueName1
	 */
	public void setValueName1(String valueName1) {
		this.valueName1 = valueName1;
	}
	public String getValueName1() {
		return valueName1;
	}

	/**
	 * SQL内容
	 * @param sqlContent
	 */
	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}
	public String getSqlContent() {
		return sqlContent;
	}

	/**
	 * 类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}

	/**
	 * 说明
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 数据范围
	 * @param dataScope
	 */
	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}
	public String getDataScope() {
		return dataScope;
	}

	/**
	 * 关联表
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * 模式（1：简单:2：自定义）
	 * @param sqlMode
	 */
	public void setSqlMode(String sqlMode) {
		this.sqlMode = sqlMode;
	}
	public String getSqlMode() {
		return sqlMode;
	}

	/**
	 * 用户字段绑定
	 * @param userIdFieldBind
	 */
	public void setUserIdFieldBind(String userIdFieldBind) {
		this.userIdFieldBind = userIdFieldBind;
	}
	public String getUserIdFieldBind() {
		return userIdFieldBind;
	}
	
	/**
	 * 机构号字段绑定
	 * @param organCodeFieldBind
	 */
	public void setOrganCodeFieldBind(String organCodeFieldBind) {
		this.organCodeFieldBind = organCodeFieldBind;
	}
	public String getOrganCodeFieldBind() {
		return organCodeFieldBind;
	}
	
	
	public String getUserIdFieldBind1() {
		return userIdFieldBind1;
	}

	public String getOrganCodeFieldBind1() {
		return organCodeFieldBind1;
	}

	public void setUserIdFieldBind1(String userIdFieldBind1) {
		this.userIdFieldBind1 = userIdFieldBind1;
	}

	public void setOrganCodeFieldBind1(String organCodeFieldBind1) {
		this.organCodeFieldBind1 = organCodeFieldBind1;
	}

	@Override
	public String toString() {
		return "动态数据字典： {\"id\": \""+id+"\", \"keyName\": \""+keyName+"\", \"valueName\": \""+valueName+"\", \"sqlContent\": \""+sqlContent+"\", \"type\": \""+type+"\", \"remarks\": \""+remarks+"\", \"dataScope\": \""+dataScope+"\", \"tableName\": \""+tableName+"\", \"sqlMode\": \""+sqlMode+"\", \"userIdFieldBind\": \""+userIdFieldBind+"\", \"organCodeFieldBind\": \""+organCodeFieldBind+"}";
	}
}