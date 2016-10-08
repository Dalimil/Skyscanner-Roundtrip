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
	return "<code>GET /map?lat=41.37&lng=2.08<br>- and now wait for the SMS<br><br>POST /trip <br> - with parameter 'params'=jsonData<br> where jsonData = {}</code>".format(json.dumps(form_sample, indent=4))

@app.route('/trip', methods=['POST'])
def getTrip():
	params = request.form["params"] if "params" in request.form else json.dumps(form_sample)
	params = json.loads(params)

	cities = params["cities"]
	year_month = params["year-month"]
	budget = params["budget"]
	journey = flights.find_flights(cities, year_month)
	return json.dumps(journey)

@app.route('/map')
def get_map():
	lat = request.args.get('lat') if 'lat' in request.args else 41.37
	lng = request.args.get('lng') if 'lng' in request.args else 2.08
	smsmap.get_map(lat, lng)
	return "OK"

@app.route('/debug')
def debug():
	flights.find_flights(['BUD', 'ROME', 'VENI', 'BCN'], "2016-11")
	return "ok"
