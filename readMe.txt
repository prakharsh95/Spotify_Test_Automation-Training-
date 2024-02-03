maven command with environment variables is given below:
mvn test -DBase_URL="https://api.spotify.com" -DAccount_Base_URL="https://accounts.spotify.com"

For running locally on IDE, use the VM options text given below in configurations:
-ea -DBase_URL="https://api.spotify.com" -DAccount_Base_URL="https://accounts.spotify.com"