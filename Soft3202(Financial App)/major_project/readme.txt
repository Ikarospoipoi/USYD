SOMETHING QUIRK!!!!!!!!!!
MY API CANNOT BE RUN FOR SOME REASON IN LATE NIGHT. IT HAPPENS ON MY MILESTONE2 RESUBMISSION VERSION TOO.MY MILESTONE2 RESUBMISION IS IN D LEVEL.SEEMS IT IS SERVER PROBLEM. MY MILESTONE2 RESUBMISION IS IN D LEVEL SO IF THE EXAM VERSION NOT WORKING PLEASE CHECK WHETHER THE MILESTONE2 VERSION IS WORKING OR NOT.

Input API: Alphavantage
Output API: Pastebin
Claimed Tier: Distinction
Credit Optional Feature 1: About
Credit Optional Feature 2: Theme song
Distinction Optional Feature: Spinning Progress Indicator
High Distinction Optional Feature:

Milestone 1 Submission:
    SHA: a2bd07ab725d7a29cb2a476db36715654b428e05
    URI: https://github.sydney.edu.au/sshi4795/SCD2_2022/commit/a2bd07ab725d7a29cb2a476db36715654b428e05
Milestone 1 Re-Submission:
    SHA: 1c5c8e978a31015d695cc0834c384b910ffd62ce
    URI: https://github.sydney.edu.au/sshi4795/SCD2_2022/commit/1c5c8e978a31015d695cc0834c384b910ffd62ce
Milestone 2 Submission:
    SHA: 0d735b5f56c12bca4b03fbfc55ac2caba3af639a
    URI: https://github.sydney.edu.au/sshi4795/SCD2_2022/commit/0d735b5f56c12bca4b03fbfc55ac2caba3af639a
Milestone 2 Re-Submission:
    SHA: 599f000707a91718e8dc8b209184b89eb43dbf09
    URI: https://github.sydney.edu.au/sshi4795/SCD2_2022/commit/599f000707a91718e8dc8b209184b89eb43dbf09
Exam Base Commit:
    SHA: 599f000707a91718e8dc8b209184b89eb43dbf09
    URI: https://github.sydney.edu.au/sshi4795/SCD2_2022/commit/599f000707a91718e8dc8b209184b89eb43dbf09
Exam Submission Commit:
    SHA:5db257481e2ea3db67a64b16ff411e036675279a
    URI:https://github.sydney.edu.au/sshi4795/SCD2_2022/commit/5db257481e2ea3db67a64b16ff411e036675279a

The Line chart I generated refers to the code from:
http://www.javafxchina.net/blog/2015/04/doc03_linechart/

New in milestone2 Resubmission :
Javadoc is added in resubmisssion

SomeThing quirk:
The API has a limit with 5 times/min. If you do things like search or see company detail which will cause hit the API more than 5 times/min, you will get a message to inform you if you get such a problem. 
The Database has already contains some data.If you search something like ba, you will hit cache, if you want to delete, just delete the cache when you run my program.
The window may not fit some type of screen size. I tried it on my mac and windows pc in school seems fine, but not sure with other smaller screen size.

What I have done is Distinct:
Hurdle: I have implemented all the features including list detail, show charts, paste detail.
	You can run with e.g: grade run --args="online offline" without crashing 
	The classes and methods are separated.
	No Json is showed:
Pass: Model is fully tested. Model_View is done. 
Credit: The Program will save the data the first time you try to look for a specific company detail. The next time you try to see the detail of the company, an alert will be given and ask you whether you would like to use cache. 
The program has a theme song, you can pause/continue with the button on the top right corner.
The program has a menu bar and has a menu with an about menu item to let you see the info.
Distinguish: Concurrency is achieved with "Task" classes and thread.
A spinning progress indicator has been done and you will see it while the Api call.

Extension: The user is able to save a company and a single chart (no need for database access, just saved to a variable). When a new company is searched, the application should compare the last value of the saved chart to the last value of the corresponding chart for the new company. If the new company's value is larger, the application should pop up an alert saying 'This company's *whichever chart value* is bigger!'. The saved company name and value should be displayed in the main window. The user can replace the current saved company with a new one.
