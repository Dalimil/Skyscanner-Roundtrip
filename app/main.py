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
	return "<code>POST /trip <br> - with parameter 'params'=jsonData<br> where jsonData = {}<br> where budget is not used at the moment</code>".format(json.dumps(form_sample, indent=4))

@app.route('/trip', methods=['POST'])
def getTrip():
	params = request.form["params"] if "params" in request.form else json.dumps(form_sample)
	params = json.loads(params)

	cities = params["cities"]
	year_month = params["year-month"]
	budget = params["budget"]
	journey = flights.find_flights(cities, year_month)
	return json.dumps(journey)

@app.route('/debug')
def debug():
	find_flights(['BUD', 'ROME', 'VENI', 'BCN'], "2016-11")
	return "ok"

@app.route('/imgs')
def get_imgs():
	return "<img src='" + "'><img src='".join([ i["img"] for i in json.loads(open("cities.json").read())["cities"] ]) + ">"

@app.route('/map')
def map_route():
	smsmap.get_map("", "")
	return "ok"
