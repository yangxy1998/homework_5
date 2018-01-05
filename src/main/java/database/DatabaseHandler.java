package database;

import vo.Program;

import java.sql.ResultSet;
import java.util.List;


/**
 * 数据库信息处理接口
 */


public interface DatabaseHandler {

    //连接到指定数据库，需要参数：数据库地址、用户名、密码
    void connectTo(String URL,String Username,String Password);

    //终止连接
    void disConnect();

    //从数据库读取单条项目
    Program getProgram(ResultSet resultSet);

    //从数据库获取全部项目信息
    List<Program> getPrograms();

    //向数据库增加一条项目信息
    void addProgram(Program program);

    //向数据库增加全部项目信息
    void addPrograms(List<Program> programs);

    //通过项目Id删除一条项目信息
    void deleteProgram(String id);

    //修改一条项目信息
    void modifyProgram(Program program,ChangeType changeType,String update);

    //通过Id查找唯一项目信息
    Program findProgram(String id);

    //通过项目其他信息查找与之相同的所有项目信息
    List<Program> findPrograms(ChangeType changeType,String search);
}
