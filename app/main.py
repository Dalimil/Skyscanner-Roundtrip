from flask import *
# Import the app object from the main app module
from app import app
import json
import requests
import smsmap
import datetime

API_KEY = "ca462265583925016847853845313477"

jsonSample = {
	'from_list': False, # or True + cities=Europe
	'cities': ['London', 'Paris', 'Barcelona'], # or Europe
	'date_from': 1476182787,
	'date_to': 1477737987,
	'price': 400 # or none
}

@app.route('/')
def index():
	return "<code> GET /map - to see sample usage <br><br>POST /trip <br> - with parameter 'params'=jsonData<br>jsonData = {}</code>".format(json.dumps(jsonSample, indent=4))


@app.route('/trip', methods=['POST'])
def getTrip():
	params = request.form["params"]
	params = json.loads(params)

	from_list = params["fromList"]
	cities = params["cities"]
	if (from_list):
		cities = select_from_list(cities)

	date_from = params["date_from"]
	date_to = params["date_to"]
	budget = params["budget"]

@app.route('/debug')
def debug():
	# find_flights(["LON", "BCN", "PRG", "AMS"], 1476182787)
	return "ok"

@app.route('/imgs')
def get_imgs():
	return "<img src='" + "'><img src='".join([ i["img"] for i in json.loads(open("cities.json").read())["cities"] ]) + ">"

@app.route('/map')
def map_route():
	smsmap.get_map("", "")
	return "ok"

def select_from_list(name):
	if (name == "Europe"):
		return ["Paris", "London"]
	print("invalid list-name")
	return []

def qdtToTs(qdt):
	# print(qdt)
	k = datetime.datetime.strptime(qdt, "%Y-%m-%d")
	return int((k-datetime.datetime(1970,1,1)).total_seconds())

def find_flights(cities, c_date):
	journey = []

	a = cities[0]
	seen = [a]
	while(len(seen) != len(cities)):
		print(a, c_date, journey)
		could_go = []
		for b in cities:
			if a == b:
				continue
			if b in seen:
				continue

			flights = [ i for i in find_flights_ab(a, b) if qdtToTs(i["time"]) > c_date + 24*3600*2 ]
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

	flights = [ i for i in find_flights_ab(a, cities[0]) if qdtToTs(i["time"]) > c_date + 24*3600*2 ]
	final_tripA = ({ "from": a, "to": cities[0], "price": flights[0]["price"], "time": flights[0]["time"] })
	final_tripB = ({ "from": a, "to": cities[0], "price": flights[1]["price"], "time": flights[1]["time"] })
	final_trip = final_tripA if final_tripA["price"] < final_tripB["price"] else final_tripB
	journey.append(final_trip)

	print(journey)
	return journey



def find_flights_ab(a, b):
	year = "2016"
	month = "10"
	url = "http://partners.api.skyscanner.net/apiservices/browsegrid/v1.0/GB/GBP/en-GB/{}/{}/{}-{}?apiKey={}".format(a, b, year, month, API_KEY)
	r = requests.get(url, headers={"Accept": "application/json"})
	# print(r.text)
	data = r.json()["Dates"]
	return [ { "time": data[0][i]["DateString"], "price": data[1][i]["MinPrice"] } for i in range(len(data[0])) if data[1][i] != None ]


