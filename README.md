# Spring-Cloud with MySQL

## API
### HTTP Get Request to helloworld
-   "/helloworld"
<pre>
EX: curl -X GET "http://<"your_url">:8080/helloworld
</pre>

### HTTP Post Request to add user

- "/addUser" 
    - Body: { "firstName":"XX","lastName":"XX","address":"XX","email":"XX" }


<pre>
EX: curl -i -H "Content-Type: application/json"  \
         -d "{ \"firstName\":\"WW\", \"lastName\":\"XX\", \"address\":\"YY\", \"email\":\"ZZ\" }" \
         -X POST "http://<"your_url">:8080/addUser
</pre>

### HTTP Delete Request to delete user
- "/deleteUser/{id}"
<pre>
EX: curl -X DELETE "http://<"your_url">:8080/deleteUser/123456
</pre>

### HTTP Get Request to print all user
- "/printAllUsers"
<pre>
EX: curl -X GET "http://<"your_url">:8080/printAllUsers
</pre>


