MarketFetch

### What is it?

MarketFetch is a basic Scala application for fetching financial market data from public APIs.  Currently it only works with TDAmeritrade but should be easily extensible to other APIs.  The intent is that this can be used as a starting point by traders who can then extend the existing classes with their own proprietary classes for analysis.

The application does not enter trades by itself, but it's very much in development so definitely use at your own risk.  I do not recommend tying it to any account with actual money until you've thoroughly tested it on your own.

### How do I use it

Currently (as of 01/2020) MarketFetch only gets stock quotes and option data.  

Setup with TDAmeritrade: 
- Follow the instructions in [this post ](https://www.reddit.com/r/algotrading/comments/914q22/successful_access_to_td_ameritrade_api/ "this post ")(not by me) through step 7 to get a refresh token.
- paste the refresh token in the location indicated by MFProperties.scala
- enter the application name in MFProperties.scala
- recompile 

Sample arguments (run from main class)
- GOOG - gets Google stock quote
- option GOOG - gets full google option chain (see OptionChainBase for other arguments)
- repeater [inputfilepath] - runs list of arguments in file at inputfilepath
