# pingapp

This is a simple ping application 

it basically captures the output from the following four commands and creates JSON object for each last request.
1) ping -n 5 $hostname
2) tcpping $hostname 
3) tracert $hostname 

This is a maven project , it uses java 1.8 ,java executors for concurrent processing , it uses http client for tcp ping ,
it uses jackson for creating java - json objects , it uses java logger utility for logging .

