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

    //Roshan
    public static String forYou(int userID) {
        String query1 = "select distinct(a.\"Name\"), count(a.ar_id)\n" +
                "from \"Listened_to\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where lt.u_id = " + userID + "\n" +
                "group by a.\"Name\"\n" +
                "order by count(a.ar_id) desc\n" +
                "limit 1";

        StringBuilder sb = new StringBuilder("(");
        //grabbing the top artist(s) of the current user by play number
        try (StarbugConnection conn = new StarbugConnection()) {
            ResultSet rs = conn.doQuery(query1);

            while (rs.next()) {
                sb.append("\'");
                sb.append(rs.getString("Name")); // artist name
                sb.append("\',");
            }

            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        catch (Exception e) {}

        sb.append(")");

        String artists = sb.toString();
        String query2 = "select distinct(u_id)\n" +
                "from \"Listened_to\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Song\" s\n" +
                "on s.s_id = lt.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where a.\"Name\" IN " + artists + "\n" +
                "limit 10";

        StringBuilder users = new StringBuilder("(");
        //grabbing 10 users who have played the top artist(s) of the current user
        try (StarbugConnection conn2 = new StarbugConnection()) {
            ResultSet rs = conn2.doQuery(query2);

            while (rs.next()) {
                users.append("\'");
                users.append(rs.getInt("u_id")); // user id
                users.append("\',");
            }

            if (users.length() > 1) {
                users.deleteCharAt(users.length() - 1);
            }
        }
        catch (Exception e) {}

        users.append(")");
        String query3 = "select s.\"name\" as songname, a.\"Name\" as artistname\n" +
                "from \"Listened_to\" lt\n" +
                "inner join \"Produces_s\" ps\n" +
                "on lt.s_id = ps.s_id\n" +
                "inner join \"Song\" s\n" +
                "on s.s_id = lt.s_id\n" +
                "inner join \"Artist\" a\n" +
                "on ps.ar_id = a.ar_id\n" +
                "where lt.u_id IN " + users.toString() + "\n" +
                "group by s.\"name\", a.\"Name\"\n" +
                "limit 10";

        StringBuilder result = new StringBuilder("For You:\n");
        try (StarbugConnection conn3 = new StarbugConnection()) {
            ResultSet rs = conn3.doQuery(query3);

            while (rs.next()) {
                result.append("\"");
                result.append(rs.getString("songname"));
                result.append("\" by ");
                result.append(rs.getString("artistname"));
                result.append("\n");
            }
        }
        catch (Exception e) {}

        return result.toString();
    }

    public static void updatePasswords() {
        for(int i = 1; i < 1007; i++){
            StringBuilder result = new StringBuilder();
            try (StarbugConnection conn = new StarbugConnection()) {
                String query = "select password\n" +
                        "from \"User\"\n" +
                        "where u_id = " + i +"\n";
                ResultSet rs = conn.doQuery(query);

                while (rs.next()) {
                    result.append(rs.getString("password"));
                }
                System.out.println(result.toString());
            }
            catch (Exception e) {}

            String new_password = User.doHashing(result.toString());
            System.out.println(new_password);
            //hash

            try (StarbugConnection conn = new StarbugConnection()) {
                String query = "update \"User\"\n" +
                        "set password = '" + new_password + "'\n" +
                        "where u_id = " + i + ";";
                int rs = conn.doUpdate(query);
                System.out.println(rs);
            }
            catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        updatePasswords();
    }
}
