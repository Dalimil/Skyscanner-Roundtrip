import requests
import datetime
import json


API_KEY = "ca462265583925016847853845313477"

def qdtToTs(qdt):
	# print(qdt)
	k = datetime.datetime.strptime(qdt, "%Y-%m-%d")
	return int((k-datetime.datetime(1970,1,1)).total_seconds())

def timeFromMonth(year_month):
	k = datetime.datetime.strptime(year_month, "%Y-%m")
	return int((k-datetime.datetime(1970,1,1)).total_seconds())

def find_flights(cities, year_month):
	journey = []

	c_date = timeFromMonth(year_month)
	a = cities[0]
	seen = [a]
	while(len(seen) != len(cities)):
		print("Partial: ", a, c_date, journey)
		could_go = []
		for b in cities:
			if a == b:
				continue
			if b in seen:
				continue

			flights = [ i for i in find_flights_ab(a, b, year_month) if qdtToTs(i["time"]) > c_date + 24*3600*2 ]
			if (len(flights) > 1):
				could_go.append({ "from": a, "to": b, "price": flights[0]["price"], "time": flights[0]["time"] })
				could_go.append({ "from": a, "to": b, "price": flights[1]["price"], "time": flights[1]["time"] })
				could_go.append({ "from": a, "to": b, "price": flights[2]["price"], "time": flights[1]["time"] })

		could_go = sorted(could_go, key=lambda x: x["price"])
		print(could_go)
		next_go = could_go[0]
		journey.append(next_go)
		c_date = qdtToTs(next_go["time"])
		a = next_go["to"]
		seen.append(a)

	flights = [ i for i in find_flights_ab(a, cities[0], year_month) if qdtToTs(i["time"]) > c_date + 24*3600*2 ]
	final_tripA = ({ "from": a, "to": cities[0], "price": flights[0]["price"], "time": flights[0]["time"] })
	final_tripB = ({ "from": a, "to": cities[0], "price": flights[1]["price"], "time": flights[1]["time"] })
	final_trip = final_tripA if final_tripA["price"] < final_tripB["price"] else final_tripB
	journey.append(final_trip)

	print("Final journey: ", journey)
	return journey



def find_flights_ab(a, b, year_month):
	url = "http://partners.api.skyscanner.net/apiservices/browsegrid/v1.0/GB/GBP/en-GB/{}/{}/{}?apiKey={}".format(a, b, year_month, API_KEY)
	r = requests.get(url, headers={"Accept": "application/json"})
	# print(r.text)
	data = r.json()["Dates"]
	return [ { "time": data[0][i]["DateString"], "price": data[1][i]["MinPrice"] } for i in range(len(data[0])) if data[1][i] != None ]


