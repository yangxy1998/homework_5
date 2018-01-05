package database.impl;

import database.ChangeType;
import database.DatabaseHandler;
import main.SearchManager;
import vo.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    static {
        DatabaseHandler databaseHandler=new DatabaseHandler() {

            @Override
            public void connectTo(String URL, String Username, String Password) {
                Connection connection=null;
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(URL, Username, Password);
                }catch (Exception e){
                    e.printStackTrace();
                }
                SearchManager.startConnection(connection);
            }

            @Override
            public void disConnect() {
                SearchManager.disConnection();
            }

            @Override
            public Program getProgram(ResultSet resultSet) {
                Program program=new Program();
                try {
                    program.setId(resultSet.getString(1));
                    program.setCountry(resultSet.getString(2));
                    program.setUniversity(resultSet.getString(3));
                    program.setSchool(resultSet.getString(4));
                    program.setProgramName(resultSet.getString(5));
                    program.setHomepage(resultSet.getString(6));
                    program.setLocation(resultSet.getString(7));
                    program.setEmail(resultSet.getString(8));
                    program.setPhoneNumber(resultSet.getString(9));
                    program.setDegree(resultSet.getString(10));
                    program.setDeadlineWithAid(resultSet.getString(11));
                    program.setDeadlineWithoutAid(resultSet.getString(12));
                    System.out.println(program.getUniversity());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return program;
            }

            @Override
            public List<Program> getPrograms() {
                List<Program> programs=new ArrayList<>();
                Connection connection=null;
                try {
                    connection= SearchManager.getConnection();
                    Statement statement=connection.createStatement();
                    String sql="SELECT DISTINCT * FROM program";
                    ResultSet resultSet=statement.executeQuery(sql);
                    while(resultSet.next()){
                        Program program=getProgram(resultSet);
                        programs.add(program);
                    }
                    resultSet.close();
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return programs;
            }

            @Override
            public void addProgram(Program program) {
                Connection connection=null;
                try {
                    connection= SearchManager.getConnection();
                    String sql="INSERT INTO program (Id,country,university,school," +
                            "program_name,homepage,location,email,phone_number," +
                            "degree,deadline_with_aid,deadline_without_aid)" +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement statement=connection.prepareStatement(sql);
                    statement.setString(1,program.getId());
                    statement.setString(2,program.getCountry());
                    statement.setString(3,program.getUniversity());
                    statement.setString(4,program.getSchool());
                    statement.setString(5,program.getProgramName());
                    statement.setString(6,program.getHomepage());
                    statement.setString(7,program.getLocation());
                    statement.setString(8,program.getEmail());
                    statement.setString(9,program.getPhoneNumber());
                    statement.setString(10,program.getDegree());
                    if(program.getDeadlineWithAid().getBytes().length<255) {
                        statement.setString(11, program.getDeadlineWithAid());
                        statement.setString(12, program.getDeadlineWithoutAid());
                        statement.executeUpdate();
                    }
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void addPrograms(List<Program> programs) {
                for(Program program:programs){
                    addProgram(program);
                }
            }

            @Override
            public void deleteProgram(String id) {
                Connection connection=null;
                try {
                    connection=SearchManager.getConnection();
                    String sql="DELETE FROM program WHERE Id=?";
                    PreparedStatement statement=connection.prepareStatement(sql);
                    statement.setString(1,id);
                    statement.executeUpdate();
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void modifyProgram(Program program, ChangeType changeType, String update) {
                Connection connection=null;
                try {
                    connection=SearchManager.getConnection();
                    String sql="UPDATE program SET "+changeType.name()+" =? WHERE Id=?";
                    PreparedStatement statement=connection.prepareStatement(sql);
                    statement.setString(1,update);
                    statement.setString(2,program.getId());
                    statement.executeUpdate();
                    statement.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public Program findProgram(String id) {
                Connection connection=null;
                try {
                    connection= SearchManager.getConnection();
                    Statement statement=connection.createStatement();
                    String sql="SELECT DISTINCT * FROM program";
                    ResultSet resultSet=statement.executeQuery(sql);
                    while(resultSet.next()){
                        if(resultSet.getString(1).equals(id)){
                            Program program=getProgram(resultSet);
                            return program;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public List<Program> findPrograms(ChangeType changeType,String search){
                List<Program> programs=new ArrayList<>();
                Connection connection=null;
                try {
                    connection= SearchManager.getConnection();
                    Statement statement=connection.createStatement();
                    String sql="SELECT DISTINCT * FROM program";
                    ResultSet resultSet=statement.executeQuery(sql);
                    while(resultSet.next()){
                        if(resultSet.getString(changeType.getType()).equals(search)){
                            Program program=getProgram(resultSet);
                            programs.add(program);
                        }
                    }
                    return programs;
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    return programs;
                }
            }
        };
        SearchManager.registDatabaseHandler(databaseHandler);
    }
}
