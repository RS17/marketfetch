MarketFetch

### What is it?

MarketFetch is a basic Scala application for fetching financial market data from public APIs.  Currently it only works with TDAmeritrade but should be easily extendable to other APIs.  The intent is that this can be used as a starting point by traders who can then extend the existing classes with their own proprietary classes for analysis.

The application should not do anything but get information, but it's very much in development so definitely use at your own risk.  I do not recommend tying it to any account with actual money until you've thoroughly tested it on your own.

### How do I use it?

Currently (as of 01/2020) MarketFetch only gets stock quotes and option data.  I hope to expand it to get account trade history at some point.

Setup with TDAmeritrade: 
1. Follow the instructions in [this post ](https://www.reddit.com/r/algotrading/comments/c81vzq/td_ameritrade_api_access_2019_guide/ "this post ")(not by me) through step 16 to get a "code", do not unencode it.
2. replace the appname "RS17MF" with the name fo your application in MFProperties.scala
3. recompile
4. run refreshtoken [code from step 16] from the main class to get and store a refresh token (note: this will make your code no longer valid).
5. The other commands should now automatically authenticate for 90 days.  When the refresh token expires, you'll need to repeat steps 1, 2, and 4.

Sample arguments (run from main class)
- GOOG - gets Google stock quote
- option GOOG - gets full google option chain (see OptionChainBase for other arguments)
- repeater [inputfilepath] - runs list of arguments in file at inputfilepath
- refreshtoken [code] - gets a refresh token from the (url encoded) code passed in and saves to refresh token file
