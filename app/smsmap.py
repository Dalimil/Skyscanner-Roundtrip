# Twilio SMS to Map project code here

import requests

def get_map(lat, lng):
	lat = "40.71"
	lng = "-73.998"
	for z in [18, 16, 14, 12]:
		url = "https://maps.googleapis.com/maps/api/staticmap?center={},{}&zoom={}&size=500x500&key=AIzaSyD7_mdKB-fGg-O9axsu_wIsvW6XjeTs0YI".format(lat, lng, str(z))
		r = requests.get(url)
		f = open("map-{}.png".format(z), 'wb')
		f.write(r.content)
		f.close()
