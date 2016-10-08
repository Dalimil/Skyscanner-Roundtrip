from flask import *
# Import the app object from the main app module
from app import app
import json
import smsmap
import flights

form_sample = {
	'cities': ['BUD', 'ROME', 'VENI', 'BCN'],
	'year-month': "2016-11",
	'budget': 400 # or none
}

@app.route('/')
def index():
	return "<code>SMS to +441922214447 -> POST /map with lat=41.37&lng=2.08<br>- and now wait for the reply SMS<br><br>POST /trip <br> - with parameter 'params'=jsonData<br> where jsonData = {}</code>".format(json.dumps(form_sample, indent=4))

@app.route('/trip', methods=['POST'])
def getTrip():
	params = request.form["params"] if "params" in request.form else json.dumps(form_sample)
	params = json.loads(params)

	cities = params["cities"]
	year_month = params["year-month"]
	budget = params["budget"]
	journey = flights.find_flights(cities, year_month)
	return json.dumps(journey)

@app.route('/map', methods=['POST'])
def get_map():
	print("Fetching map images")
	print(request.form)
	data = request.form["Body"] if "Body" in request.form else '{ "lat": 41.37, "lng": 2.08 }'
	data = json.loads(data)

	lat = data["lat"]
	lng = data["lng"]
	return smsmap.respond(lat, lng)

@app.route('/debug')
def debug():
	flights.find_flights(['BUD', 'ROME', 'VENI', 'BCN'], "2016-11")
	return "ok"
