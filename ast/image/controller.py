import requests
import json
import base64
base_url= 'https://api.kairos.com/'
header={
	"Content-type"    : "application/json",
	"APP_ID":"bae65c79",
	"APP_KEY" :"54031ab71f5bff6c2b21cc6af8a6a0d7"
}
class Image(object):
	def __init__(self, filename,name,gallery_name):
		self.filecontent= filename
		# self.action=action
		self.name= name
		self.gallery_name=gallery_name
	def encode(self):
		with open (self.filecontent,"rb") as image_file :
			encoded_string = base64.b64encode(image_file.read())
			return encoded_string

	def enroll(self):
		url= base_url+"/enroll"
		payload={
		"image":self.encode(),
		"subject_id":self.name,
		"gallery_name":self.gallery_name
		}
		r=requests.post(url,data=json.dumps(payload),headers=header)
		return r.content
	def recognize(self):
		url= base_url+"/recognize"
		payload={
		"image":self.encode(),
		"gallery_name":self.gallery_name
		}
		r= requests.post(url, data=json.dumps(payload),headers=header)
		return r.content


# for i in range(1,58):
# 	try:
# 		filename= "/home/zurez/Work/hackathon/ast/image/images/himanshu/h ("+str(i)+").jpg"
# 		# print (filename)
# 		i = Image(filename,"Himanshu","gallery")
# 		print(i.enroll())
# 	except:pass

i= Image("/home/zurez/Work/hackathon/ast/image/htest1.jpg","Zurez","gallery")
print (i.recognize())