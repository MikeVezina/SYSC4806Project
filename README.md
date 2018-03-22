# SYSC 4806 Project [![Build Status](https://travis-ci.com/MikeVezina/SYSC4806Project.svg?token=5XuGRvxnd7EFyJcxyBNe&branch=master)](https://travis-ci.com/MikeVezina/SYSC4806Project)

Group Members: Alex Hoecht, Michael Vezina, and Reid Cain-Mondoux
-----------------------------------------------------------

Project Backlog:
----------------------------
    Current State of the Project:
    ------------------------------------
    
    
    Scrum 1: Give a 10-15 minute demo during the lab on March 8th.
    
    Scrum 2: Give a 10-15 minute demo during the lab on March 22rd.
    
    Scrum 3: Public demo during the lab on April 5th.



Project Description:
-----------------------------
Users review products. These products could be identified by a URL to the web site where they are listed. 

A review consists of a star-rating and some text. 

Users can also "follow" other users whose reviews they find valuable. A user can then list products for a given 
category by its cumulative rating, or the cumulative rating of only the users they follow. A user can also list the 
users whose ratings are the most "similar" to their own using the **Jaccard distance** (google it!). Product reviews 
can also be ranked according to the similarity score of the people who reviewed them. Users can also list the most 
followed users. The **degree of separation** (see Kevin Bacon!) according to the "follow" link can also be displayed 
next to each reviewer (the assumption is that the "closer" a user is to you, the more trustworthy he/she should be 
to you).


Milestones:
----------------------------
| Milestone| Title| Description|
|----------|------|------------|
|    1     | Early prototype| The application has a user login, and a registration page for new users to create an account (A user must have an account to login to the application). When logged in, users can see their account information, write reviews on products, and view any previous reviews they have written. They may also view a product page which displays User reviews written about it. |
|    2     | Alpha Release| The application now uses Flyway db migration tool. Added Diagrams/Drawings to product BACKLOG. Increased number of unit/integration tests run. **Functionality:** The application starts with a user login page that requires a user to create a new Account (Admins already exist). Once logged in, the home page of the application will be displayed showing the top rated products in the application.  Users have the ability to navigate to Product pages and write reviews about the product. Users may also navigate to other User pages to see their account information and can follow the user. Admins of the application may access their private services through /admin. Admin services include generate test objects for the application and Create custom products for the application. (Jaccard distance is implemented into the backend, but not front end yet)
|    3     | Project complete|

      
 
How to Run:
------------------------------
1) Clean and Package Maven
2) Run App.class