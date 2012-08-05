palindrome
===============
Environment

1. Test on Java 6 && JUnit4 
2. Run unit test with __mvn test__
3. Start server with __mvn jetty:run__
4. Access url(http://localhost:8080/Palindrome), replace __localhost__ if the server is not in the same machine.
5. Import into eclipse using pom.xml.

Rule(Assumption)

1. The input stick to English characters. Any other characters will be ignored.
2. The score will be half of the length of palindrome string (puntuatation and word dividers is not included), the score will be round up to a integer.
3. Only registered member can be shown in the rank list. 

Introduction of code

1. com.playfish.palindrome.util package
###### StringUtil.java : Check palindrome String, and return the score. ==> A better name is needed.(more related with application)
2. com.playfish.palindrome.model package
###### User.java : Keep user information with a unique id(UUID).
###### Score.jave : Used in rank list.
3. com.playfish.palindrome.dao package
Store and load user information from storage.
###### UserDaoLocalImpl.java: Using ConcurrentHashMap to keep users in memory. Can use another platform or storage implements UserDao interface.

4, com.playfish.palindrome.action package
###### Controller of MVC
5, com.playfish.palindrome.service package
###### Main logic, store and load user information, update
rank list.


TODO(based on the order of importance)

1. Imporve the user experiences by adjust the page layout, font size. Extract CSS code from Html files.
2. Make the ranks page and registration page as a popup.
3. Add input validity check at client side(javascript) to release the load of server, now checked only at server side.
4. Add memory statitic report using JMX and reconsider places that needs logging.
5. Clear the unregisted user who has not access the game based on lastActive timestamp of User object. 
6. Do stress test using JMeter, now just use unit test to simulate multithread access at logic side.
7. Add view layer and control layer unit test, now only for logic side. 


