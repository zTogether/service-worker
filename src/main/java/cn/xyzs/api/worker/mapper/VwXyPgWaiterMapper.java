package cn.xyzs.api.worker.mapper;

import cn.xyzs.common.pojo.VwXyPgWaiter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface VwXyPgWaiterMapper extends Mapper<VwXyPgWaiter> {
    @Select("<script>" +
            "SELECT\n" +
            "\tTO_CHAR(vxpw.PG_OP_DATE, 'yyyy-MM-dd hh24:mi:ss' ) PG_OP_DATE, \n" +
            "\tvxpw.PG_ID,\n" +
            "\tvxpw.GR_ID, \n" +
            "\tvxpw.GR_NAME, \n" +
            "\tvxpw.DISTANCE, \n" +
            "\tTO_CHAR(vxpw.PG_BEGIN_DATE, 'yyyy-MM-dd hh24:mi:ss' ) PG_BEGIN_DATE,\n" +
            "\tvxpw.GZ, \n" +
            "\tvxpw.ADD_MONEY, \n" +
            "\tvxpw.CTR_CODE, \n" +
            "\tvxpw.CTR_NAME, \n" +
            "\tvxpw.CTR_ADDR, \n" +
            "\tvxpw.ORG_PRJ_NAME, \n" +
            "\tvxpw.STATE,\n" +
            "\txp.PG_DAYS,\n" +
            "\tTO_CHAR(( vxpw.PG_BEGIN_DATE + xp.PG_DAYS ), 'yyyy-MM-dd hh24:mi:ss' ) ENDDATE \n" +
            "FROM\n" +
            "\tVW_XY_PG_WAITER vxpw,\n" +
            "\tXY_PG xp \n" +
            "WHERE\n" +
            "\tvxpw.GR_ID = #{grId,jdbcType=VARCHAR}\n" +
            "\tAND vxpw.PG_ID = xp.PG_ID \n" +
            "ORDER BY\n" +
            "\tvxpw.STATE DESC,\n" +
            "\tvxpw.DISTANCE" +
            "</script>")
    public List<Map<String ,Object>> getVwXyPgWaiters(String grId) throws SQLException;


    @Update("UPDATE XY_PG_WAITER T SET T.ZT=(CASE T.GR_ID WHEN #{userId} THEN '抢单成功' ELSE '抢单失败' END) WHERE T.PG_ID=#{pgId}")
    public int checkPg(@Param("pgId") String pgId, @Param("userId")String userId);


    @Select("SELECT A.GR_ID,\n" +
            "LEAST((A.GR_LEVEL+A.GR_LEVEL_VM),20) GR_LEVEL,\n" +
            "A.GR_GPS_X,\n" +
            "A.GR_GPS_Y\n" +
            "FROM \n" +
            "\tXY_PG_WAITER T,XY_GCB_GRXX A \n" +
            "WHERE \n" +
            "\tT.PG_ID='' \n" +
            "AND \n" +
            "\tT.GR_ID=A.GR_ID \n" +
            "ORDER BY 2 DESC")
    public List<Map<String,Object>> getGrs(String pgId);


    @Select("SELECT PG_ID FROM XY_PG T WHERE T.PG_GR IS NULL AND EXISTS(SELECT 1 FROM XY_PG_WAITER A WHERE A.PG_ID=T.PG_ID AND A.ZT='已报名')  AND (SYSDATE - T.PG_OP_DATE >0.5 OR (SELECT COUNT(1) FROM XY_PG_WAITER A WHERE A.PG_ID=T.PG_ID)>14 OR (SELECT COUNT(1) FROM XY_PG_WAITER A WHERE A.PG_ID=T.PG_ID AND T.PG_STAGE IN ('10','21'))>(CASE T.PG_STAGE WHEN '10' THEN 2 WHEN '21' THEN 1 ELSE 999 END))")
    public List<String> checkD();
}
