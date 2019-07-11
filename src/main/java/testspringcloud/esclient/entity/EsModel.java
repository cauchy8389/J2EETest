package testspringcloud.esclient.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: zhy
 * @Description: 搜索引擎师范Model类
 * @Date 2019-7-11
 */
@Data
@ToString
@NoArgsConstructor
public class EsModel {

    private String id;

    private String name;

    private int age;

    private Date date;
}
