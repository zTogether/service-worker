package cn.xyzs.api.worker.mapper;

import cn.xyzs.common.pojo.XySupplier;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/***
 *
 * @Description: 查询供应商信息
 * @author: GeWeiliang
 * @date: 2018\8\31 0031 9:33
 * @param:
 * @return:
 */
public interface XySupplierMapper extends Mapper<XySupplier> {
    @Select("<script>SELECT * FROM XY_SUPPLIER WHERE SUP_CODE = #{supCode,jdbcType=VARCHAR}</script>")
    public List<Map<String,Object>> getSupInfo(@Param("supCode") String supCode);
}
