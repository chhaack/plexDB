# plexDB

simple java application for searching the plex db for missing episodes

absolutely basic - only checks if seasons or episodes between are missing - no checks with online sources!

read only - should work with running plex media server.

USE AT YOUR OWN RISK!

java -jar plexDB.jar listmissingshows <<full qualified db>>
  
example: java -jar plexDB.jar listmissingshows /var/lib/plexmediaserver/Library/Application\ Support/Plex\ Media\ Server/Plug-in\ Support/Databases/com.plexapp.plugins.library.db
