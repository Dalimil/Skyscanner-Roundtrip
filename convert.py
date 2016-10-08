import json

for i in json.loads(open("cities.json").read())["cities"]:
	f = open("imgs/{}.jpg".format(i["short"]), "wb")
	s = i["img"].replace("data:image/jpeg;base64,", "")
	print(s[:10])
	f.write(s.decode('base64'))
	f.close()
