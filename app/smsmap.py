# Twilio SMS to Map project code here

from twilio.rest import TwilioRestClient 
import twilio.twiml

import requests

ACCOUNT_SID = "AC967dff27485707f431ba8e8369491df9" 
AUTH_TOKEN = "ab61121b926d8c4a93b6327e7304e343" 

client = TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN) 
base = "+441922214447"

def get_map_imgs(lat, lng):
	for z in [16, 17]:
		url = "https://maps.googleapis.com/maps/api/staticmap?center={},{}&zoom={}&size=500x500&key=AIzaSyD7_mdKB-fGg-O9axsu_wIsvW6XjeTs0YI".format(lat, lng, str(z))
		r = requests.get(url)
		f = open("map-{}.png".format(z), 'wb')
		f.write(r.content)
		f.close()

# get_map_imgs(41.3894881, 2.1111972)

import cv2
from matplotlib import pyplot as plt
import numpy as np
from skimage import data
from skimage.transform import resize
import binascii

def img_to_bitstring(img_url):
	#experiment with higher and lower resolution
	imgB = cv2.imread(img_url,  cv2.CV_LOAD_IMAGE_GRAYSCALE)

	## uncomment if used outside of jupyter notebook
	# cv2.imshow('image',imgB)
	# cv2.waitKey(0) & 0xFF
	# cv2.destroyWindow('image') 

	# grayscaled image
	# plt.imshow(imgB, cmap='Greys_r')

	# thresholding the grayscaled image
	tresh = 235
	ret, imgB_tresh = cv2.threshold(imgB,tresh,255,cv2.THRESH_BINARY)
	#ret, imgS_tresh = cv2.threshold(imgS,tresh,255,cv2.THRESH_BINARY)

	#plt.figure()
	#plt.imshow(imgB_tresh, cmap='Greys_r')

	#plt.figure()
	#plt.imshow(imgS_tresh, cmap='Greys_r')


	# rescaling the higher resolution image with cv2
	small = cv2.resize(imgB_tresh, (0,0), fx=0.2, fy=0.2) 
	#plt.figure()
	#plt.imshow(small, cmap='Greys_r')


	# rescaling the higher resolution image with skimage (probably better)
	small2 = resize(imgB_tresh, (79, 79))
	#plt.figure()
	#plt.imshow(np.round(small2), cmap='Greys_r')

	# translating binary image to a bit strind
	flat = np.round(small2).flatten().astype(int).astype(str)
	bit_string = ""
	for e in flat:
		bit_string = bit_string + e

	key = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz:;"
	
	ascii_string = ""
	for i in range(0,len(bit_string),6):
		block = bit_string[i:i+6]
		block_id = int("0b"+block,2)

		ascii_string = ascii_string + key[block_id]

	return ascii_string


def respond(lat, lng):
	get_map_imgs(lat, lng)
	body = img_to_bitstring('map-16.png')
	#body="0x103018087f000101fc340a071c20fe00141ff058170f0c81f48061ffe8387f0f0e01f10d0ffe1831fc4f8c03e038ffe01073f84f860009cfffc0a0e3e7078600063fff8f60f7cc87070018ffffbe43ff31c0030133ffff7e873c67000302f7fffe7d00719e0003036ffff0e343d9390033845fff00029f98720019981ffe0005fb1bcc0079a1bffc007dc47f3000f1827fe007e879ee6001c302ff0007d1e3fc80070406f8000fb3c1f6001e18c1f01c1e63a7f6003821db805c11876cfe00e0c39000f05e0f13e60103033041e3601df55b03040627c11b00fdde230e100eff81b0017d30c318500c1f08800cc0f901e0c419bc7c0039986601819c3945c006799848018c38237c000ef3702005d8e003e001dce7c0c038d1c07c0ef399cfb100440003387ff733fe6601d1801d13f7e67799880324007032efce7e33300690130021cfdffc0c401930e80c438fbff8010044cf80f8c3bfe3f802009ff007f0877f03f00801af007f810ffe07e30006d803f8020ffc0f06001928078026f9f800080076c00801c481f8000100a900001f087bf0004602260001f318f7e0079e0e48000f8211ef8038cd9d2000fe0e23c80341eefe418fde6e67803c0265e90efcf7dc4002c0006786378179f8801a400032086ce0ffe100680010303354e1fdc30e808060304282c0d802d00003e0398befce2007400137c01a02de0000580803e8007c06dc800780601f8003980c8000381c01fc003010103803c0001fc033842061f83c0000fe01f83c605e1bf0000fe01a43f840f03c30007f30b09ff08f03c02007f00f60ffe1782400007f007201fe0383c00067f807a007f0021e00007ff03d08070004e00003f9ffc84000000f00001f801486800000380001ff01e5be200007d0001fc20e61d44000063000ff05da038e0001d82189ff0e501bdc000e0867037e39b10d5101cc0087e03f6860400c058001ff003c409294540c0003f803fa012e970000003f801cf802147f0e0001fe0321f00401fa4d801fc61e03e0e8ced88f02fc01e007c3d780803e0fe01e0005cfef23f03c7e00f0000f873644227ff00f000017377effac7L" 
	print("SMS length: ", len(body))
	return body
	#resp = twilio.twiml.Response()
	#resp.message("Hello, Mobile Monkey {}, {}".format(lat, lng))
	#return str(resp)
	