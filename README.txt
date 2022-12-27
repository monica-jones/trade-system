TABLE OF CONTENTS



0. SYSTEM REQUIREMENTS

This program was written to be run with Intellij.

Download the Following Application to View the Database Information depending on your PC type:
         - https://github.com/pawelsalawa/sqlitestudio/releases/download/3.2.1/InstallSQLiteStudio-3.2.1.exe  (Use This If you are using Windows)
         - https://github.com/pawelsalawa/sqlitestudio/releases/download/3.2.1/InstallSQLiteStudio-3.2.1.dmg  (Use This If you are using Mac)

Download The SQLite Library From the Following Link:
	 - https://drive.google.com/file/d/1sOZ4eN1kzQ328IJkRR5TgfnhuiCNe15x/view?usp=sharing


It also requires a SQLite library to be installed. Download the file from the links above. In Intellij, open project
folder and navigate to File > Project Structure > Libraries. Click the '+' symbol on the top left to open file
navigation and add the SQLite file.

Restart Intellij to establish these changes. Navigate to MainPage and run it to start the program.

NOTE: To run the program from scratch and start off with a completley new set of data, delete the database.db and database_demo.db files
      from the Phase2 Directory


0.1 LANGUAGE CONFIGURATIONS

The program will detect the language of your system and run in either English or French. If your system language is not
one of these, it will run in English by default.

To change the display language, either change the system display language or run the program by the command line:
Open the command line and change directory to the project folder:
    cd \...\group_0040\phase2\src\gui
Compile the program:
    javac MainPage.java
To run the program in English:
    java MainPage en
TO run the program in French:
    java MainPage fr


1. SIGN UP
From the welcome page, select "Sign up" to navigate to the registration page.
Select a home city from the given options and choose a password.
A user ID will be generated for you. You must memorize this user ID and password in order to sign in.
Both regular users and administrators can be signed up this way.

1.2 GUEST ACCOUNT SIGN UP
Guest accounts can be created to view but not interact with the system.
From the welcome page, select "Guest account" to create one. Guest accounts can perform the same actions as
regular user accounts. The data of guest accounts is stored in a separate database from the main database of the
program, which allows guests to freely explore various functions of the program without affecting the regular users at
all. If you want to make trades as a guest account, please create multiple guest accounts to simulate the tradings
by yourself.

2. USER OPERATIONS

Normal users should enter their User ID and password, then click "User Login" when logging in.

2.1 ADDING ITEMS TO INVENTORY
From the main page, select "view account details". Select "User Inventory."
To view your current inventory, select "View User Inventory". This includes items that have not yet been approved by
the administrator.
To add an item, enter its value, its name, and its description, then select "Add new item".
To delete an item, enter its ID, then select "Delete Item".

2.2 ADDING ITEMS TO WISHLIST
From the main page, select "view account details". Select "WishList/System Inventory."
To view the current wishlist, select "View Wishlist Inventory".
To view items that can be added to the wishlist, select "View System Inventory".
To add an item to the wishlist, enter its ID and select "Add to wishlist".
To delete an item from the wishlist, enter its ID and select "Delete from wishlist".

2.3 OTHER USER ACCOUNT OPERATIONS
To request to have your account unfrozen, select "view account details", then "request to unfreeze account".
To view your recent trades, select "view account details", then "Present Recent Trades".
To view your most frequent trading partners, select "view account details", then "Present Frequent Traders".
To change your vacation status, select "Change Vacation Status" from the main menu. When vacation mode is on, other
users will not be able to request trades with you.

2.4 REQUESTING TRADES
From the main page, select "view trade menu". From here, you can request or manage a trade.
To request a trade, select the type of trade to request:
ONE WAY: To request a one way trade, enter the ID of the item you want, and select "permanent" if you want to borrow it
forever.
Select "View all available items" to see the items you can request.
TWO WAY: To request a two way trade, enter the ID of the item you want. Select "view all inventory" to see the items
you can request.
With an ID entered, select "view items you can offer" to see which items you own that the owner has on their wishlist.
Select permanent if you want the trade to be forever.
THREE WAY: If you have no items the owner is interested in, they may be interested in a three way trade. To request a
three way trade, enter the ID of the item you want and select "search for trade". If there is a possible three way
trade, the system will print the suggested trade. If you are interested in the trade, select "request trade".

