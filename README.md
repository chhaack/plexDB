# plexDB

simple java application for searching the plex db for missing episodes

absolutly basic - only checks if seasons or episodes between are missing - no checks with online sources!

java -jar plexDB.jar listmissingshows <<full qualified db>>
  
example: java -jar plexDB.jar listmissingshows /var/lib/plexmediaserver/Library/Application\ Support/Plex\ Media\ Server/Plug-in\ Support/Databases/com.plexapp.plugins.library.db
