from flask import *
# Import the app object from the main app module
from app import app
import json
import requests
import smsmap

API_KEY = "ca462265583925016847853845313477"

jsonSample = {
	'from_list': False, # or True + cities=Europe
	'cities': ['London', 'Paris', 'Barcelona'], # or Europe
	'date_from': 1475912855,
	'date_to': 1475998855,
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
	find_flights_ab("", "")
	return "ok"

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
	k = datetime.datetime.strptime(s, "%Y-%m-%dT%H:%M:%S")
	return int((k-datetime.datetime(1970,1,1)).total_seconds())

def find_flights(cities, c_date):
	journey = []
	seen = []
	for a in cities:
		could_go = []
		for b in cities:
			if a == b:
				continue
			if b in seen:
				continue

			flights = [ i for i in find_flights_ab(a, b) if qdtToTs(i["QuoteDateTime"]) > c_date + 24*3600*2 ]
			if (len(flights) > 0):
				could_go.append({ "to": b, "price": flights[i]["MinPrice"] })

		could_go = sorted(could_go, lambda x: x["price"])
		print(could_go)
		next_go = could_go[0]
		journey.append(next_go)



def find_flights_ab(a, b):
	a = "LON"
	b = "JFK"
	year = "2016"
	month = "11"
	url = "http://partners.api.skyscanner.net/apiservices/browsegrid/v1.0/GB/GBP/en-GB/{}/{}/{}-{}?apiKey={}".format(a, b, year, month, API_KEY)
	r = requests.get(url, headers={"Accept": "application/json"})
	print(r.text)
	return r.json["Dates"][1]


