Feature: LogIn Action Test
Description: This feature will test a LogIn and LogOut functionality

Scenario: Successful Login with Valid Credentials
     Given User is on Home Page
     When User Navigate to LogIn Page
     And User enters UserName and Password
     Then Message displayed LogIn Successfully


Feature: LogIn Action Test
Description: This feature will test a LogIn and LogOut functionality

Scenario: Successful Login with Valid Credentials
     Given User is on Home Page
     And LogIn Link displayed
     When User Navigate to LogIn Page
     And User enters UserName and Password
     Then Message displayed Login Successfully
     And LogOut Link displayed


Scenario: Unsuccessful Login with InValid Credentials
     Given User is on Home Page
     When User Navigate to LogIn Page
     And User enters UserName and Password
     But The user credentials are wrong
     Then Message displayed Wrong UserName & Password
     And Do something on the end