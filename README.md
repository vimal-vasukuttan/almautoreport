# almautoreport
This project will generate report of HP alm defects. Reports will be send to mail addresses.

# Java client to connect to HP ALM rest end points and retrieve defects. Defects report will be created in Excel and configuration available to
send the same in mail

# It is developed such that it is scheduled to run every Monday at 8 am.

# Maven for build.
  Run Time : Tomcat 7 or more
  JDK : Java 8
  IOC : Spring
  Excel : APACHE POI
  MAIL : Javax Mail
  Rest Client : Apache Http Client plus Jackson for Json operation
  
 # If facing connectivity issue with SSL connection to ALM servers(Connected to server using https), make sure to install Server certificate.
