# geocoding-api-test
perform some REST api testing on Google geocoding API

This is a mvn project project.
## Preqs
* Java 1.8 SDK
* Maven 3.x
* System Path is able to find `mvn` and `java`
* Git or download this repo

## Execution of the Test cases
* Open open CMD, terminal, or an IDE
* cd to the top-level of the repo that was cloned or download.
* If you are using an IDE, import the project as a Maven project or Java project. For example in VS Code, I opened the top level folder.
* In the terminal or CMD, enter `mvn test`, this should execute the available tests. Of course it will download the necessary dependencies.
* In the IDE, hopefully your IDE has some support for maven. Just run the maven test lifecycle goal.

## Test coverage
I didn't get much of what I wanted to cover.
There are tests that cover:
* "REQUEST_DENIED" indicates that your request was denied.
* "INVALID_REQUEST" generally indicates that the query (address, components or latlng) is missing.

I was somewhat suprised with the status codes as you could not get a 401 or 403 to be returned. Well at least with the time that I spoent on the assignment <br>
I focused more on the parameters. <br>
I will see if I can add more at a later time <br>

## Missing Test Coverage
* Testing of "OVER_DAILY_LIMIT" is just the longevity testing and hitting whatever limit for the day it is. Running a loop over the same request X + 1 times. The last request would cause this error to be returned. You will need to set wait in between each call so that you don't hit the any RATE LIMIT or the next error "OVER_QUERY_LIMIT"
* TESTING "OVER_QUERY_LIMIT" is a performance test to hit the rate limit -> indicates that you are over your quota. In the a loop don't wait in between each call. You will be bound to hit this error call.
* "UNKNOWN_ERROR" indicates that the request could not be processed due to a server error. The request may succeed if you try again. This error is usually random it the equalivalent of 500 series HTTP STATUS Codes that we are used to.