2.5 AUTOMATICALLY SUGGESTED ITEMS
To view items automatically suggested by the system to offer in a trade, navigate to "view trade menu", then select
either "Request Two Way Trade" or "Request Three Way Trade". Enter the ID of the item you're interested in and the
system will automatically suggest items to offer that are on the owner's wishlist, or a possible three way trade that
works for all users.

2.6 MANAGING TRADES
From the main page, select "view trade menu".
REQUESTED TRADES: Select "Display Requested Trades" to view your trade requests. This includes trades you requested, and
trades other users requested of you. To accept a trade request, enter the ID and select "Accept Trade". To decline it,
select "Decline Trade" instead. You may not accept your own requests, but you may cancel them by selecting "Decline
Trade".
PENDING TRADES: These are trades for which a meeting has not been set. Select "Display List of Pending Trades" to view
your pending trades. Enter a trade's ID and select "Display Meeting Info" to see the current suggestion for the meeting.
If the other user has suggested a meeting, select "Accept This Meeting" to accept it. If you do not like the meeting or
a meeting has not been suggested, enter a date, time, and location to suggest, and select "Suggest alternate Meeting
Time and Location".
OPEN TRADES: These are trades for which a meeting has been agreed upon. To view your list of open trades, select
"Display Open Trades". To view the current meeting information for the trade, enter its ID and select "Display
Current Meeting for Trade ID". If the meeting occurred successfully, select "Mark Complete". If this trade is temporary,
when all traders have marked the first meeting complete, a second meeting will be scheduled for 1 month later. If any
trader did not show up for the meeting, or it did not occur as planned for another reason, select "Mark Incomplete".

2.7 MONETIZATION
Every newly created user will have an inital funds of $5000 which they can use to borrow or lend item from other users.

3. ADMINISTRATOR OPERATIONS

An existing admin account is:
	UserID: 1
	password: blerg
This account can be logged into upon starting the program. Other admin accounts can either be created by the
registration page or the admin main menu.


3.1 MANAGING USERS
To manage users, select "Manage Users and Items" from the main page.
Select "Display flagged users" to see a list of users who have surpassed any threshold set by the system. To freeze a
user, enter their ID and select "Freeze user".
Select "Display frozen users" to see a list of users who are frozen.
Select "Display unfreeze requests" to see a list of users who have requested to be unfrozen. To unfreeze a user, enter
their ID and select "Unfreeze user".

3.2 MANAGING ITEMS
To manage items in the system inventory, select "Manage users and items" from the main page.
Select "Display items to approve" to display a list of items that users have requested to add to the system.
To approve an item, enter its ID and select "Approve item". To deny it, select "Deny item".


3.3 UNDOING ACTIONS
To undo user actions, select "Undo Recent Actions" from the main menu.
To undo the most recent trade, select "Undo last trade". This will delete the trade from the system.
To undo the three most recent trades, select "Undo recent trades". This will delete the trades from the system.
To undo a specific trade, enter its ID and select "Undo specified trade".
To delete an item from the system inventory, enter its ID and select "Delete item from inventory". This will remove it
from users' inventories and wishlists as well. You do not need to enter a user ID.
To delete an item from a user's wishlist, enter the item ID and the user ID and select "Delete item from wishlist".

3.4 LIMITS
To view the current thresholds set by the system, select "view limits" on the main menu.
The meeting edit limit is the number of times traders may edit a meeting before the trade is cancelled.
The overborrow limit is the number of items a user my receive more than they give before being flagged.
The trades per week limit is the number of trades a user may participate in per week before being flagged.
The incomplete trades limit is the number of incomplete trades a user may be involved in before being flagged.
To change a limit, enter the new limit and select "Reset Limits". These limits can also be changed by manually editing
the config.txt file.
To reset the trade count for each user, select "Reset Weekly Trade Count". This resets each user's trade count to 0
and should be done at the start of every week.

AUTHORS
Aly Abdelwahed
Namratha Anil
Rutwa Engineer
Monica Jones
Janine Newton
Wen Hua Tung
Yuchong Zhang
Richard Zhou
