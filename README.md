# Popular Movies App (Udacity Android Nanodegree project)
For information in regards to project requirement, technical decisions made and related resources please refer to
project wiki

## Api key
In order to access themoviedb.org API an API key is required. It is required to modify *values/server-api-key.xml* in
 order to add a valid api key. Otherwise the server shall reject requests as unauthorized. The proper api key should
 be placed instead of *dummy-api-key* in the following code snippet from the file.
><string name="server_api_key">dummy-api-key</string>