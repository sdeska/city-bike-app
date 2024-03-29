# Helsinki City Bike App

This repository contains the implementation of a Helsinki city bike app. This is a web application that makes use of 
Helsinki Region Transport's (HSL) datasets to display data from journeys made with the city bikes.

The application is written in Java, using the Spring Framework and a PostgreSQL database. The application is run in a Docker container.

The required files for running the program:  
- https://dev.hsl.fi/citybikes/od-trips-2021/2021-05.csv
- https://dev.hsl.fi/citybikes/od-trips-2021/2021-06.csv
- https://dev.hsl.fi/citybikes/od-trips-2021/2021-07.csv
- https://opendata.arcgis.com/datasets/726277c507ef4914b0aec3cbcfcbfafc_0.csv  

These should be placed into /src/main/resources/ before executing, or the program won't work.

License and information: https://www.avoindata.fi/data/en_GB/dataset/hsl-n-kaupunkipyoraasemat/resource/a23eef3a-cc40-4608-8aa2-c730d17e8902

Javadocs for the project can be generated if Maven is installed locally.  
The docs can be generated by running the command 'mvn javadoc:javadoc'.  
After this the docs can be found in /target/site/apidocs/

After adding the data files as described above, and with Docker installed and running, 
the program can be executed by running 'docker-compose up --build' at the root of the project.  
After this, using a browser it is possible to go to http://localhost:8080/ to use the app.  
If one wishes to restart the backend, currently 'docker-compose down' needs to be run before running 'docker-compose up' again, since the program
cannot currently handle already having the same data in the database.  
On Linux these commands require sudo, unless the current user is in docker group.

As of right now, the importing of data at startup is implemented, as is a basic frontpage and paginated & sortable tables of stations and journeys.  
It is not yet possible to modify the state of the database through the web UI.

The idea for this project came from Solita's Dev Academy pre-assignment.  
See: https://github.com/solita/dev-academy-2023-exercise
