# Skyscanner Roundtrip
App that lets you find multi-day multi-stop low-cost roundtrips around Europe - other continents are comming soon.

## Motivation
When you want to do a trip around Europe and want to visit specific cities, it can get very complicated to buy all the individual tickets and find the right connections. Our app can do this for you - it finds the cheapest route that visits all the cities that you wish to visit, gives you several days to spend in each and organizes everything for you. 

### Skyscanner API
You might think that the cheapest option is to always travel to the next closest city, but in fact ticket prices vary a lot and it is often the case that it is much cheaper to visit your destinations in a different order and save significant amounts. That's why we're using Skyscanner API to find the cheapest combinations of flights.

### Twilio API
When you finally have all your travel sorted, it might still happen that you visit a country and get lost with no WiFi and limited (or very expensive) data-connection options. That's why we also implemented a service that enables you to send a single sms with your current GPS location and receive a map of the surrounding area (completely offline!).


## Installation

#### Install required packages
```sh
pip install -r requirements.txt
```

#### Start the server (localhost:8080):
```sh
python run.py
```

