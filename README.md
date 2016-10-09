# Skyscanner Roundtrip

Mobile app that lets you find multi-day multi-stop low-cost roundtrips around Europe, US or any other continent. Simply pick the cities that you want to visit, your available date, and our Android app will plan the best round-trip with the cheapest flight combinations.

<img src="https://github.com/Dalimil/Skyscanner-Roundtrip/raw/master/docs/screenshots/Screenshot_2016-10-09-08-10-18.png" width="150">
<img src="https://github.com/Dalimil/Skyscanner-Roundtrip/raw/master/docs/screenshots/Screenshot_2016-10-09-08-11-41.png" width="150">
<img src="https://github.com/Dalimil/Skyscanner-Roundtrip/raw/master/docs/screenshots/Screenshot_2016-10-09-08-10-32.png" width="150">
<img src="https://github.com/Dalimil/Skyscanner-Roundtrip/raw/master/docs/screenshots/Screenshot_2016-10-09-08-52-06.png" width="150">
<img src="https://github.com/Dalimil/Skyscanner-Roundtrip/raw/master/docs/screenshots/Screenshot_2016-10-09-08-52-20.png" width="150">

## Motivation

When you want to do a trip around Europe and want to visit specific cities, it can get very complicated to buy all the individual tickets and find the right connections. Our app can do this for you - it finds the cheapest route that visits all the cities that you wish to visit, gives you several days to spend in each and organizes everything for you. 

## Skyscanner API

You might think that the cheapest option is to always travel to the next closest city, but in fact ticket prices vary a lot and it is often the case that it is much cheaper to visit your destinations in a different order and thus save significant amounts. Of course, nobody has time to do all this planning manually, so that's why we're using the Skyscanner API to compute the best and cheapest combinations of flights on our custom backend.

## Twilio API

When you finally have all your travel sorted, it might still happen that you visit a country and get lost with no Wi-Fi and limited (or very expensive) data-connection options. That's why we also implemented a service that enables you to send a single SMS with your current GPS location and receive a simple map of the surrounding area - completely offline, no data connection needed.

## How we built it

The mobile app is built for Android. It communicates with Skyscanner API, uses SMS to communicate with Twilio and also talks to our own custom backend which handles the heavy computations and data processing. This allows the interface between the app and the backend to be clean and maintainable.

## Challenges we ran into

The most difficult part was implementing the routing algorithm that finds the optimal combinations of flights for the trip. We had to think about different ways to handle dates, prices, and various travel paths that one could take.
For the Twilio part, we ran into problems with SMS size limitations but we eventually managed to send and assemble data by splitting it into several messages sent to the device independently.

## What's next for Skyscanner Rountrip

We would like to continue to get user feedback and improve the experience of the app.

# Technologies
Android, Java, XML, Python, Flask, Twilio API, Skyscanner API, Google Maps API, Numpy, scikit-image, cv2
