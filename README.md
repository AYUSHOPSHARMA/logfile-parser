# logfile-parser
Our custom-build server logs different events to a file named logfile.txt. Every event has 2 entries in the file
- one entry when the event was started and another when the event was finished. The entries in the file
have no specific order (a finish event could occur before a start event for a given id)
Every line in the file is a JSON object containing the following event data:
 id - the unique event identifier
 state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
 timestamp - the timestamp of the event in milliseconds
Application Server logs also have the following additional attributes:
 type - type of log
 host - hostname
