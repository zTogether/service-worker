package cn.xyzs.api.worker.mapper;

import cn.xyzs.api.worker.pojo.XyClbZctxMbVr;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface XyClbZctxMbMapper extends Mapper<XyClbZctxMbVr> {
    /***
     *
     * @Description: 根据vrStyle查询套系VR
     * @author: GeWeiliang
     * @date: 2018\9\2 0002 17:35
     * @param: []
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Select("<script>SELECT * FROM XY_CLB_ZCTX_MB_VR WHERE VR_STYLE=#{vrStyle,jdbcType=VARCHAR}</script>")
    public List<Map<String,Object>> showZctxVr(@Param("vrStyle") String vrStyle)throws SQLException;

    @Select("<script>SELECT * FROM XY_CLB_ZCTX_MB_VR WHERE VR_ID=#{vrId,jdbcType=VARCHAR}</script>")
    public Map<String,Object> vrDetail(@Param("vrId") String vrId) throws SQLException;

    /**
     * 首页展示的整装套系数据随机获取8个
     * @Description:
     * @author: zheng shuai
     * @date: 2018/10/28 10:10
     * @param: []
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Select("<script>" +
            "SELECT\n" +
            "\tVR_ID, \n" +
            "\tVR_NAME, \n" +
            "\tVR_TYPE, \n" +
            "\tVR_STYLE, \n" +
            "\tVR_URL, \n" +
            "\tVR_PIC, \n" +
            "\tVR_SPEC\n" +
            "FROM\n" +
            "\tXY_CLB_ZCTX_MB_VR sample(50)\n" +
            "WHERE\n" +
            "\tROWNUM <![CDATA[<]]> 9" +
            "</script>")
    public List<Map<String ,Object>> getRandZctx() throws SQLException;

    /***
     *
     * @Description: 套系材料列表
     * @author: GeWeiliang
     * @date: 2018\9\3 0003 16:44
     * @param: [vrId]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Select("<script>" +
            "SELECT zm.*,NVL(zd.ZC_NAME, '-') ZC_NAME,zd.ZC_PRICE_OUT,NVL(zd.ZC_BRAND, '-') ZC_BRAND,\n" +
            "\t\t   NVL(sup.SUP_NAME, '-') SUP_NAME, NVL(zd.ZC_SPEC, '-') ZC_SPEC,\n" +
            "\t\t\t NVL(zd.ZC_MATERIAL, '-') ZC_MATERIAL,NVL(zd.ZC_COLOR, '-') ZC_COLOR,NVL(zf.ZCFL_NAME,'-') ZCFL,\n" +
            "       NVL(zd.ZC_UNIT,'-') ZC_UNIT,NVL(zd.ZC_DES,'-') ZC_DES,zd.ZC_CYC,NVL(zd.ZC_VERSION, '-') ZC_VERSION\n" +
            "FROM XY_CLB_ZCTX_MB zm\n" +
            "LEFT JOIN XY_CLB_ZC_DB zd ON zm.ZC_CODE=zd.ZC_CODE\n" +
            "LEFT JOIN XY_SUPPLIER sup ON zd.ZC_SUP=sup.SUP_CODE\n" +
            "LEFT JOIN XY_CLB_ZC_FL zf ON zd.ZC_TYPE=zf.ZCFL_CODE\n" +
            "WHERE zm.VR_ID = #{vrId,jdbcType=VARCHAR} AND zm.FL_BH LIKE #{flBh,jdbcType=VARCHAR}||'%'\n" +
            "ORDER BY zm.ZC_CODE" +
            "</script>")
    public List<Map<String ,Object>> getZctxMbList(@Param("vrId") String vrId, @Param("flBh") String flBh) throws SQLException;

}
