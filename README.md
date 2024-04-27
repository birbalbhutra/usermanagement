#REQUIREMENTS

Create, Update, Delete, List web service endpoints for a User object.
User object should contain a name, email address, password and the date of their last login.
Provide a login endpoint that validates the email address and password provided by the user matches the
one stored in the database.


#SETUP PROJECT

Setup MySQL locally and create a DB named `usermanagement`.

Setup JDK as well.

RUN `mvn spring-boot:run` to start the project locally.


#IMPORTANT ENDPOINTS

`POST /api/v1/login   Provide email and password for login`

`POST /api/v1/signup  Use this for creation of new users`

`PUT /api/v1/users/update/{userId}   Update users with userId`

`DELETE /api/v1/users/delete/{userId}   Delete users with userId`

`GET /api/v1/users   List All Users, can only be accessed by ADMIN users`