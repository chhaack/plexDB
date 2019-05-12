package com.grossertiger.plex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class PlexDataBase
{
    private final static String SQL_ALL_SERIES ="SELECT serie.title as title, " +
                                                "saison.[index] as season, " +
                                                "episode.[index] as episode " +
                                        "FROM metadata_items episode " +
                                            "JOIN metadata_items saison ON saison.id = episode.parent_id " +
                                            "JOIN metadata_items serie ON serie.id = saison.parent_id " +
                                            "JOIN media_items media ON media.metadata_item_id = episode.id " +
                                            "JOIN library_sections librairie ON librairie.id = episode.library_section_id " +
                                        "WHERE episode.metadata_type = 4 " +
                                        "ORDER BY serie.title, saison.[index], episode.[index]";

    private static String dbPath = "/var/lib/plexmediaserver/Library/Application Support/Plex Media Server/Plug-in Support/Databases/com.plexapp.plugins.library.db";

    public static void main(String[] args)
    {
        String dbPath = "/var/lib/plexmediaserver/Library/Application Support/Plex Media Server/Plug-in Support/Databases/com.plexapp.plugins.library.db";

        if (args.length == 0 || args[0].equalsIgnoreCase("listMissingShows")) {
            if (args.length == 2 && !args[1].isEmpty()) {
                dbPath = args[1];
            }
            listMissingShows(dbPath);
        } else if(args[0].equals("MY_OTHER_METHOD")) {
            //callMyOtherMethod();
            //... Repeat ad nauseum...
        } else {
            //Do other main stuff, or print error message
        }
    }

    private static void listMissingShows(String dbPath) {
        Connection connection = null;

        String actualShow = "";
        int actualSeason = 0;
        int actualEpisode = 0;

        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery(SQL_ALL_SERIES);
            while(rs.next())
            {
                String newShow = rs.getString("serientitel");
                if (!newShow.equalsIgnoreCase(actualShow)) {
                    actualShow = newShow;
                    actualSeason = 0;
                    actualEpisode = 0;
                }
                int newSeason = rs.getInt("season");
                if (newSeason != actualSeason) {
                    if (newSeason == (actualSeason + 1)) {
                        actualSeason = newSeason;
                        actualEpisode = 0;
                    } else if (newSeason != (actualSeason + 1)) {
                        System.out.println("Season " + (actualSeason + 1) + " for show " +actualShow+ " is missing!");
                        actualSeason = newSeason;
                        actualEpisode = 0;
                    }
                }
                int newEpisode = rs.getInt("episode");
                if (newEpisode != actualEpisode && newEpisode != (actualEpisode + 1)) {
                    System.out.println("Episode " + (actualEpisode + 1) + " in Season " +actualSeason+ " for Show " +actualShow+ " is missing!");
                }
                actualEpisode = newEpisode;
                //System.out.println(actualShow + "-" + actualSeason + "-" +actualEpisode);
            }
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}