package com.pib2000;

import java.sql.ResultSet;

public class Recommendation {

    //Andromeda
    public static String top50SongsFor30Days(){
        String query = "select distinct(s.\"name\"), count(s.s_id)\n" +
                "from \"Listened_to\" lt\n" +
                "inner join \"Song\" s on lt.s_id = s.s_id\n" +
                "where lt.listened_to_datetime >= now() - interval '30 days'\n" +
                "group by s.\"name\"\n" +
                "order by count(s.s_id) desc\n" +
                "limit 50";
        StringBuilder sb = new StringBuilder();
        try (StarbugConnection conn = new StarbugConnection()) {
            ResultSet rs = conn.doQuery(query);

            int num = 1;
            while (rs.next()) {
                sb.append(num);
                sb.append(": ");
                sb.append(rs.getString("name"));
                sb.append(" ");
                sb.append(rs.getString("count"));
                sb.append("\n");
                num++;
            }
            return sb.toString();
        }
        catch (Exception e) {}
        return sb.toString();
    }

    public static String top50SongsOfFriends(int userID){

        return null;
    }

    //Andromeda
    public static String top5GenresOfMonth(){

        return null;
    }

    public static void main(String[] args){
        System.out.println(top50SongsFor30Days());
    }
}
