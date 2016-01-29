
Dependencies:
-------------------------------------------------------------------------------
These scripts were tested on centos 6.4 and 
MinGW/bash on windows (the version that ships with msysgit for windows)

it will probably work under cygwin.  
It requires bash, curl, sed, cat, tr, and head to be on your path.


Running the samples:
-------------------------------------------------------------------------------
# assume <shell> is the directory where the *.sh files are.

cd <shell>

cp ./api.properties.SAMPLE ./api.properties

# edit the properties file to include your account name and token
vi ./api.properties

# should return https status 200 and an xml document.
./home-quote-1.0.sh

# should return https status 200 and no document.
./status-ping-1.0.sh

