package com.mayao;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

/**
 * @author: zhu
 * @date: 2018/8/20 11:17
 * mybatis-plus逆向工程示例代码
 */
public class GenerateCode {
    @Test
    public void testGenerator() {
        //1、全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)//开启AR模式
                //.setAuthor("mayao")//设置作者
                // 配置是否打开目录，false 为不打开（可选）
                .setOpen(false)
                //生成路径(一般都是生成在此项目的src/main/java下面)
                .setOutputDir("/Users/mayao/Desktop/codes/back-codes/myvhr2/src/main/java")
                .setFileOverride(true)//第二次生成会把第一次生成的覆盖掉
                .setIdType(IdType.AUTO)//主键策略
                .setServiceName("%sService")//生成的service接口名字首字母是否为I，这样设置就没有I
                .setBaseResultMap(true)//生成resultMap
                .setBaseColumnList(true);//在xml中生成基础列
        //2、数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)//数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/vhr?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8")
                .setUsername("root")
                .setPassword("mayao666");
        //3、策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)//开启全局大写命名
                .setNaming(NamingStrategy.underline_to_camel)//下划线到驼峰的命名方式
                .setTablePrefix("t_")//表名前缀,逆向工程生成java文件时删除前缀
                .setEntityLombokModel(true)//使用lombok
                .setRestControllerStyle(true)//配置成@RestController
                .setInclude("adjustsalary","appraise","department","employee","employeeec","employeeremove",
                        "employeetrain","empsalary","flyway_schema_history","hr","hr_role","joblevel",
                        "mail_send_log","mail_send_log_copy1","menu","menu_role","msgcontent","nation",
                        "oplog","politicsstatus","position","role","salary","sysmsg");//逆向工程使用的表
        //4、包名策略配置
        PackageConfig packageConfig = new PackageConfig();

        packageConfig.setParent("com.mayao")//项目的包名
                .setMapper("mapper")
                .setService("service")
                //.setController("controller")
                .setEntity("entity")
                .setXml("mapper/xml");//设置xml文件的目录
        //5、整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);
        //6、执行
        autoGenerator.execute();
    }
}