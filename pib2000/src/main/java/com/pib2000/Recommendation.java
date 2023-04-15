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

    //Andromeda
    public static String top50SongsOfFriends(int userID){
        String query = "select distinct(s.name), count(s.s_id)\n" +
                "from \"Follows\" fl\n" +
                "inner join \"Listened_to\" lt on lt.u_id = fl.follows\n" +
                "inner join  \"Song\" s on s.s_id = lt.s_id\n" +
                "where fl.u_id = 2\n" +
                "group by s.name\n" +
                "order by count(s.s_id) desc\n" +
                "limit 50;";
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

    //Andromeda
    public static String top5GenresOfMonth(){
        String query = "select distinct(g.\"genrename\"), count(g.g_id)\n" +
        "from \"Listened_to\" lt\n" +
        "inner join \"Song\" s on lt.s_id = s.s_id\n" +
        "inner join \"Genre_s\" gs on gs.s_id = s.s_id\n" +
        "inner join \"Genre\" g on g.g_id = gs.g_id\n" +
        "where extract(MONTH from lt.listened_to_datetime) = extract(MONTH from now())\n" +
        "group by g.\"genrename\"\n" +
        "order by count(g.g_id) desc\n" +
        "limit 5";
        StringBuilder sb = new StringBuilder();
        try (StarbugConnection conn = new StarbugConnection()) {
            ResultSet rs = conn.doQuery(query);

            int num = 1;
            while (rs.next()) {
                sb.append(num);
                sb.append(": ");
                sb.append(rs.getString("genrename"));
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

    public static void main(String[] args){
        System.out.println(top50SongsOfFriends(2));
    }
}
