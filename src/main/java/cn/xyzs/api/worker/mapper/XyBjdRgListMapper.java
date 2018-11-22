package cn.xyzs.api.worker.mapper;

import cn.xyzs.api.worker.pojo.XyBjdRgList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface XyBjdRgListMapper extends Mapper<XyBjdRgList> {

    @Select("<script>SELECT BRL.BJD_CODE,BRL.BJD_RG_ROWID,BRL.BJD_RG_STAGE,BRL.BJD_RG_NO,\n" +
            "\t\tBRL.RG_NAME,BRL.RG_UNIT," +
            "NVL(BRL.RG_QTY,'0') RG_QTY,BRL.RG_PRICE,BRL.RG_XJ,\n" +
            "\t\tNVL(BRL.RG_MARK, '-') RG_MARK,\n" +
            "\t\tNVL(BRL.RG_DES, '-') RG_DES,\n" +
            "\t\tNVL(BRL.RG_YN, '-') RG_YN,\n" +
            "\t\tNVL(BRL.RG_YZRK, '-') RG_YZRK\t" +
            "FROM XY_BJD_RG_LIST BRL \n" +
            "LEFT JOIN XY_BJD_MAIN BM ON BRL.BJD_CODE=BM.BJD_CODE\n" +
            "WHERE BM.CTR_CODE=#{ctrCode ,jdbcType=VARCHAR} AND BRL.BJD_CODE=#{bjdCode ,jdbcType=VARCHAR}\n" +
            "ORDER BY TO_NUMBER(BRL.BJD_RG_NO) </script>")
    public List<Map<String,Object>> getBjdRgList(@Param("ctrCode") String ctrCode,
                                                 @Param("bjdCode") String bjdCode)throws SQLException;

    @Select("<script>" +
            "SELECT NVL(SUM(BRL.RG_XJ),0) PRJ_ZJ\t\n" +
            "FROM XY_BJD_RG_LIST BRL \n" +
            "LEFT JOIN XY_BJD_MAIN BM ON BRL.BJD_CODE=BM.BJD_CODE\n" +
            "WHERE BM.CTR_CODE=#{ctrCode,jdbcType=VARCHAR} AND BRL.BJD_CODE=#{bjdCode,jdbcType=VARCHAR} AND BRL.BJD_RG_STAGE=#{rgStage,jdbcType=VARCHAR}\n" +
            "ORDER BY TO_NUMBER(BRL.BJD_RG_NO)" +
            "</script>")
    public Map<String,Object> prjZongJi(@Param("ctrCode") String ctrCode, @Param("bjdCode") String bjdCode,
                                        @Param("rgStage") String rgStage) throws SQLException;
}
